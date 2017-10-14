package com.github.galbanie.views

import com.github.galbanie.CheckPartsMasterScope
import com.github.galbanie.DragSource
import com.github.galbanie.SourceListFound
import com.github.galbanie.SourceListRequest
import com.github.galbanie.models.Source
import com.github.galbanie.models.SourceModel
import com.github.galbanie.utils.Action
import javafx.beans.property.SimpleListProperty
import javafx.scene.control.ContextMenu
import tornadofx.*
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DataFormat
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode

/**
 * Created by Galbanie on 2017-07-31.
 */
class SourcesList : View("Source") {
    val sourceModel : SourceModel by inject()
    //val listDataFormat = DataFormat("listOfSources")
    override val root = ListView<Source>()
    init {
        with(root){
            selectionModel.selectionMode = SelectionMode.MULTIPLE
            cellCache {
                //textProperty().bind(it.nameProperty)
                label {
                    textProperty().bind(it.nameProperty)
                    tooltip {
                        textProperty().bind(it.urlProperty)
                    }
                }
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
            /*focusedProperty().onChange {
                if(!it){
                    selectionModel.clearSelection()
                }
            }*/
            /*setOnDragDetected {
                println("Drag")
                var cpa = workspace.dockedComponent
                if(cpa != null && !selectionModel.selectedItems.isEmpty()){
                    //cpa as CheckPartsArea
                    val dragBoard = startDragAndDrop(TransferMode.MOVE)
                    val content = ClipboardContent()
                    //content.put(listDataFormat, arrayListOf(selectionModel.selectedItems))
                    //dragBoard.setContent(content)
                    content.putString("sources")
                    dragBoard.setContent(content)
                    fire(DragSource(selectionModel.selectedItems))
                    it.consume()
                }
            }*/
        }
    }
}
