package com.github.galbanie.views

import com.github.galbanie.models.SourceModel
import com.github.galbanie.utils.PartDelimiter
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.binding.BooleanExpression
import javafx.scene.control.TextField
import org.jsoup.Connection
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-09.
 */
class SourceInformation : View("Source Information") {
    val sourceModel : SourceModel by inject()
    lateinit var urltextfield : TextField
    override val complete = sourceModel.valid(sourceModel.name,sourceModel.url)
    override val root = form{
        fieldset("Source Information") {
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
        fieldset("Proxy") {
            field("Active") {
                checkbox("", sourceModel.activeProxy) {

                }
            }
            hbox(25) {
                field("Address") {
                    textfield(sourceModel.proxyAddress) {

                    }

                }
                field("Port") {
                    /*textfield(sourceModel.proxyPort) {
                        style {
                            maxWidth = 90.px
                            minWidth = maxWidth
                        }
                    }*/
                    spinner<Number>(min = 0, max = 65535, initialValue = 0, amountToStepBy = 1, editable = true, property = sourceModel.proxyPort){
                        style {
                            maxWidth = 90.px
                            minWidth = maxWidth
                        }
                    }
                }
                field {
                    button(graphic = FontAwesomeIconView(FontAwesomeIcon.LIST)) {
                        action {
                            find<Browser>(params = mapOf(Browser::url to "http://proxydb.net")).openWindow()
                        }
                    }
                }
            }
        }
        fieldset("Timeout") {
            field("Millisecond") {
                spinner<Number>(min = 0, max = 30000, initialValue = 0, amountToStepBy = 500, editable = true, property = sourceModel.timeout){

                }
            }
        }
        fieldset("latency") {
            field("Millisecond") {
                spinner<Number>(min = 0, max = 14000, initialValue = 0, amountToStepBy = 500, editable = true, property = sourceModel.latency){

                }
            }
        }
    }
}
