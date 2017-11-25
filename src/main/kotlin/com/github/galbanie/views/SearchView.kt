package com.github.galbanie.views

import com.github.galbanie.SearchRequest
import com.github.galbanie.models.Result
import javafx.collections.transformation.FilteredList
import tornadofx.*
import javafx.scene.control.TextField

/**
 * Created by Galbanie on 2017-07-31.
 */
class SearchView : View() {
    override val root = textfield{
        promptText = "Search"
        /*textProperty().onChange {
            if(it != null) fire(SearchRequest(it))
        }*/
    }
}
