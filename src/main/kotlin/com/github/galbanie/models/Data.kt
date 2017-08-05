package com.github.galbanie.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-01.
 */
class Data(key : String?, value : String?) {
    val keyProperty = SimpleStringProperty(key)
    var key by keyProperty

    val valueProperty = SimpleStringProperty(value)
    var value by valueProperty

    override fun toString(): String {
        return "$key=$value"
    }
}

class DataModel : ItemViewModel<Data>() {
    val key = bind(Data::keyProperty)
    val value = bind(Data::valueProperty)
}
