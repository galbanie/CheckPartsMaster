package com.github.galbanie.views

import com.github.galbanie.CheckPartsQuery
import com.github.galbanie.ResultsListFound
import com.github.galbanie.SearchRequest
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Result
import tornadofx.*
import javafx.scene.control.TableView
import javafx.util.converter.DefaultStringConverter
import java.util.*
import java.util.function.Predicate

/**
 * Created by Galbanie on 2017-08-03.
 */
class ResultsTable : Fragment() {
    val check : CheckParts by param()
    val checkPartsModel = CheckPartsModel()
    val searchView : SearchView by inject()
    lateinit var data : SortedFilteredList<Result>
    init {
        checkPartsModel.item = check
    }
    override fun onDock() {
        data = SortedFilteredList<Result>(checkPartsModel.item.resultsProperty.value).bindTo(root)
        data.filteredItems.predicate = Predicate{ it.matches(searchView.root.text) }
    }
    override val root = tableview<Result>(){
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
        subscribe<SearchRequest> { event ->
            data.filteredItems.predicate = Predicate{ it.matches(event.query) }
        }
    }
}
