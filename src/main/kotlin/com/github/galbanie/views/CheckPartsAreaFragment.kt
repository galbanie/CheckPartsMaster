package com.github.galbanie.views

import com.github.galbanie.*
import com.github.galbanie.controllers.CheckPartsController
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Part
import com.github.galbanie.models.Source
import com.github.galbanie.utils.Action
import com.github.galbanie.utils.ActionFile
import com.github.galbanie.utils.ResultViewType
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.*
import javafx.scene.layout.StackPane
import javafx.stage.FileChooser
import javafx.util.converter.DefaultStringConverter
import tornadofx.*
import kotlin.reflect.full.createInstance

/**
 * Created by Galbanie on 2017-07-31.
 */
class CheckPartsAreaFragment : Fragment() {
    val checkParts : CheckParts by param()
    val checkPartsModel : CheckPartsModel by inject()
    lateinit var partsListView : ListView<Part>
    lateinit var sourcesListView : ListView<Source>
    lateinit var partsTableView : TableView<Part>
    lateinit var resultView : StackPane
    val status : TaskStatus by inject(scope)
    init {
        checkPartsModel.item = checkParts
        titleProperty.bind(checkPartsModel.name)
        checkPartsModel.item.resultViewTypeProperty.onChange {
            when(it){
                ResultViewType.LIST -> resultView.replaceChildren(find<ResultsListFragment>(params = mapOf(ResultsListFragment::check to checkPartsModel.item)).root)
                ResultViewType.TABLE -> resultView.replaceChildren(find<ResultsTableFragment>(params = mapOf(ResultsTableFragment::check to checkPartsModel.item)).root)
                ResultViewType.ITEM -> resultView.replaceChildren(find<ResultsItemFragment>(params = mapOf(ResultsItemFragment::check to checkPartsModel.item)).root)
            }
        }
    }
    override fun onDock() {
        checkPartsModel.item.lockProperty.onChange {

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
                        checkPartsModel.item.resultViewTypeProperty.value = ResultViewType.LIST
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
                        checkPartsModel.item.resultViewTypeProperty.value = ResultViewType.TABLE
                    }
                }
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.IMAGE).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        checkPartsModel.item.resultViewTypeProperty.value = ResultViewType.ITEM
                    }
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
                button {
                    disableWhen(status.running)
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.GEAR).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        partsTableView.selectionModel.clearSelection()
                        sourcesListView.selectionModel.clearSelection()
                        fire(SearchRequest(""))
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
                            }
                            checkPartsModel.item.parts.removeAll(parts)
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
            hbox(4.0) {
                progressbar(status.progress)
                label(status.message)
                enableWhen { status.running }
                visibleWhen { status.running }
                paddingAll = 4
            }
        }
    }

    //override val closeable = checkPartsModel.dirty.not()

    override val root = borderpane {
        center{
            splitpane{
                setDividerPositions(0.3)
                splitpane(Orientation.VERTICAL){
                    setDividerPositions(0.7)
                    padding = Insets(10.0)
                    setDividerPosition(0,0.5)
                    partsTableView = tableview(checkPartsModel.parts) {
                        isTableMenuButtonVisible = true
                        //selectionModelProperty().onChange { println("test") }
                        onUserSelect(1) {
                            //fire(SearchRequest("",parts = selectionModel.selectedItems.map { it.part }, sources = sourcesListView.selectionModel.selectedItems.map { it.name }))
                            if(selectedItem != null) fire(SearchRequest(selectedItem!!.part))
                        }
                        selectionModel.selectionMode = SelectionMode.MULTIPLE
                        isEditable = true
                        column("", Part::checkProperty).apply {
                            prefWidth(25.0)
                            cellFormat {
                                if(it){
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
                            prefWidth(125.0)
                            useTextField(DefaultStringConverter())
                        }
                        column("Sources", Part::sourcesProperty).apply {
                            prefWidth(100.0)
                            cellFormat {
                                if(it.isNotEmpty()){
                                    text = rowItem.sources.joinToString()
                                }
                                else text = "No Source"
                            }
                        }
                        columnResizePolicy = SmartResize.POLICY
                    }
                    sourcesListView = listview<Source>(checkPartsModel.sources) {
                        onUserSelect(1) {
                            //fire(SearchRequest("",parts = partsTableView.selectionModel.selectedItems.map { it.part }, sources = selectionModel.selectedItems.map { it.name }))
                            if(selectedItem != null) fire(SearchRequest(selectedItem!!.name))
                        }
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
                    }
                }
                resultView = stackpane {
                    when(checkPartsModel.resultViewType.value){
                        ResultViewType.LIST -> this += find<ResultsListFragment>(params = mapOf(ResultsListFragment::check to checkPartsModel.item)).root
                        ResultViewType.TABLE -> this += find<ResultsTableFragment>(params = mapOf(ResultsTableFragment::check to checkPartsModel.item)).root
                        ResultViewType.ITEM -> this += find<ResultsItemFragment>(params = mapOf(ResultsItemFragment::check to checkPartsModel.item)).root
                        else -> this += find<ResultsListFragment>(params = mapOf(ResultsListFragment::check to checkPartsModel.item)).root
                    }
                }

            }
        }
    }
    override fun onSave() {
        fire(CheckPartsSelectedListFound(listOf(checkPartsModel.item)))
        fire(ChooseFileActionEvent("Save Web Check Parts", arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")), FileChooserMode.Save, ActionFile.saveCheckToXml))
    }

    override fun onRefresh() {
        fire(CheckPartsListRequest)
    }

    override fun onDelete() {
        confirmation("Do you really want to delete?", "", ButtonType.YES, ButtonType.NO) {
            fire(CheckPartsRemoved(checkPartsModel.item))
        }
        close()
    }
}