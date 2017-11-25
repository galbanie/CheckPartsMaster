package com.github.galbanie.views

import com.github.galbanie.CheckPartsMasterApp
import com.github.galbanie.CheckPartsMasterScope
import com.github.galbanie.CheckPartsMasterWorkspace
import com.github.galbanie.Styles
import javafx.geometry.Insets
import tornadofx.*
import javafx.scene.control.TabPane
import javafx.stage.Stage

/**
 * Created by Galbanie on 2017-07-31.
 */
class Configuration : View("Configuration") {
    val generalConfView : GeneralConf by inject()
    val databaseConfView : DatabaseConf by inject()
    lateinit var tabPane : TabPane

    init {

    }

    override val root = borderpane {
        //prefHeight = 540.0
        //prefWidth = 540.0
        center {
            tabPane = tabpane {
                tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                tab(generalConfView){

                }
                tab(databaseConfView){

                }
            }
        }

        bottom {
            padding = Insets(10.0)
            buttonbar {
                button("Reload").action {
                    primaryStage.close()
                    //app.stop()
                    runLater {
                        CheckPartsMasterApp().start(Stage())
                    }
                }
                button("Finish").action {
                    close()
                }
            }
        }
    }
}
