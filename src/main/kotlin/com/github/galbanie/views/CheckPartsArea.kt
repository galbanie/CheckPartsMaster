package com.github.galbanie.views

import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Part
import com.github.galbanie.models.Source
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Insets
import javafx.geometry.Orientation
import tornadofx.*

/**
 * Created by Galbanie on 2017-07-31.
 */
class CheckPartsArea : Fragment() {

    val checkParts : CheckParts by param()
    val checkPartsModel : CheckPartsModel by inject()

    init {
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
                        this@CheckPartsArea.root.center.replaceWith(find<ResultsList>(params = mapOf(ResultsList::checkPartsId to checkPartsModel.item.id)).root)
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
                        this@CheckPartsArea.root.center.replaceWith(find<ResultsTable>(params = mapOf(ResultsList::checkPartsId to checkPartsModel.item.id)).root)
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
                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE).apply {
                        style {
                            fill = c("#818181")
                        }
                        glyphSize = 18
                    }
                    action {  }
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

    override val root = borderpane {
        left{
            splitpane(Orientation.VERTICAL){
                padding = Insets(10.0)
                setDividerPosition(0,0.5)
                listview<Part>(checkPartsModel.parts) {

                }
                listview<Source>(checkPartsModel.sources) {

                }
            }
        }
        center = find<ResultsList>(params = mapOf(ResultsList::checkPartsId to checkPartsModel.item.id)).root

    }
}
