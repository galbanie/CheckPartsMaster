package com.github.galbanie.views

import com.github.galbanie.CheckPartsCreated
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import javafx.geometry.Insets
import javafx.scene.control.TextField
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-10.
 */
class NewCheckParts : View("New Check Parts") {
    val checkPartsModel = CheckPartsModel()
    init {
        checkPartsModel.item = CheckParts()
    }
    override val root = borderpane {
        center{
            form{
                fieldset {
                    field("Name") {
                        textfield(checkPartsModel.name) {
                            required()
                        }
                    }
                }
            }
        }
        bottom {
            padding = Insets(10.0)
            buttonbar {
                button("Create").action {
                    checkPartsModel.commit{
                        fire(CheckPartsCreated(checkPartsModel.item))
                        close()
                    }
                }
                button("Cancel").action {
                    close()
                }
            }
        }
    }
}
