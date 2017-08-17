package com.github.galbanie.views

import com.github.galbanie.models.Data
import com.github.galbanie.models.SourceModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.util.converter.DefaultStringConverter
import org.jsoup.Connection
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-10.
 */
class SourceDataPost : View("Data (POST)") {
    val sourceModel : SourceModel by inject()
    lateinit var datatableview : TableView<Data>
    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            field("Data") {
                //enableWhen { sourceModel.method.booleanBinding() }
                vbox {
                    datatableview = tableview<Data> {
                        itemsProperty().bindBidirectional(sourceModel.data)
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
                                    datatableview.items.add(Data().apply { key = "your key"; value = "your value" })
                                }
                                button(text = "Delete",graphic = FontAwesomeIconView(FontAwesomeIcon.MINUS)).setOnAction {
                                    datatableview.items.remove(datatableview.selectedItem)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
