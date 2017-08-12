package com.github.galbanie.views

import com.github.galbanie.PartsListFound
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Part
import com.github.galbanie.models.Source
import com.github.galbanie.utils.PartListCell
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import javafx.scene.control.Tab
import tornadofx.*

/**
 * Created by Galbanie on 2017-07-31.
 */
class CheckPartsArea : Fragment() {

    val checkParts : CheckParts by param()
    val checkPartsModel = CheckPartsModel()
    lateinit var partsListView : ListView<Part>

    init {
        checkPartsModel.item = checkParts
        titleProperty.bindBidirectional(checkPartsModel.name)
    }

    override fun onDock() {
        with(workspace){
            hbox {
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.LIST).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {
                        root.center.replaceWith(find<ResultsList>(params = mapOf(ResultsList::checkPartsId to checkPartsModel.item.id)).root)
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
                        root.center.replaceWith(find<ResultsTable>(params = mapOf(ResultsList::checkPartsId to checkPartsModel.item.id)).root)
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
                        find<PartsAdd>(params = mapOf(PartsAdd::checkPartsId to checkPartsModel.item.id)).openModal()
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
                    action {  }
                }
                button {
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.ASTERISK).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {  }
                }
            }
        }
    }

    override fun onUndock() {
        super.onUndock()
        //println(workspace.tabContainer.tabs)
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
                    /*subscribe<PartsListFound> {
                        println(it.checkId)
                        if (it.checkId.equals(checkPartsModel.item.id)){
                            items.setAll(it.parts)
                        }
                    }*/
                }
                listview<Source>(checkPartsModel.sources) {

                }
            }
        }
        center = find<ResultsList>(params = mapOf(ResultsList::checkPartsId to checkPartsModel.item.id)).root

    }
}
