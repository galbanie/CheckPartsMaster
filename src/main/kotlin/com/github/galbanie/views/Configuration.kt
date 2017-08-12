package com.github.galbanie.views

import javafx.geometry.Insets
import tornadofx.*
import javafx.scene.control.TabPane

/**
 * Created by Galbanie on 2017-07-31.
 */
class Configuration : View() {

    val generalConfView : GeneralConf by inject()
    val databaseConfView : DatabaseConf by inject()

    init {

    }

    override val root = borderpane {
        prefHeight = 540.0
        prefWidth = 540.0
        center {
            tabpane {
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
                //button("Save")
                button("Finish").action {
                    close()
                }
            }
        }
    }
}
