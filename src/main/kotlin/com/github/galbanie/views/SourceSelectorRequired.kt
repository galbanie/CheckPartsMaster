package com.github.galbanie.views

import com.github.galbanie.models.SourceModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.binding.BooleanExpression
import javafx.geometry.Orientation
import javafx.scene.control.TextField
import tornadofx.*
import tornadofx.Form

/**
 * Created by Galbanie on 2017-08-10.
 */
class SourceSelectorRequired : View("Selector Required") {
    val sourceModel : SourceModel by inject()
    lateinit var elementselectortextfield : TextField
    lateinit var titreselectortextfield : TextField
    lateinit var urlselectortextfield : TextField
    override val complete = sourceModel.valid(sourceModel.elementSelector,sourceModel.titreSelector,sourceModel.urlSelector)
    override val root = form {
        fieldset("Selector") {
            labelPosition = Orientation.VERTICAL
            field("Element Part (css query) : ") {
                elementselectortextfield = textfield(sourceModel.elementSelector){
                    required()
                }
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT)) {
                    action {
                        find(EditTextArea::class,params = mapOf(EditTextArea::node to elementselectortextfield)).openModal()
                    }
                }
            }
            field("Titre Part (css query) : ") {
                titreselectortextfield = textfield(sourceModel.titreSelector){
                    required()
                }
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT)) {
                    action {
                        find(EditTextArea::class,params = mapOf(EditTextArea::node to titreselectortextfield)).openModal()
                    }
                }
            }
            field("Url Part (css query) : ") {
                urlselectortextfield = textfield(sourceModel.urlSelector){
                    required()
                }
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT)) {
                    action {
                        find(EditTextArea::class,params = mapOf(EditTextArea::node to urlselectortextfield)).openModal()
                    }
                }
            }
        }
    }
}
