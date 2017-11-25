package com.github.galbanie.models

import com.github.galbanie.utils.PartDelimiter
import javafx.beans.property.*
import javafx.collections.FXCollections
import org.jsoup.Connection
import tornadofx.*
import java.io.Serializable
import java.util.*

/**
 * Created by Galbanie on 2017-07-31.
 */
class Source : Serializable{
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

    val activeProxyProperty = SimpleBooleanProperty(false)
    var activeProxy by activeProxyProperty

    val proxyAddressProperty = SimpleStringProperty("127.0.0.1")
    var proxyAddress by proxyAddressProperty

    val proxyPortProperty = SimpleIntegerProperty(8080)
    var proxyPort by proxyPortProperty

    val timeoutProperty = SimpleIntegerProperty(0)
    var timeout by timeoutProperty

    val latencyProperty = SimpleIntegerProperty(0)
    var latency by latencyProperty

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as Source
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "$name"
    }
}

class SourceModel : ItemViewModel<Source>() {
    val id = bind(Source::idProperty,autocommit = true)
    val name = bind(Source::nameProperty,autocommit = true)
    val url = bind(Source::urlProperty,autocommit = true)
    val available = bind(Source::availableProperty,autocommit = true)
    val method = bind(Source::methodProperty,autocommit = true)
    val data = bind(Source::dataProperty,autocommit = true)
    val query = bind(Source::queryProperty,autocommit = true)
    val elementSelector = bind(Source::elementSelectorProperty,autocommit = true)
    val titreSelector = bind(Source::titreSelectorProperty,autocommit = true)
    val urlSelector = bind(Source::urlSelectorProperty,autocommit = true)
    val descriptionSelectors = bind(Source::descriptionSelectorsProperty,autocommit = true)
    val imageSelectors = bind(Source::imageSelectorsProperty,autocommit = true)
    val pagination = bind(Source::paginationProperty,autocommit = true)
    val linkPaginationSelector = bind(Source::linkPaginationSelectorProperty,autocommit = true)
    val urlPagination = bind(Source::urlPaginationProperty,autocommit = true)
    val numberElementPagination = bind(Source::numberElementPaginationProperty,autocommit = true)
    val delimiter = bind(Source::delimiterProperty,autocommit = true)
    val activeProxy = bind(Source::activeProxyProperty,autocommit = true)
    val proxyAddress = bind(Source::proxyAddressProperty,autocommit = true)
    val proxyPort = bind(Source::proxyPortProperty,autocommit = true)
    val timeout = bind(Source::timeoutProperty,autocommit = true)
    val latency = bind(Source::latencyProperty,autocommit = true)
}
