package com.github.galbanie.views

import com.github.galbanie.ChooseFileActionEvent
import com.github.galbanie.SourceCreated
import com.github.galbanie.utils.Action
import com.github.galbanie.utils.ActionFile
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.application.Platform
import tornadofx.*
import javafx.scene.control.MenuBar
import javafx.scene.input.KeyCombination
import javafx.stage.FileChooser
import kotlin.system.exitProcess

/**
 * Created by Galbanie on 2017-07-31.
 */
class MainMenu : View() {
    val status: TaskStatus by inject()
    override val root = MenuBar()
    init {
        with(root){
            menu("Check Parts Master") {
                graphic = resources.imageview("/com/github/galbanie/checkmark_on_circle.png").apply {
                    fitWidth = 14.0
                    fitHeight = 14.0
                }
                item("Configurations"){
                    action{
                        find<Configuration>().openModal()
                    }
                }
                separator()
                item("Quit", KeyCombination.keyCombination("Shortcut+Q")).action {
                    exitProcess(0)
                    //Platform.exit()
                    //FX.primaryStage.close()
                }
            }
            menu("File") {
                menu("New"){
                    item("Check Parts", KeyCombination.keyCombination("Shortcut+N")){
                        action {
                            find<CheckPartsEditor>(params = mapOf(CheckPartsEditor::action to Action.CREATE)).openModal()
                        }
                    }
                    item("Sources"){
                        action {
                            find<NewSource> {
                                onComplete {
                                    runAsync {
                                        fire(SourceCreated(sourceModel.item))
                                    } ui {
                                        workspace.dockInNewScope<SourceArea>(params = mapOf(SourceArea::source to sourceModel.item))
                                    }
                                }
                                openModal()
                            }
                        }
                    }
                }
                item("Open", KeyCombination.keyCombination("Shortcut+O"), FontAwesomeIconView(FontAwesomeIcon.FOLDER_OPEN_ALT)){
                    disableWhen(status.running)
                    action {
                        fire(ChooseFileActionEvent("Open Results", arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")), FileChooserMode.Single, ActionFile.openXmlToCheck))
                    }
                }
                separator()
                item("Save", KeyCombination.keyCombination("Shortcut+S"), FontAwesomeIconView(FontAwesomeIcon.FLOPPY_ALT)){
                    disableWhen(status.running)
                    action {
                        fire(ChooseFileActionEvent("Save Check Parts", arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")), FileChooserMode.Save, ActionFile.saveCheckToXml))
                    }
                }
                menu("Save Result As") {
                    disableWhen(status.running)
                    item("CSV", KeyCombination.keyCombination("Shortcut+Alt+C")).action {
                        fire(ChooseFileActionEvent("Export Result to CSV", arrayOf(FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"), FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv")), FileChooserMode.Save, ActionFile.saveResultToCsv))
                    }
                    item("XML", KeyCombination.keyCombination("Shortcut+Alt+X")).action {
                        fire(ChooseFileActionEvent("Export Result to XML", arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")), FileChooserMode.Save, ActionFile.saveResultToXml))
                    }
                }
                separator()
                item("Import Sources", KeyCombination.keyCombination("Shortcut+I")).action {
                    fire(ChooseFileActionEvent("Load Web Sources", arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")), FileChooserMode.Single, ActionFile.loadSources))
                }
                item("Export Sources", KeyCombination.keyCombination("Shortcut+E")).action {
                    find<ChooseSource>(params = mapOf(ChooseSource::checkId to null)).openModal()
                }
            }
            menu("Edit") {
                item("Copy", KeyCombination.keyCombination("Shortcut+C"), FontAwesomeIconView(FontAwesomeIcon.CLIPBOARD)).action {

                }
                item("Paste", KeyCombination.keyCombination("Shortcut+P"), FontAwesomeIconView(FontAwesomeIcon.CLIPBOARD)).action {

                }
            }
            /*menu("Run") {
                item("Run", KeyCombination.keyCombination("Alt+R"), FontAwesomeIconView(FontAwesomeIcon.PLAY)){
                    disableWhen(status.running)
                    action {

                    }
                }
                item("Stop", KeyCombination.keyCombination("Alt+S"), FontAwesomeIconView(FontAwesomeIcon.STOP)){
                    enableWhen(status.running)
                    action {

                    }
                }
                separator()
                item("Clear", KeyCombination.keyCombination("Alt+C"), FontAwesomeIconView(FontAwesomeIcon.ERASER)){
                    disableWhen(status.running)
                    action {

                    }
                }

            }*/
            menu("Help") {
                item("Keymap Reference").action {
                    find<Browser>(params = mapOf(Browser::url to resources.url("/com/github/galbanie/public/KeymapReference.html").toString(), Browser::_title to "Keymap Reference")).openModal()
                }
                item("Selector Reference").action {
                    find<Browser>(params = mapOf(Browser::url to resources.url("/com/github/galbanie/public/SelectorReference.html").toString(), Browser::_title to "Selector Reference")).openModal()
                }
                item("Demo and Screencasts").action {

                }
                item("Report").action {
                    find<ReportView>(scope).openModal()
                }
            }
        }
    }
}
