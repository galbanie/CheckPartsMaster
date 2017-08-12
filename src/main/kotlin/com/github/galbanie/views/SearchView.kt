package com.github.galbanie.views

import tornadofx.*
import javafx.scene.control.TextField

/**
 * Created by Galbanie on 2017-07-31.
 */
class SearchView : View() {
    override val root = textfield{
        promptText = "Search"
    }
}
