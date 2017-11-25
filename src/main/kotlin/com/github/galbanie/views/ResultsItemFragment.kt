package com.github.galbanie.views

import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Result
import tornadofx.*
import java.net.URL

/**
 * Created by Galbanie on 2017-10-18.
 */
class ResultsItemFragment : Fragment() {
    override val configPath = app.configBasePath.resolve("app.properties")
    val check : CheckParts by param()
    val checkPartsModel = CheckPartsModel()
    val searchView : SearchView by inject()
    lateinit var data : SortedFilteredList<Result>
    lateinit var url : URL
    override val root = borderpane {

    }
}
