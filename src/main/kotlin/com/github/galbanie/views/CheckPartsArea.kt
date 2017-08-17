package com.github.galbanie.views

import com.github.galbanie.*
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Part
import com.github.galbanie.models.Source
import com.github.galbanie.utils.PartListCell
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.ButtonType
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import javafx.scene.control.Tab
import javafx.scene.input.TransferMode
import tornadofx.*

/**
 * Created by Galbanie on 2017-07-31.
 */
class CheckPartsArea : Fragment() {

    val checkParts : CheckParts by param()
    val checkPartsModel : CheckPartsModel by inject()
    lateinit var partsListView : ListView<Part>
    lateinit var sourcesListView : ListView<Source>
    override val savable = SimpleBooleanProperty(false)
    init {
        checkPartsModel.item = checkParts
        titleProperty.bind(checkPartsModel.name)
    }
    override fun onDock() {
        with(workspace){
            hbox {
                visibleWhen { workspace.dockedComponentProperty.booleanBinding{it != null} }
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.LIST).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        (workspace.dockedComponent as CheckPartsArea).root.center.replaceWith(find<ResultsList>(params = mapOf(ResultsList::checkId to checkPartsModel.item.id)).root)
                    }
                }
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.TABLE).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        (workspace.dockedComponent as CheckPartsArea).root.center.replaceWith(find<ResultsTable>(params = mapOf(ResultsList::checkId to checkPartsModel.item.id)).root)
                    }
                }
                button {
                    //disableProperty().bind(available.not())
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.IMAGE).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {  }
                }
            }
            hbox {
                visibleWhen { workspace.dockedComponentProperty.booleanBinding{it != null} }
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.PLAY).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {  }
                }
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.STOP).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {  }
                }
                button {
                    //disableProperty().bind(available.not())
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.ERASER).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {  }
                }
            }
            hbox {
                visibleWhen { workspace.dockedComponentProperty.booleanBinding{it != null} }
                button {
                    //disableProperty().bind(available.not())
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        find<PartsAdd>(params = mapOf(PartsAdd::checkId to checkPartsModel.item.id)).openModal()
                    }
                }
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.MINUS_CIRCLE).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        if(partsListView.selectionModel.isEmpty){
                            checkPartsModel.item.parts.clear()
                        }
                        else checkPartsModel.item.parts.removeAll(partsListView.selectionModel.selectedItems)
                    }
                }
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.ASTERISK).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        if(partsListView.selectionModel.isEmpty){
                            checkPartsModel.item.parts.forEach{
                                it.check = false
                            }
                        }
                        else {
                            partsListView.selectionModel.selectedItems.forEach { part ->
                                checkPartsModel.item.parts.find { it.equals(part) }!!.initialize()
                            }
                        }
                    }
                }
            }
            hbox {
                visibleWhen { workspace.dockedComponentProperty.booleanBinding{it != null} }
                button {
                    //disableProperty().bind(available.not())
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        find<ChooseSource>(params = mapOf(ChooseSource::checkId to checkPartsModel.item.id)).openModal()
                    }
                }
                button {
                    //disableProperty().bind(available.not())
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.MINUS).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        checkPartsModel.item.sources.removeAll(sourcesListView.selectionModel.selectedItems)
                    }
                }
            }
        }
    }

    //override val closeable = checkPartsModel.dirty.not()

    override val root = borderpane {
        left{
            splitpane(Orientation.VERTICAL){
                padding = Insets(10.0)
                setDividerPosition(0,0.5)
                partsListView = listview<Part>(checkPartsModel.parts) {
                    setCellFactory { PartListCell(checkPartsModel.item.id) }
                    isEditable = true
                    selectionModel.selectionMode = SelectionMode.MULTIPLE
                }
                sourcesListView = listview<Source>(checkPartsModel.sources) {
                    cellCache {
                        label(it.nameProperty) {
                            tooltip {
                                textProperty().bind(it.urlProperty)
                            }
                        }
                    }
                    selectionModel.selectionMode = SelectionMode.MULTIPLE
                    setOnDragOver {
                        if (it.dragboard.hasString()){
                            it.acceptTransferModes(TransferMode.MOVE)
                        }
                    }
                    setOnDragEntered {
                        println("Entered")
                    }
                    setOnDragDone {

                    }
                    setOnDragDropped {
                        fire(SourceListRequest)
                        if (it.dragboard.hasString() && it.dragboard.string.equals("sources")){
                            subscribe<DropSource> {
                                println("Drag Dropped")
                                println(it.sources)
                                items.addAll(it.sources)
                            }
                            it.isDropCompleted = true
                        }
                        else it.isDropCompleted = false
                    }
                }
            }
        }
        center = find<ResultsList>(params = mapOf(ResultsList::checkId to checkPartsModel.item.id)).root

    }
    override fun onSave() {
        checkPartsModel.commit{

        }
    }

    override fun onRefresh() {
        fire(CheckPartsListRequest)
    }

    override fun onDelete() {
        confirmation("Do you really want to delete?","", ButtonType.YES, ButtonType.NO){
            fire(CheckPartsRemoved(checkPartsModel.item))
        }
        close()
    }
}
