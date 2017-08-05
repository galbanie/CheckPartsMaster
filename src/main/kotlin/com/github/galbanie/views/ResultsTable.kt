package com.github.galbanie.views

import com.github.galbanie.models.Result
import tornadofx.*
import javafx.scene.control.TableView

/**
 * Created by Galbanie on 2017-08-03.
 */
class ResultsTable : Fragment("My View") {
    override val root = TableView<Result>()

    init {

    }
}
