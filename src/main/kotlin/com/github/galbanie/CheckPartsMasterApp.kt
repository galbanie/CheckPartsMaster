package com.github.galbanie

import com.github.galbanie.controllers.CheckPartsMasterController
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.*

class CheckPartsMasterApp : App(CheckPartsMasterWorkspace::class, Styles::class, CheckPartsMasterScope()){
    val controller : CheckPartsMasterController by inject(scope)
    init {
        reloadStylesheetsOnFocus()
        controller
    }
    override fun start(stage: Stage) {
        super.start(stage)
        FX.primaryStage.icons += resources.image("/com/github/galbanie/checkmark_on_circle.png")
    }
    override fun createPrimaryScene(view: UIComponent) = Scene(view.root, 1024.0, 768.0)
    override fun onBeforeShow(view: UIComponent) {
        fire(InitDataSource)
        configuration()
        try {
            controller.connectDatabase()
            controller.createTables()
        }
        catch (e : org.h2.jdbc.JdbcSQLException){

        }
    }
    private fun configuration(){
        // Database
        if (config.containsKey("database.url").not() or config.string("database.url", "").isNullOrBlank() or config.string("database.url", "").isNullOrEmpty()){
            with(config){
                set("database.url", "jdbc:h2:file:./db/cpm;DB_CLOSE_DELAY=-1;IFEXISTS=TRUE")
                set("database.type", "file")
                set("database.user","")
                set("database.password","")
                set("database.driver","org.h2.Driver")
                set("database.path","./db/cpm")
                set("database.options","DB_CLOSE_DELAY=-1;IFEXISTS=TRUE")
                save()
            }
        }
    }
}

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    Application.launch(CheckPartsMasterApp::class.java, *args)
}