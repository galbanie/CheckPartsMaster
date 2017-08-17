package com.github.galbanie.views

import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.SourceModel
import com.github.galbanie.utils.EditStringListCell
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Orientation
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextField
import tornadofx.*
import tornadofx.Form

/**
 * Created by Galbanie on 2017-08-10.
 */
class SourceSelectorOptional : View("Selector Optional") {
    val sourceModel : SourceModel by inject()
    lateinit var desclistview : ListView<String>
    lateinit var imglistview : ListView<String>
    lateinit var descadditemtextfield : TextField
    lateinit var imgadditemtextfield : TextField
    override val root = form {
        fieldset {
            labelPosition = Orientation.VERTICAL
            hbox(20) {
                field("Descriptions Part (css query) : ") {
                    vbox {
                        useMaxWidth = true
                        desclistview = listview<String> {
                            setCellFactory {EditStringListCell()}
                            isEditable = true
                            selectionModel.selectionMode = SelectionMode.MULTIPLE
                            itemsProperty().bindBidirectional(sourceModel.descriptionSelectors)
                        }
                        form {
                            fieldset {
                                field {
                                    descadditemtextfield = textfield {  }
                                    button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT)) {
                                        action {
                                            find(EditTextArea::class,params = mapOf(EditTextArea::node to descadditemtextfield)).openModal()
                                        }
                                    }

                                }
                                field {
                                    button(text = "Add",graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS)).setOnAction {
                                        if(descadditemtextfield.text.isNotBlank() and descadditemtextfield.text.isNotEmpty())desclistview.items.add(descadditemtextfield.text)
                                        descadditemtextfield.text = ""

                                    }
                                    button(text = "Delete",graphic = FontAwesomeIconView(FontAwesomeIcon.MINUS)).setOnAction {
                                        desclistview.items.remove(desclistview.selectedItem)
                                    }
                                }
                            }
                        }

                    }
                }
                field("Images Part (css query) : ") {
                    vbox {
                        useMaxWidth = true
                        imglistview = listview<String> {
                            setCellFactory { EditStringListCell() }
                            isEditable = true
                            selectionModel.selectionMode = SelectionMode.MULTIPLE
                            itemsProperty().bindBidirectional(sourceModel.imageSelectors)

                        }
                        form {
                            fieldset {
                                field {
                                    imgadditemtextfield = textfield {  }
                                    button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT)) {
                                        action {
                                            find(EditTextArea::class,params = mapOf(EditTextArea::node to imgadditemtextfield)).openModal()
                                        }
                                    }

                                }
                                field {
                                    button(text = "Add",graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS)).setOnAction {
                                        if(imgadditemtextfield.text.isNotBlank() and imgadditemtextfield.text.isNotEmpty()) imglistview.items.add(imgadditemtextfield.text)
                                        imgadditemtextfield.text = ""
                                    }
                                    button(text = "Delete",graphic = FontAwesomeIconView(FontAwesomeIcon.MINUS)).setOnAction {
                                        imglistview.items.remove(imglistview.selectedItem)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
