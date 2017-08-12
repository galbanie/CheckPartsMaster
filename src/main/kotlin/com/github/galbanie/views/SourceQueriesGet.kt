package com.github.galbanie.views

import com.github.galbanie.models.Data
import com.github.galbanie.models.SourceModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.util.converter.DefaultStringConverter
import tornadofx.*
import tornadofx.Form

/**
 * Created by Galbanie on 2017-08-10.
 */
class SourceQueriesGet : View("Queries") {
    val sourceModel : SourceModel by inject()
    lateinit var querytableview : TableView<Data>
    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            field("Queries") {
                vbox {
                    querytableview = tableview<Data> {
                        itemsProperty().bindBidirectional(sourceModel.query)
                        isEditable = true
                        column("Key",Data::key){
                            useTextField(DefaultStringConverter())
                            weigthedWidth(1.0)
                        }
                        column("Value",Data::value){
                            useTextField(DefaultStringConverter())
                            weigthedWidth(1.0)
                        }
                        columnResizePolicy = SmartResize.POLICY
                    }
                    form {
                        fieldset {
                            field {
                                button(text = "Add",graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS)).setOnAction {
                                    querytableview.items.add(Data(key = "your key", value = "your value"))
                                }
                                button(text = "Delete",graphic = FontAwesomeIconView(FontAwesomeIcon.MINUS)).setOnAction {
                                    querytableview.items.remove(querytableview.selectedItem)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
