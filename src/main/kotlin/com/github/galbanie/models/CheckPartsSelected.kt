package com.github.galbanie.models

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

/**
 * Created by Galbanie on 2017-09-21.
 */
class CheckPartsSelected {
    val selectedProperty = SimpleBooleanProperty()
    var selected by selectedProperty

    val checkPartsProperty = SimpleObjectProperty<CheckParts>()
    var checkParts by checkPartsProperty
}

class CheckPartsSelectedModel : ItemViewModel<CheckPartsSelected>() {
    val selected = bind(CheckPartsSelected::selectedProperty)
    val checkParts = bind(CheckPartsSelected::checkPartsProperty)
}
