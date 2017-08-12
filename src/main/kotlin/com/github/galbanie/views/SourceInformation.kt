package com.github.galbanie.views

import com.github.galbanie.models.SourceModel
import com.github.galbanie.utils.PartDelimiter
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.TextField
import org.jsoup.Connection
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-09.
 */
class SourceInformation : View("Source Information") {
    val sourceModel : SourceModel by inject()
    lateinit var urltextfield : TextField
    override val root = form{
        fieldset("Source Info") {
            labelPosition = javafx.geometry.Orientation.VERTICAL
            field("Name : ") {
                textfield(sourceModel.name){
                    required()
                }
            }
            field("Url : ") {
                urltextfield = textfield(sourceModel.url){
                    required()
                }
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT)) {
                    action {
                        find(EditTextArea::class,params = mapOf(EditTextArea::node to urltextfield)).openModal()
                    }
                }
            }
            field("Http Method") {
                combobox<Connection.Method>(sourceModel.method) {
                    items.setAll(Connection.Method.GET, Connection.Method.POST)
                }
            }
            field("Delimiter : ") {
                combobox<PartDelimiter>(sourceModel.delimiter) {
                    items.setAll(PartDelimiter.values().toMutableList())
                }
            }
        }
    }
}
