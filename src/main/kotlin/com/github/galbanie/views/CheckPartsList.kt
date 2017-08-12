package com.github.galbanie.views

import com.github.galbanie.CheckPartsListFound
import com.github.galbanie.CheckPartsListRequest
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Result
import tornadofx.*
import javafx.scene.control.ListView

/**
 * Created by Galbanie on 2017-07-31.
 */
class CheckPartsList : View("Check Parts") {

    val checkPartsModel : CheckPartsModel by inject()

    override val root = ListView<CheckParts>()

    init {
        with(root){
            cellFormat {
                textProperty().bind(it.nameProperty)
            }
            subscribe<CheckPartsListFound> {
                items.setAll(it.checks)
            }
            bindSelected(checkPartsModel)
            onUserSelect(2){
                workspace.dockInNewScope<CheckPartsArea>(params = mapOf(CheckPartsArea::checkParts to selectedItem))
                selectionModel.clearSelection()
            }
            whenDocked {
                fire(CheckPartsListRequest)
            }
        }
    }
}
