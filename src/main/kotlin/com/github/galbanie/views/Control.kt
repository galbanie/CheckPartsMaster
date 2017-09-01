package com.github.galbanie.views

import com.github.galbanie.models.Part
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-07.
 */
class Control : View() {
    //val checkPartsArea : CheckPartsArea by inject()
    lateinit var dockedComponent : UIComponent
    val available = SimpleBooleanProperty(false)

    init {
        /*workspace.dockedComponentProperty.onChange { child ->
            if (child != null){

                when(child){
                    is CheckPartsArea -> available.value = true
                    else -> available.value = false
                }
            }
        }*/
    }
    override val root = hbox {
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
                var cpa = workspace?.dockedComponent
                if(cpa != null) {
                    cpa as CheckPartsArea
                    find<PartsAdd>(params = mapOf(PartsAdd::checkId to cpa.checkPartsModel.item.id)).openModal()
                }
            }
        }
        button {
            //disableProperty().bind(available.not())
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
            //disableProperty().bind(available.not())
            addClass("icon-only")
            graphic = FontAwesomeIconView(FontAwesomeIcon.ASTERISK).apply {
                style {
                    fill = c("#818181")
                }
                glyphSize = 18
            }
            action {  }
        }
        spacer()
        button {
            //disableProperty().bind(available.not())
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
            //disableProperty().bind(available.not())
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
        spacer()
        button {
            //disableProperty().bind(available.not())
            addClass("icon-only")
            graphic = FontAwesomeIconView(FontAwesomeIcon.LIST).apply {
                style {
                    fill = c("#818181")
                }
                glyphSize = 18
            }
            action {
                var cpa = workspace?.dockedComponent as CheckPartsArea
                if(cpa != null) cpa.root.center.replaceWith(find<ResultsList>(params = mapOf(ResultsList::check to cpa.checkPartsModel.item)).root)
            }
        }
        button {
            //disableProperty().bind(available.not())
            addClass("icon-only")
            graphic = FontAwesomeIconView(FontAwesomeIcon.TABLE).apply {
                style {
                    fill = c("#818181")
                }
                glyphSize = 18
            }
            action {
                var cpa = workspace?.dockedComponent as CheckPartsArea
                if(cpa != null) cpa.root.center.replaceWith(find<ResultsTable>(params = mapOf(ResultsList::check to cpa.checkPartsModel.item)).root)
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
}
