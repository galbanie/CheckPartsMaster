package com.github.galbanie.models

import com.github.galbanie.utils.PartDelimiter
import org.jetbrains.exposed.sql.Table
import org.jsoup.Connection

/**
 * Created by Galbanie on 2017-08-11.
 */
object Sources : Table("Sources") {
    val id = uuid("id").primaryKey()
    val name = varchar("name", 100).uniqueIndex()
    val url = varchar("url", 250)
    val available = bool("available")
    val method = enumeration("method", Connection.Method::class.java)
    val delimiter = enumeration("delimiter", PartDelimiter::class.java)
    val query = varchar("query", 500)
    val data = varchar("data", 500)
    val elementSelector = varchar("elementSelector", 500)
    val titreSelector = varchar("titreSelector", 500)
    val urlSelector = varchar("urlSelector", 500)
    val descSelectors = varchar("descSelectors", 500)
    val imgSelectors = varchar("imgSelectors", 500)
    val pagination = bool("pagination")
    val linkPaginationSelector = varchar("linkPaginationSelector", 500)
    val urlPagination = varchar("urlPagination", 500)
    val numberElementPagination = integer("numberElementPagination")
    val activeProxy = bool("activeProxy")
    val proxyAddress = varchar("proxyAddress", 50)
    val proxyPort = integer("proxyPort")
    val timeout = integer("timeout")
    val latency = integer("latency")
}