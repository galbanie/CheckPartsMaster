package com.github.galbanie.views

import com.github.galbanie.*
import com.github.galbanie.models.SourceSelected
import com.github.galbanie.models.SourceSelectedModel
import com.github.galbanie.utils.ActionFile
import javafx.scene.control.ButtonBar
import javafx.scene.control.TableView
import javafx.stage.FileChooser
import tornadofx.*
import java.util.*

/**
 * Created by Galbanie on 2017-08-13.
 */
class ChooseSource : Fragment("Choose Source") {
    val checkId : UUID? by nullableParam()
    val sourceSelectedModel : SourceSelectedModel by inject()
    lateinit var sourceTable : TableView<SourceSelected>
    override val root = borderpane {
        center{
            sourceTable = tableview<SourceSelected> {
                bindSelected(sourceSelectedModel)
                column("", SourceSelected::selectedProperty).makeEditable()
                column("Source", SourceSelected::source).cellFormat {
                    text = it.name
                }

                columnResizePolicy = tornadofx.SmartResize.Companion.POLICY
                enableCellEditing()

                subscribe<SourceListFound> {
                    items.setAll(it.sources.map { SourceSelected().apply {
                        selected = false
                        source = it
                    } })
                }
                whenDocked {
                    fire(SourceListRequest)
                }
            }
        }
        bottom {
            buttonbar {
                style{
                    padding = tornadofx.box(10.px)
                }
                button("Apply", ButtonBar.ButtonData.RIGHT).setOnAction {
                    if(checkId != null){
                        fire(SourceAdded(checkId!!, sourceTable.items.filter{ it.selected.equals(true) }.map{ it.source }))
                    }
                    else{
                        fire(SourceSelectedListFound(sourceTable.items.filter{ it.selected.equals(true) }.map{ it.source }))
                        fire(ChooseFileActionEvent("Save Web Sources", arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")), FileChooserMode.Save, ActionFile.saveSources))
                    }
                    close()
                }
                button("Cancel", ButtonBar.ButtonData.RIGHT).setOnAction {
                    close()
                }
                button("Select All", ButtonBar.ButtonData.LEFT).setOnAction {
                    sourceTable.items.forEach { it.selected = true }
                }
                button("Deselect All", ButtonBar.ButtonData.LEFT).setOnAction {
                    sourceTable.items.forEach { it.selected = false }
                }
            }
        }
    }
}
