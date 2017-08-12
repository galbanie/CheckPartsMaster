package com.github.galbanie.views


import com.github.galbanie.models.Source
import com.github.galbanie.models.SourceModel
import javafx.geometry.Insets
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-10.
 */
class SourceArea : Fragment() {
    val source : Source by param()
    val sourceModel = SourceModel()
    val sourceInformationView: SourceInformation by inject()
    val sourceDataPostView : SourceDataPost by inject()
    val sourcePagination : SourcePagination by inject()
    val sourceQueriesGetView : SourceQueriesGet by inject()
    val sourceSelectorOptionalView : SourceSelectorOptional by inject()
    val sourceSelectorRequiredView : SourceSelectorRequired by inject()
    init {
        sourceModel.item = source
        sourceInformationView.sourceModel.item = sourceModel.item
        titleProperty.bindBidirectional(sourceModel.name)
    }
    override val root = borderpane {
        center{
            padding = Insets(10.0)
            scrollpane(fitToWidth = true, fitToHeight = true) {
                squeezebox {
                    fold(sourceInformationView.title) {
                        this += sourceInformationView.root
                    }
                    fold(sourceQueriesGetView.title) {
                        this += sourceQueriesGetView.root
                    }
                    fold(sourceDataPostView.title) {
                        this += sourceDataPostView.root
                    }
                    fold(sourceSelectorRequiredView.title) {
                        this += sourceSelectorRequiredView.root
                    }
                    fold(sourceSelectorOptionalView.title) {
                        this += sourceSelectorOptionalView.root
                    }
                    fold(sourcePagination.title) {
                        this += sourcePagination.root
                    }
                }
            }

        }
    }
}