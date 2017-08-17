package com.github.galbanie.views

import com.github.galbanie.models.Source
import com.github.galbanie.models.SourceModel
import javafx.beans.binding.BooleanExpression
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-09.
 */
class NewSource : Wizard("Create Web Source", "Provide web source information") {
    val sourceModel : SourceModel by inject()
    override val canGoNext = currentPageComplete
    override val canFinish = allPagesComplete
    init {
        sourceModel.item = Source()
        //graphic = resources.imageview("/graphics/customer.png")
        add(SourceInformation::class)
        add(SourceQueriesGet::class)
        add(SourceDataPost::class)
        add(SourceSelectorRequired::class)
        add(SourceSelectorOptional::class)
        add(SourcePagination::class)
    }
}
