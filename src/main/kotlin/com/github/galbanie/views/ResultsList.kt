package com.github.galbanie.views

import com.github.galbanie.CheckPartsQuery
import com.github.galbanie.ResultsListFound
import com.github.galbanie.models.Result
import tornadofx.*
import javafx.scene.control.ListView
import java.util.*

/**
 * Created by Galbanie on 2017-08-01.
 */
class ResultsList : Fragment() {

    val checkPartsId : UUID by param()

    override val root = ListView<Result>()

    init {
        fire(CheckPartsQuery(checkPartsId))
        with(root){
            subscribe<ResultsListFound> {
                items.setAll(it.results)
            }
        }
    }
}
