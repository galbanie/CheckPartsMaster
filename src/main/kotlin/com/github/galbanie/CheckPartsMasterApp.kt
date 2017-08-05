package com.github.galbanie

import com.github.galbanie.controllers.CheckPartsMasterController
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.*

class CheckPartsMasterApp : App(CheckPartsMasterWorkspace::class, Styles::class, CheckPartsMasterScope()){
    init {
        reloadStylesheetsOnFocus()
        try {
            tornadofx.find<CheckPartsMasterController>(scope)
        }
        catch (e : org.h2.jdbc.JdbcSQLException){

        }
    }
    override fun start(stage: Stage) {
        super.start(stage)
        tornadofx.FX.Companion.primaryStage.icons += resources.image("/com/github/galbanie/checkmark_on_circle.png")
    }
    override fun createPrimaryScene(view: UIComponent) = Scene(view.root, 1024.0, 768.0)
}

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    Application.launch(CheckPartsMasterApp::class.java, *args)
}