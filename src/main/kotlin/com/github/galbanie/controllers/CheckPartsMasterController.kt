package com.github.galbanie.controllers

import com.github.galbanie.*
import com.github.galbanie.models.*
import com.github.galbanie.utils.ActionFile
import com.github.galbanie.utils.AddType
import com.github.galbanie.utils.NotificationType
import com.github.galbanie.views.CheckPartsArea
import com.opencsv.CSVWriter
import org.controlsfx.control.Notifications
import org.h2.api.ErrorCode
import org.h2.jdbc.JdbcSQLException
import org.h2.jdbcx.JdbcDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Database.Companion.connect
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*
import java.nio.file.Paths
import javax.xml.bind.JAXBContext

/**
 * Created by Galbanie on 2017-08-04.
 */
class CheckPartsMasterController : Controller() {
    override val scope  = super.scope as CheckPartsMasterScope
    override val configPath = Paths.get("conf/app.properties")
    val dataSource = JdbcDataSource()
    val crudSource = CrudSource()
    lateinit var database : Database
    init {
        //database = connect(dataSource)
        // Create Table If Exist
        // Subscribe Others Events
        subscribe<InitDataSource> {
            dataSource.setURL(config.string("database.url"))
            dataSource.user = config.string("database.user")
            dataSource.password = config.string("database.password")
        }
        subscribe<NotificationEvent> {
            var notification = Notifications.create()
                    .title(it.title)
                    .text(it.message)
            when(it.type){
                NotificationType.DEFAULT -> notification.show()
                NotificationType.CONFIRM -> notification.showConfirm()
                NotificationType.ERROR -> notification.showError()
                NotificationType.INFORMATION -> notification.showInformation()
                NotificationType.WARNING -> notification.showWarning()
            }
        }
        subscribe<ChooseFileActionEvent> {
            var files = chooseFile(title = it.title, filters = it.filters, owner = primaryStage, mode = it.mode) {}
            if(files.isNotEmpty()){
                when(it.action){
                    ActionFile.loadSources -> fire(LoadSourceDataFromFile(files.first()))
                    ActionFile.saveSources -> fire(SaveSourceDataToFile(files.first()))
                    ActionFile.saveResultToCsv -> fire(SaveResultDataToCSVFile(files.first()))
                    ActionFile.saveResultToXml -> fire(SaveResultDataToXMLFile(files.first()))
                    ActionFile.saveCheckToXml -> fire(SaveCheckDataToXMLFile(files.first()))
                    ActionFile.openXmlToCheck -> fire(LoadCheckDataFromXML(files.first()))
                }
            }
        }
        // Subscribe Check Parts
        subscribe<CheckPartsListRequest> {
            fire(CheckPartsListFound(scope.checks))
        }
        subscribe<CheckPartsQuery> { event ->
            var check = scope.checks.filter { it.id.equals(event.id) }.firstOrNull()
            if (check != null) {
                fire(ResultsListFound(check.results))
            }
        }
        subscribe<CheckPartsCreated> {
            scope.checks.add(it.check)
            fire(CheckPartsListRequest)
            workspace.dockInNewScope<CheckPartsArea>(params = mapOf(CheckPartsArea::checkParts to it.check))
        }
        subscribe<CheckPartsRemoved>{
            scope.checks.remove(it.check)
            fire(CheckPartsListRequest)
        }
        subscribe<LoadCheckDataFromXML> {
            try {
                val jaxbContext = JAXBContext.newInstance(CheckPartsWrapper::class.java)
                val unmarshaller = jaxbContext.createUnmarshaller()
                val wrapper = unmarshaller.unmarshal(it.file) as CheckPartsWrapper
                scope.checks.setAll(wrapper.checks)
                fire(CheckPartsListRequest)
                scope.checks.forEach {
                    workspace.dockInNewScope<CheckPartsArea>(params = mapOf(CheckPartsArea::checkParts to it))
                }
            }catch (e : Exception){
                fire(NotificationEvent("Could not load data", "Could not load data from file:\n${it.file.path}", NotificationType.ERROR))
            }
        }
        subscribe<SaveCheckDataToXMLFile> { event ->
            var cpa = workspace.dockedComponent
            if(cpa != null && cpa is CheckPartsArea){
                try {
                    val jaxbContext = javax.xml.bind.JAXBContext.newInstance(CheckPartsWrapper::class.java)
                    val marshaller = jaxbContext.createMarshaller()
                    marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true)
                    marshaller.marshal(CheckPartsWrapper().apply{ checks.addAll(scope.checks)}, event.file)
                }catch (e : Exception){
                    fire(NotificationEvent("Could not save data", "Could not save data to file:\n${event.file.path}", NotificationType.ERROR))
                }
            }
        }
        // Subscribe Sources
        subscribe<SourceListRequest> {
            runLater {
                transaction {
                    scope.sources.setAll(crudSource.findAll().toList())
                }
                fire(SourceListFound(scope.sources))
                fire(DropSource(scope.sourcesDraged))
            }
        }
        subscribe<SourceCreated>{
            transaction {
                try {
                    crudSource.create(it.source)
                    fire(NotificationEvent("Source saved!", it.source.name, NotificationType.CONFIRM))
                } catch (e: JdbcSQLException) {
                    if (e.errorCode == ErrorCode.DUPLICATE_KEY_1) {
                        fire(NotificationEvent("Source Name Duplicate Key", "${it.source.name} exist in database.", NotificationType.ERROR))
                    } else {
                        fire(NotificationEvent("", "Error : ${e.errorCode}", NotificationType.ERROR))
                    }

                }
            }
            fire(SourceListRequest)
        }
        subscribe<SourceUpdated>{
            transaction {
                crudSource.update(it.source)
            }
            fire(SourceListRequest)
        }
        subscribe<SourceRemoved>{
            transaction {
                crudSource.delete(it.source)
            }
            fire(SourceListRequest)
        }
        subscribe<SourceAdded> { event ->
            scope.checks.find { it.id.equals(event.checkId) }!!.apply {
                sources.setAll(event.sources)
            }
        }
        subscribe<SourceSelectedListFound> {
            scope.sourcesSelected.setAll(it.sources)
        }
        subscribe<LoadSourceDataFromFile> {
            try {
                val jaxbContext = JAXBContext.newInstance(SourceListWrapper::class.java)
                val unmarshaller = jaxbContext.createUnmarshaller()
                val wrapper = unmarshaller.unmarshal(it.file) as SourceListWrapper
                wrapper.sources.forEach {
                    fire(SourceCreated(it))
                }
            }catch (e : Exception){
                fire(NotificationEvent("Could not load data", "Could not load data from file:\n${it.file.path}", NotificationType.ERROR))
            }
        }
        subscribe<SaveSourceDataToFile> {
            try {
                val jaxbContext = JAXBContext.newInstance(SourceListWrapper::class.java)
                val marshaller = jaxbContext.createMarshaller()
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true)
                marshaller.marshal(SourceListWrapper().apply{ sources.addAll(scope.sourcesSelected) }, it.file)
            }catch (e : Exception){
                fire(NotificationEvent("Could not save data", "Could not save data to file:\n${it.file.path}", NotificationType.ERROR))
            }
        }
        subscribe<DragSource> {
            println("Drag Source Event")
            println(scope.sourcesDraged)
            scope.sourcesDraged.setAll(it.sources)
        }
        // Subscribe Parts
        subscribe<PartsAdded> { event ->
            scope.checks.find { it.id.equals(event.checkId) }!!.apply {
                when(event.mode){
                    AddType.add -> parts.addAll(event.parts.split("\n").map { Part(it) })
                    AddType.clearAndAdd -> parts.setAll(event.parts.split("\n").map { Part(it) })
                }
            }
        }
        // Subscribe Results
        subscribe<SaveResultDataToCSVFile> { event ->
            var cpa = workspace.dockedComponent
            if(cpa != null && cpa is CheckPartsArea){
                val writer = com.opencsv.CSVWriter(java.io.FileWriter(event.file), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER)
                var entries = arrayListOf<Array<String>>()//arrayOf("Part,Titre,Url,Source,Descriptions")
                entries.add(arrayOf("Part","Title","Url","Source","Descriptions"))
                scope.checks.find { it.id.equals(cpa.checkPartsModel.item.id) }!!.apply {
                    results.forEach {
                        entries.add(it.toString().split("|").toTypedArray())
                    }
                }
                writer.writeAll(entries)
                writer.close()
            }
        }
        subscribe<SaveResultDataToXMLFile> { event ->
            var cpa = workspace.dockedComponent
            if(cpa != null && cpa is CheckPartsArea){
                try {
                    val jaxbContext = javax.xml.bind.JAXBContext.newInstance(ResultListWrapper::class.java)
                    val marshaller = jaxbContext.createMarshaller()
                    marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true)
                    marshaller.marshal(ResultListWrapper().apply{ results.addAll(scope.checks.find{it.id.equals(cpa.checkPartsModel.item.id)}!!.results)}, event.file)
                }catch (e : Exception){
                    fire(NotificationEvent("Could not save data", "Could not save data to file:\n${event.file.path}", NotificationType.ERROR))
                }
            }
        }
    }

    fun connectDatabase(){
        database = connect(dataSource)
    }

    fun createTables(){
        transaction {
            crudSource.createTable()
        }
    }
}