package com.github.galbanie.views

import com.github.galbanie.SourceListFound
import com.github.galbanie.SourceListRequest
import com.github.galbanie.models.Source
import com.github.galbanie.models.SourceModel
import tornadofx.*
import javafx.scene.control.ListView

/**
 * Created by Galbanie on 2017-07-31.
 */
class SourcesList : View("Source") {
    val sourceModel : SourceModel by inject()
    override val root = ListView<Source>()
    init {
        with(root){
            cellFormat {
                textProperty().bind(it.nameProperty)
            }
            subscribe<SourceListFound> {
                items.setAll(it.sources)
            }
            bindSelected(sourceModel)
            onUserSelect(2){
                workspace.dockInNewScope<SourceArea>(params = mapOf(SourceArea::source to selectedItem))
                selectionModel.clearSelection()
            }
            whenDocked {
                fire(SourceListRequest)
            }
        }
    }
}
