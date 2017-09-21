package com.github.galbanie.views

import com.github.galbanie.*
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Part
import com.github.galbanie.models.Source
import com.github.galbanie.utils.Action
import com.github.galbanie.utils.ActionFile
import com.github.galbanie.utils.PartListCell
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventType
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import javafx.util.converter.DefaultStringConverter
import tornadofx.*

/**
 * Created by Galbanie on 2017-07-31.
 */
class CheckPartsArea : Fragment() {
    val checkParts : CheckParts by param()
    val checkPartsModel : CheckPartsModel by inject()
    lateinit var partsListView : ListView<Part>
    lateinit var sourcesListView : ListView<Source>
    lateinit var partsTableView : TableView<Part>
    //override val savable = SimpleBooleanProperty(false)
    val status : TaskStatus by inject(scope)
    init {
        checkPartsModel.item = checkParts
        titleProperty.bind(checkPartsModel.name)
        //search.data.bindTo()
    }
    override fun onDock() {
        checkPartsModel.item.lockProperty.onChange {
            //println(it)
        }
        with(workspace){
            button {
                addClass("icon-only")
                graphic = FontAwesomeIconView(FontAwesomeIcon.PENCIL).apply {
                    style {
                        fill = c("#818181")
                    }
                    glyphSize = 18
                }
                action {
                    find<CheckPartsEditor>(params = mapOf(CheckPartsEditor::action to Action.EDIT, CheckPartsEditor::check to checkPartsModel.item)).openModal()
                }
            }
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
                        (workspace.dockedComponent as CheckPartsArea).root.center.replaceWith(find<ResultsList>(params = mapOf(ResultsList::check to checkPartsModel.item)).root)
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
                        (workspace.dockedComponent as CheckPartsArea).root.center.replaceWith(find<ResultsTable>(params = mapOf(ResultsList::check to checkPartsModel.item)).root)
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
                    disableWhen(status.running)
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.PLAY).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        if(checkPartsModel.item.parts.isNotEmpty() and checkPartsModel.item.sources.isNotEmpty()) fire(Run(scope))
                    }
                }
                button {
                    enableWhen(status.running)
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.STOP).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        fire(Stop)
                    }
                }
                button {
                    disableWhen(status.running)
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.ERASER).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        fire(Clear)
                    }
                }
            }
            hbox {
                visibleWhen { workspace.dockedComponentProperty.booleanBinding{it != null} }
                button {
                    disableWhen(status.running)
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
                    disableWhen(status.running)
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.MINUS_CIRCLE).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        if(partsTableView.selectionModel.isEmpty){
                            checkPartsModel.item.parts.clear()
                            checkPartsModel.item.results.clear()
                        }
                        else {
                            val parts = partsTableView.selectionModel.selectedItems
                            println(parts)
                            parts.forEach { part ->
                                checkPartsModel.item.results.removeAll(checkPartsModel.item.results.filter { it.part.equals(part.part) })
                                //checkPartsModel.item.parts.remove(part)
                            }
                            checkPartsModel.item.parts.removeAll(parts)
                        }
                    }
                }
                button {
                    disableWhen(status.running)
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.ASTERISK).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        if(partsTableView.selectionModel.isEmpty){
                            checkPartsModel.item.parts.forEach{
                                it.check = false
                            }
                            checkPartsModel.item.results.clear()
                        }
                        else {
                            partsTableView.selectionModel.selectedItems.forEach { part ->
                                checkPartsModel.item.parts.find { it.equals(part) }!!.initialize()
                                checkPartsModel.item.results.removeAll(checkPartsModel.item.results.filter { it.part.equals(part.part) })
                            }
                        }
                    }
                }
            }
            hbox {
                visibleWhen { workspace.dockedComponentProperty.booleanBinding{it != null} }
                button {
                    disableWhen(status.running)
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
                    disableWhen(status.running)
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
                /*partsListView = listview<Part>(checkPartsModel.parts) {
                    //setCellFactory { PartListCell(checkPartsModel.item.id) }
                    /*cellFormat {
                        if (it == null){
                            text = null
                            graphic = null
                        }
                        else{
                            graphic = hbox(10) {
                                it.checkProperty.onChange {
                                    if (it){
                                        style {
                                            backgroundColor += Color.GREENYELLOW
                                        }
                                    }
                                    else{
                                        style {
                                            backgroundColor += Color.GRAY
                                        }
                                    }
                                }
                                button {
                                    graphic = FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT).apply {
                                        style {
                                            fill = c("#818181")
                                        }
                                        //glyphSize = 18
                                    }
                                    action {

                                    }
                                }
                                label(it.partProperty)
                            }
                        }
                    }*/
                    isEditable = true
                    selectionModel.selectionMode = SelectionMode.MULTIPLE
                    /*focusedProperty().onChange {
                        if(!it){
                            selectionModel.clearSelection()
                        }
                    }*/
                }*/
                partsTableView = tableview(checkPartsModel.parts) {
                    selectionModel.selectionMode = SelectionMode.MULTIPLE
                    isEditable = true
                    column("",Part::checkProperty).apply {
                        pctWidth(10.0)
                        cellFormat {
                            if(it){
                                /*if (rowItem.sources.isNotEmpty()){

                                }
                                else{

                                }*/
                                graphic = if (rowItem.sources.isNotEmpty()) FontAwesomeIconView(FontAwesomeIcon.CHECK).apply {
                                    style {
                                        fill = c("#818181")
                                    }
                                    glyphSize = 14
                                } else FontAwesomeIconView(FontAwesomeIcon.TIMES).apply {
                                    style {
                                        fill = c("#818181")
                                    }
                                    glyphSize = 14
                                }
                            }
                            else{
                                graphic = FontAwesomeIconView(FontAwesomeIcon.CIRCLE_ALT).apply {
                                    style {
                                        fill = c("#818181")
                                    }
                                    glyphSize = 14
                                }
                            }
                        }
                    }
                    column("Part", Part::part){
                        useTextField(DefaultStringConverter())
                        pctWidth(60.0)
                    }
                    column("Sources", Part::sourcesProperty).apply {
                        cellFormat {
                            if(it.isNotEmpty()){
                                text = rowItem.sources.joinToString()
                            }
                            else text = "No Source"
                        }
                        pctWidth(30.0)
                    }
                    columnResizePolicy = SmartResize.POLICY
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
                    /*focusedProperty().onChange {
                        if(!it){
                            selectionModel.clearSelection()
                        }
                    }*/
                    /*setOnDragOver {
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
                    }*/
                }
            }
        }
        center = find<ResultsList>(params = mapOf(ResultsList::check to checkPartsModel.item)).root

    }
    override fun onSave() {
        //checkPartsModel.commit{
            fire(CheckPartsSelectedListFound(listOf(checkPartsModel.item)))
        //}
        fire(ChooseFileActionEvent("Save Web Check Parts", arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")), FileChooserMode.Save, ActionFile.saveCheckToXml))
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
