package com.github.galbanie.views

import com.github.galbanie.CheckPartsQuery
import com.github.galbanie.ResultsListFound
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Result
import tornadofx.*
import javafx.scene.control.TableView
import javafx.util.converter.DefaultStringConverter
import java.util.*

/**
 * Created by Galbanie on 2017-08-03.
 */
class ResultsTable : Fragment() {
    val check : CheckParts by param()
    val checkPartsModel = CheckPartsModel()
    init {
        checkPartsModel.item = check
    }
    override val root = tableview<Result>(checkPartsModel.results){
        isEditable = true
        column("Part", Result::part).useTextField(DefaultStringConverter())
        column("Titre", Result::titre).useTextField(DefaultStringConverter())
        column("Source", Result::source).cellFormat {
            text = it.name
        }
        column("Descriptions", Result::descriptions).cellFormat {
            text = it.joinToString("\n")
        }
        columnResizePolicy = SmartResize.POLICY
    }
}
