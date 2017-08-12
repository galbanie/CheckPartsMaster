package com.github.galbanie.views

import com.github.galbanie.models.SourceModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Orientation
import javafx.scene.control.TextField
import tornadofx.*
import tornadofx.Form

/**
 * Created by Galbanie on 2017-08-10.
 */
class SourcePagination : View("Pagination") {
    val sourceModel : SourceModel by inject()
    lateinit var linkpagiselectortextfield : TextField
    lateinit var urlquerypagitextfield : TextField
    override val root = form{
        fieldset {
            labelPosition = Orientation.VERTICAL
            field("Pagination") {
                checkbox(property = sourceModel.pagination).action {

                }
            }
            field("Link Pagination (css query) : ") {
                enableWhen { sourceModel.pagination }
                linkpagiselectortextfield = textfield(sourceModel.linkPaginationSelector){}
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT)) {
                    action {
                        find(EditTextArea::class,params = mapOf(EditTextArea::node to linkpagiselectortextfield)).openModal()
                    }
                }
            }
            field("Url Query Pagination : ") {
                enableWhen { sourceModel.pagination }
                urlquerypagitextfield = textfield(sourceModel.urlPagination){}
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT)) {
                    action {
                        find(EditTextArea::class,params = mapOf(EditTextArea::node to urlquerypagitextfield)).openModal()
                    }
                }
            }
            field("Items per page : "){
                enableWhen { sourceModel.pagination }
                spinner<Int>(min = 0, max = 50, initialValue = 10, amountToStepBy = 1, editable = true, property = sourceModel.numberElementPagination)
            }
        }
    }
}
