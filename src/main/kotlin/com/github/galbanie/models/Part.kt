package com.github.galbanie.models

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-01.
 */
class Part(part : String = "", check : Boolean = false) {
    val partProperty = SimpleStringProperty(part)
    var part by partProperty

    val checkProperty = SimpleBooleanProperty(check)
    var check by checkProperty

    val sourcesProperty = SimpleListProperty<Source>(FXCollections.observableArrayList())
    var sources by sourcesProperty

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Part

        if (part != other.part) return false

        return true
    }

    override fun hashCode(): Int {
        return part.hashCode()
    }

    override fun toString(): String {
        return "$part[$check]"
    }

    fun initialize() {check = false}
}

class PartModel : ItemViewModel<Part>() {
    val part = bind(Part::partProperty)
    val check = bind(Part::checkProperty)
    val sources = bind(Part::sourcesProperty)
}
