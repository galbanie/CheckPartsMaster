package com.github.galbanie.views

import com.github.galbanie.models.Result
import tornadofx.*
import javafx.scene.control.TextField

/**
 * Created by Galbanie on 2017-07-31.
 */
class SearchView : View() {
    val data = SortedFilteredList<Result>()
    override val root = textfield{
        promptText = "Search"
        data.filterWhen(textProperty(), { query, item -> item.matches(query) } )
    }
}
