package com.github.galbanie.utils

import javafx.application.Platform
import javafx.scene.control.TextArea
import java.io.OutputStream

/**
 * Created by Galbanie on 2017-09-17.
 */
class Console(textarea : TextArea) : OutputStream() {

    val output : TextArea = textarea

    override fun write(b: Int) {
        Platform.runLater {
            output.appendText((b.toChar()).toString())

        }
    }

}