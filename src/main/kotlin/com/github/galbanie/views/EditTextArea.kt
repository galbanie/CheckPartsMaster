package com.github.galbanie.views

import javafx.scene.Node
import javafx.scene.control.ButtonBar
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-10.
 */
class EditTextArea : View("Edit") {
    val node : Node by param()

    lateinit var areatext : TextArea

    override val root = borderpane {
        center {
            areatext = textarea {
                when(node){
                    is TextField ->  textProperty().bindBidirectional((node as TextField).textProperty())
                }
                isWrapText = true
            }
        }
        bottom {
            buttonbar {
                style{
                    padding = box(10.px)
                }
                button("Close", ButtonBar.ButtonData.RIGHT).setOnAction {
                    close()
                }
            }
        }
    }
}
