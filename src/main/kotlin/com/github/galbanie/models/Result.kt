package com.github.galbanie.models

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-01.
 */
class Result {
    val partProperty = SimpleStringProperty()
    var part by partProperty

    val titreProperty = SimpleStringProperty()
    var titre by titreProperty

    val urlProperty = SimpleStringProperty()
    var url by urlProperty

    val sourceProperty = SimpleObjectProperty<Source>()
    var source by sourceProperty

    val descriptionsProperty = SimpleListProperty<String>(FXCollections.observableArrayList())
    var descriptions by descriptionsProperty

    val imagesUrlProperty = SimpleListProperty<String>(FXCollections.observableArrayList())
    var imagesUrl by imagesUrlProperty

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Result

        if (part != other.part) return false
        if (titre != other.titre) return false
        if (url != other.url) return false
        if (source != other.source) return false
        if (descriptions.containsAll(other.descriptions)) return false
        if (imagesUrl.containsAll(other.imagesUrl)) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        //return "$part|$titre|$url|${source.name}|${descriptions.joinToString("\n")}"
        return super.toString()
    }

    fun matches(query : String) : Boolean{
        return part.toLowerCase().contains(query.toLowerCase().toRegex()) || titre.toLowerCase().contains(query.toLowerCase().toRegex())
    }
}

class ResultModel : ItemViewModel<Result>() {
    val part = bind(Result::partProperty)
    val titre = bind(Result::titreProperty)
    val url = bind(Result::urlProperty)
    val source = bind(Result::sourceProperty)
    val descriptions = bind(Result::descriptionsProperty)
    val imagesUrl = bind(Result::imagesUrlProperty)
}
