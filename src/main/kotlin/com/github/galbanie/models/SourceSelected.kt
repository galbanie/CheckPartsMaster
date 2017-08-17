package com.github.galbanie.models

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-13.
 */
class SourceSelected {
    val selectedProperty = SimpleBooleanProperty()
    var selected by selectedProperty

    val sourceProperty = SimpleObjectProperty<Source>()
    var source by sourceProperty
}

class SourceSelectedModel : ItemViewModel<SourceSelected>() {
    val selected = bind(SourceSelected::selectedProperty)
    val source = bind(SourceSelected::sourceProperty)
}
