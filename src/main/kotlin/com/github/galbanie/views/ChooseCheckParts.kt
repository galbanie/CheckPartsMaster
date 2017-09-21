package com.github.galbanie.views

import com.github.galbanie.*
import com.github.galbanie.models.CheckPartsSelected
import com.github.galbanie.models.CheckPartsSelectedModel
import com.github.galbanie.models.SourceSelected
import com.github.galbanie.utils.ActionFile
import javafx.scene.control.ButtonBar
import javafx.scene.control.TableView
import javafx.stage.FileChooser
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-13.
 */
class ChooseCheckParts : Fragment("Choose Check Parts") {
    val checkPartsSelectedModel: CheckPartsSelectedModel by inject()
    lateinit var checkTable: TableView<CheckPartsSelected>
    override val root = borderpane {
        center{
            checkTable = tableview<CheckPartsSelected> {
                bindSelected(checkPartsSelectedModel)
                column("", CheckPartsSelected::selectedProperty).makeEditable()
                column("Check Parts", CheckPartsSelected::checkPartsProperty).cellFormat {
                    text = it.name
                }

                columnResizePolicy = tornadofx.SmartResize.Companion.POLICY
                enableCellEditing()

                subscribe<CheckPartsListFound> {
                    items.setAll(it.checks.map { CheckPartsSelected().apply {
                        selected = false
                        checkParts = it
                    } })
                }
                whenDocked {
                    fire(CheckPartsListRequest)
                }
            }
        }
        bottom {
            buttonbar {
                style{
                    padding = tornadofx.box(10.px)
                }
                button("Apply", ButtonBar.ButtonData.RIGHT).setOnAction {
                    fire(CheckPartsSelectedListFound(checkTable.items.filter{ it.selected.equals(true) }.map{ it.checkParts }))
                    fire(ChooseFileActionEvent("Save Web Check Parts", arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")), FileChooserMode.Save, ActionFile.saveCheckToXml))
                    close()
                }
                button("Cancel", ButtonBar.ButtonData.RIGHT).setOnAction {
                    close()
                }
                button("Select All", ButtonBar.ButtonData.LEFT).setOnAction {
                    checkTable.items.forEach { it.selected = true }
                }
                button("Deselect All", ButtonBar.ButtonData.LEFT).setOnAction {
                    checkTable.items.forEach { it.selected = false }
                }
            }
        }
    }
}
