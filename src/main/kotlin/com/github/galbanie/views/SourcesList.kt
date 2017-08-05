package com.github.galbanie.views

import com.github.galbanie.models.Source
import tornadofx.*
import javafx.scene.control.ListView

/**
 * Created by Galbanie on 2017-07-31.
 */
class SourcesList : View("Source") {
    override val root = ListView<Source>()

    init {
        with(root){

        }
    }
}
