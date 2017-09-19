package com.github.galbanie.views

import com.github.galbanie.utils.Console
import javafx.geometry.Insets
import javafx.scene.control.TextArea
import javafx.scene.text.Font
import tornadofx.*
import java.io.PrintStream

/**
 * Created by Galbanie on 2017-09-17.
 */
class Output : View("Output") {
    var console : Console
    var ps : PrintStream
    lateinit var outputtextarea : TextArea

    override val root = stackpane {
        outputtextarea = textarea {
            style {

            }
            isEditable = false
            padding = Insets(5.0)
            font = Font("courier", 11.0)
            prefWidth = 700.0
            isWrapText = true
            opaqueInsets = Insets.EMPTY
        }

    }

    init {
        console = Console(outputtextarea)
        ps = PrintStream(console,true)
        System.setOut(ps)
        //System.setErr(ps)
    }
}
