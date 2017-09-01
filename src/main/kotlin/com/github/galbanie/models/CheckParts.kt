package com.github.galbanie.models

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*
import java.util.*
import javax.xml.bind.annotation.XmlRootElement

/**
 * Created by Galbanie on 2017-07-31.
 */
@XmlRootElement(name = "CheckParts")
class CheckParts {

    val idProperty = SimpleObjectProperty<UUID>(UUID.randomUUID())
    var id by idProperty

    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val partsProperty = SimpleListProperty<Part>(FXCollections.observableArrayList())
    var parts by partsProperty

    val sourcesProperty = SimpleListProperty<Source>(FXCollections.observableArrayList())
    var sources by sourcesProperty

    val resultsProperty = SimpleListProperty<Result>(FXCollections.observableArrayList())
    var results by resultsProperty

    val lockProperty = SimpleBooleanProperty(false)
    var lock by lockProperty

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}

class CheckPartsModel : ItemViewModel<CheckParts>() {
    val id = bind(CheckParts::idProperty)
    val name = bind(CheckParts::nameProperty)
    val parts = bind(CheckParts::partsProperty)
    val sources = bind(CheckParts::sourcesProperty)
    val results = bind(CheckParts::resultsProperty)
    val lock = bind(CheckParts::lockProperty)
}


