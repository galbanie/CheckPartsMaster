package com.github.galbanie.views

import com.github.galbanie.CheckPartsCreated
import com.github.galbanie.CheckPartsUpdated
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.utils.Action
import javafx.geometry.Insets
import javafx.scene.control.TextField
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-10.
 */
class CheckPartsEditor : Fragment() {
    val check : CheckParts? by nullableParam()
    val action : Action by param()
    val checkPartsModel : CheckPartsModel by inject()
    init {
        checkPartsModel.item = check ?: CheckParts()
        when(action){
            Action.CREATE -> title = "New Check Parts"
            Action.EDIT -> title = "Rename"
        }
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
                button("Apply").action {
                    checkPartsModel.commit{
                        when(action){
                            Action.CREATE -> fire(CheckPartsCreated(checkPartsModel.item))
                            Action.EDIT -> fire(CheckPartsUpdated(checkPartsModel.item))
                        }

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
