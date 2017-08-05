package com.github.galbanie.models

import com.github.galbanie.utils.PartDelimiter
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import org.jsoup.Connection
import tornadofx.*
import java.util.*

/**
 * Created by Galbanie on 2017-07-31.
 */
class Source{
    val idProperty = SimpleObjectProperty<UUID>(UUID.randomUUID())
    var id by idProperty

    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val urlProperty = SimpleStringProperty()
    var url by urlProperty

    val availableProperty = SimpleBooleanProperty()
    var available by availableProperty

    val methodProperty = SimpleObjectProperty<Connection.Method>(Connection.Method.GET)
    var method by methodProperty

    val dataProperty = SimpleListProperty<Data>(FXCollections.observableArrayList())
    var data by dataProperty

    val queryProperty = SimpleListProperty<Data>(FXCollections.observableArrayList())
    var query by queryProperty

    val elementSelectorProperty = SimpleStringProperty()
    var elementSelector by elementSelectorProperty

    val titreSelectorProperty = SimpleStringProperty()
    var titreSelector by titreSelectorProperty

    val urlSelectorProperty = SimpleStringProperty()
    var urlSelector by urlSelectorProperty

    val descriptionSelectorsProperty = SimpleListProperty<String>(FXCollections.observableArrayList())
    var descriptionSelectors by descriptionSelectorsProperty

    val imageSelectorsProperty = SimpleListProperty<String>(FXCollections.observableArrayList())
    var imageSelectors by imageSelectorsProperty

    val paginationProperty = SimpleBooleanProperty(false)
    var pagination by paginationProperty

    val linkPaginationSelectorProperty = SimpleStringProperty("")
    var linkPaginationSelector by linkPaginationSelectorProperty

    val urlPaginationProperty = SimpleStringProperty("")
    var urlPagination by urlPaginationProperty

    val numberElementPaginationProperty = SimpleObjectProperty<Int>(10)
    var numberElementPagination by numberElementPaginationProperty

    val delimiterProperty = SimpleObjectProperty<PartDelimiter>(PartDelimiter.DEFAULT)
    var delimiter by delimiterProperty

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

class SourceModel : ItemViewModel<Source>() {
    val id = bind(Source::idProperty)
    val name = bind(Source::nameProperty)
    val url = bind(Source::urlProperty)
    val available = bind(Source::availableProperty)
    val method = bind(Source::methodProperty)
    val data = bind(Source::dataProperty)
    val query = bind(Source::queryProperty)
    val elementSelector = bind(Source::elementSelectorProperty)
    val titreSelector = bind(Source::titreSelectorProperty)
    val urlSelector = bind(Source::urlSelectorProperty)
    val descriptionSelectors = bind(Source::descriptionSelectorsProperty)
    val imageSelectors = bind(Source::imageSelectorsProperty)
    val pagination = bind(Source::paginationProperty)
    val linkPaginationSelector = bind(Source::linkPaginationSelectorProperty)
    val urlPagination = bind(Source::urlPaginationProperty)
    val numberElementPagination = bind(Source::numberElementPaginationProperty)
    val delimiter = bind(Source::delimiterProperty)
}
