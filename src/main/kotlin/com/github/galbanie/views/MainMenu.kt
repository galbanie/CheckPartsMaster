package com.github.galbanie.views

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import tornadofx.*
import javafx.scene.control.MenuBar
import javafx.scene.input.KeyCombination

/**
 * Created by Galbanie on 2017-07-31.
 */
class MainMenu : View() {
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

                    }
                }
                separator()
                item("Quit", KeyCombination.keyCombination("Shortcut+Q")).action {
                    kotlin.system.exitProcess(0)
                }
            }
            menu("File") {
                menu("New"){
                    item("Check Parts", KeyCombination.keyCombination("Shortcut+N")){

                    }
                    item("Source"){

                    }
                }
                item("Open", KeyCombination.keyCombination("Shortcut+O"), FontAwesomeIconView(FontAwesomeIcon.FOLDER_OPEN_ALT)){
                    action {

                    }
                }
                separator()
                item("Save", KeyCombination.keyCombination("Shortcut+S"), FontAwesomeIconView(FontAwesomeIcon.FLOPPY_ALT)){
                    action {

                    }
                }
                menu("Save As") {
                    item("CSV", KeyCombination.keyCombination("Shortcut+Alt+C")).action {

                    }
                    item("XML", KeyCombination.keyCombination("Shortcut+Alt+X")).action {

                    }
                }
                separator()
                item("Manage Sources", KeyCombination.keyCombination("Shortcut+M")).action {

                }
                item("Import Sources", KeyCombination.keyCombination("Shortcut+I")).action {

                }
                item("Export Sources", KeyCombination.keyCombination("Shortcut+E")).action {

                }
            }
            menu("Edit") {
                item("Copy", KeyCombination.keyCombination("Shortcut+C"), FontAwesomeIconView(FontAwesomeIcon.CLIPBOARD)).action {

                }
                item("Paste", KeyCombination.keyCombination("Shortcut+P"), FontAwesomeIconView(FontAwesomeIcon.CLIPBOARD)).action {

                }
            }
            menu("Run") {
                item("Run", KeyCombination.keyCombination("Alt+R"), FontAwesomeIconView(FontAwesomeIcon.PLAY)){
                    action {

                    }
                }
                item("Stop", KeyCombination.keyCombination("Alt+S"), FontAwesomeIconView(FontAwesomeIcon.STOP)){
                    action {

                    }
                }
                separator()
                item("Clear", KeyCombination.keyCombination("Alt+C"), FontAwesomeIconView(FontAwesomeIcon.ERASER)){
                    action {

                    }
                }

            }
            menu("Help") {
                item("Keymap Reference").action {

                }
                item("Selector Reference").action {

                }
                item("Demo and Screencasts").action {

                }
                item("Report").action {

                }
            }
        }
    }
}
