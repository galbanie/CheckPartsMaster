package com.github.galbanie.controllers

import com.github.galbanie.*
import com.github.galbanie.models.*
import com.github.galbanie.utils.ActionFile
import com.github.galbanie.utils.AddType
import com.github.galbanie.utils.NotificationType
import com.github.galbanie.utils.PartDelimiter
import com.github.galbanie.views.CheckPartsArea
import com.opencsv.CSVWriter
import org.apache.commons.validator.routines.UrlValidator
import org.controlsfx.control.Notifications
import org.h2.api.ErrorCode
import org.h2.jdbc.JdbcSQLException
import org.h2.jdbcx.JdbcDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Database.Companion.connect
import org.jetbrains.exposed.sql.transactions.transaction
import org.jsoup.Jsoup
import tornadofx.*
import java.net.URL
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
        subscribe<Run> {
            runLater {
                val cpa = workspace.dockedComponent
                if(cpa != null){
                    if(cpa is CheckPartsArea){
                        cpa.checkPartsModel.item.lockProperty.value = false
                        this@CheckPartsMasterController.run(cpa.checkPartsModel.item, cpa.status)
                    }
                }
            }
        }
        subscribe<Clear> {
            runLater {
                val cpa = workspace.dockedComponent
                if(cpa != null){
                    if(cpa is CheckPartsArea){
                        cpa.checkPartsModel.item.results.clear()
                        cpa.checkPartsModel.item.parts.clear()
                        cpa.checkPartsModel.item.sources.clear()
                    }
                }
            }
        }
        subscribe<Stop> {
            val cpa = workspace.dockedComponent
            if(cpa != null){
                if(cpa is CheckPartsArea){
                    cpa.checkPartsModel.item.lockProperty.value = true
                }
            }
        }
        // Subscribe Check Parts
        subscribe<CheckPartsListRequest> {
            fire(CheckPartsListFound(scope.checks))
        }
        subscribe<CheckPartsQuery> { event ->
            val check = scope.checks.filter { it.id.equals(event.id) }.firstOrNull()
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
                    AddType.add -> {
                        event.parts.split("\n").map { Part(it) }.distinct().forEach{
                            if(!parts.contains(it)) parts.add(it)
                        }
                    }
                    AddType.clearAndAdd -> parts.setAll(event.parts.split("\n").map { Part(it) }.distinct())
                }
            }
        }
        // Subscribe Results
        subscribe<ResultAdded> { event ->
            scope.checks.find { it.id.equals(event.checkId) }!!.apply {
                results.add(event.result)
            }
        }
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

    private fun run(check : CheckParts, status: TaskStatus){
        runAsync(status){
            fire(NotificationEvent("Run", "Running process in progress", NotificationType.INFORMATION))
            updateTitle("Running process in progress")
            println("Running process in progress")
            check.parts.filter { it.check.not() }.forEach { part ->
                if (check.lockProperty.value){
                    fire(NotificationEvent("Stop", "Process Stopping", NotificationType.INFORMATION))
                    println("Process Stopping")
                    return@runAsync
                }
                check.sources.forEach { source ->
                    updateMessage("Current part : ${part.part} - ${source.name}")
                    println("Current part : ${part.part} - ${source.name}")
                    var hasNext = false
                    var url = source.url.replace("%part%",delimiter(part.part,source.delimiter),true)
                    var urlFinal  = url
                    var data = source.data.associate({ Pair(it.key,it.value.replace("%part%",delimiter(part.part,source.delimiter),true)) })
                    println("data = $data")
                    var start = 0
                    do {
                        try {
                            var connection = Jsoup.connect(urlFinal)
                            if (config.boolean("proxy.active")) connection.proxy(config.string("proxy.address","127.0.0.1"), config.string("proxy.port", "8080")!!.toInt())
                            connection.ignoreHttpErrors(true)
                            connection.followRedirects(true)
                            connection.timeout(config.string("timeout", "7000").toInt())
                            connection.ignoreContentType(true)
                            connection.method(source.method)
                            connection.data(data)
                            var response = connection.execute()
                            when(response.statusCode()){
                                200 -> {
                                    var host = "${response.url().protocol}://${response.url().host}"
                                    var doc = response.parse()
                                    var result : Result
                                    var elements = doc.select(source.elementSelector)
                                    elements.forEach { element ->
                                        result = Result().apply {
                                            this.part = part.part
                                            this.titre = element.select(source.titreSelector).text()
                                            this.url = if (source.urlSelector.equals("%url%")) url else{
                                                var href = element.select(source.urlSelector).attr("href")
                                                if(UrlValidator().isValid(href)) href
                                                else host+href
                                            }
                                            this.source = source
                                        }
                                        source.descriptionSelectors.forEach {
                                            if(it.isNotEmpty() and !element.select(it).isEmpty()) result.descriptions.addAll(element.select(it).map { it.text() })
                                        }
                                        source.imageSelectors.forEach {
                                            if(it.isNotEmpty() and !element.select(it).isEmpty()) result.imagesUrl.addAll(element.select(it).map { it.attr("src") })
                                        }
                                        //check.results.add(result)
                                        if(!part.sources.contains(source))part.sources.add(source)
                                        fire(ResultAdded(check.id, result))
                                    }
                                    if(source.pagination and (elements.count() > 0)){
                                        hasNext = true
                                        if((source.urlPagination.isNotEmpty() and source.urlPagination.isNotBlank())){
                                            start += source.numberElementPagination
                                            urlFinal = url + source.urlPagination.replace("%start%", (start).toString(), true)
                                        }
                                        else if(source.linkPaginationSelector.isNotEmpty() and source.linkPaginationSelector.isNotBlank()){
                                            urlFinal = doc.select(source.linkPaginationSelector).attr("href")
                                        }
                                        else {
                                            hasNext = false
                                        }
                                    }
                                    else hasNext = false
                                }
                                in 201..206 -> {
                                    fire(NotificationEvent("Code ${response.statusCode()}", response.statusMessage(), NotificationType.INFORMATION))
                                    println("Code ${response.statusCode()} : ${response.statusMessage()}")
                                }
                                in 400..451 -> {
                                    fire(NotificationEvent("Code ${response.statusCode()}", response.statusMessage(), NotificationType.WARNING))
                                    println("Code ${response.statusCode()} : ${response.statusMessage()}")
                                }
                                in 500..511 -> {
                                    fire(NotificationEvent("Code ${response.statusCode()}", response.statusMessage(), NotificationType.ERROR))
                                    println("Code ${response.statusCode()} : ${response.statusMessage()}")
                                }
                                else -> {
                                    println("Code ${response.statusCode()} : ${response.statusMessage()}")
                                }
                            }
                        }catch (e : IllegalArgumentException){
                            fire(NotificationEvent("Wrong Url", "Please make sure to give a valid url ($url) ", NotificationType.ERROR))
                            fire(NotificationEvent("Error", "Process Fail", NotificationType.ERROR))
                            println(e.message)
                            println("Please make sure to give a valid url ($url) ")
                            println("Process Fail")
                            return@runAsync
                        }catch (e : Exception){
                            fire(NotificationEvent("Error", e.message!!, NotificationType.ERROR))
                            println(e.message)
                            return@runAsync
                        }
                    }while (source.pagination and hasNext)
                    part.checkProperty.value = true
                }
            }
            fire(NotificationEvent("Run", "Process finish", NotificationType.INFORMATION))
            println("Process finish - Completed successfully")
            updateTitle("Completed successfully")
            updateMessage("")
            //Thread.sleep(2000)
            check.lockProperty.value = true
        }
    }

    fun delimiter(str : String, delimiter: PartDelimiter) : String{
        if (delimiter.equals(PartDelimiter.DEFAULT).not()) str.replace(" ",delimiter.delimiter, true)
        return str
    }
}