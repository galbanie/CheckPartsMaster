package com.github.galbanie.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import tornadofx.*


/**
 * Created by Galbanie on 2017-08-11.
 */

interface CrudTable<T> {
    fun createTable()
    fun create(t : T) : T
    fun findAll() : Iterable<T>
    fun deleteAll() : Int
    fun delete(t : T) : Int
    fun findBy(column : org.jetbrains.exposed.sql.Column<*>, value : Any) : Iterable<T>?
    fun update(t : T)
    fun findColumnName() : Iterable<String>
}

class CrudSource : CrudTable<Source> {
    override fun createTable() {
        SchemaUtils.create(Sources)
    }

    override fun create(t: Source): Source {
        Sources.insert(toRow(t))
        return t
    }

    override fun findAll(): Iterable<Source> = Sources.selectAll().map { fromRow(it) }

    override fun deleteAll(): Int = Sources.deleteAll()

    override fun delete(t: Source): Int = Sources.deleteWhere { Sources.id eq t.id }

    override fun findBy(column: Column<*>, value: Any): Iterable<Source>? = Sources.select{column eq value}.map { fromRow(it) }

    override fun update(t: Source) {
        Sources.update({ Sources.id eq t.id}){
            it[name] = t.name
            it[url] = t.url
            it[method] = t.method
            //it[data] = t.data.map { (k,v) -> "$k=$v" }.joinToString(separator = "|")
            it[data] = t.data.joinToString(separator = "|")
            it[query] = t.query.joinToString(separator = "|")
            it[available] = t.available
            it[delimiter] = t.delimiter
            it[elementSelector] = t.elementSelector
            it[titreSelector] = t.titreSelector
            it[urlSelector] = t.urlSelector
            it[descSelectors] = t.descriptionSelectors.joinToString(separator = "|")
            it[imgSelectors] = t.imageSelectors.joinToString(separator = "|")
            it[pagination] = t.pagination
            it[linkPaginationSelector] = t.linkPaginationSelector
            it[urlPagination] = t.urlPagination
            it[numberElementPagination] = t.numberElementPagination
            it[activeProxy] = t.activeProxy
            it[proxyAddress] = t.proxyAddress
            it[proxyPort] = t.proxyPort
            it[timeout] = t.timeout
            it[latency] = t.latency
        }
    }

    override fun findColumnName(): Iterable<String> {
        var c = arrayListOf<String>()
        Sources.columns.forEach{
            c.add(it.name)
        }
        return c
    }

    private fun toRow(t : Source) : Sources.(UpdateBuilder<*>) -> Unit = {
        it[id] = t.id
        it[name] = t.name
        it[url] = t.url
        it[method] = t.method
        //it[data] = t.data.map { (k,v) -> "$k=$v" }.joinToString(separator = "|")
        it[data] = t.data.joinToString(separator = "|")
        it[query] = t.query.joinToString(separator = "|")
        it[available] = t.available
        it[delimiter] = t.delimiter
        it[elementSelector] = t.elementSelector
        it[titreSelector] = t.titreSelector
        it[urlSelector] = t.urlSelector
        it[descSelectors] = t.descriptionSelectors.joinToString(separator = "|")
        it[imgSelectors]= t.imageSelectors.joinToString(separator = "|")
        it[pagination] = t.pagination
        it[linkPaginationSelector] = t.linkPaginationSelector
        it[urlPagination] = t.urlPagination
        it[numberElementPagination] = t.numberElementPagination
        it[activeProxy] = t.activeProxy
        it[proxyAddress] = t.proxyAddress
        it[proxyPort] = t.proxyPort
        it[timeout] = t.timeout
        it[latency] = t.latency
    }

    private fun fromRow(r: ResultRow) = Source().apply {
        id = r[Sources.id]
        name = r[Sources.name]
        url = r[Sources.url]
        method = r[Sources.method]
        available = r[Sources.available]
        delimiter = r[Sources.delimiter]
        //data = r[WebSources.data].split("|").map { it.split("=") }.associate({ Pair(it.first(),it.last()) })
        data = if(r[Sources.data].split("|").first().isNotBlank()) r[Sources.data].split("|").map { Data().apply { key = it.split("=").first(); value = it.split("=").last() } }.observable() else arrayListOf<Data>().observable()
        query = if(r[Sources.query].split("|").first().isNotBlank()) r[Sources.query].split("|").map { Data().apply { key = it.split("=").first(); value = it.split("=").last() } }.observable() else arrayListOf<Data>().observable()
        elementSelector = r[Sources.elementSelector]
        titreSelector = r[Sources.titreSelector]
        urlSelector = r[Sources.urlSelector]
        descriptionSelectors = if(r[Sources.descSelectors].split("|").first().isNotBlank()) r[Sources.descSelectors].split("|").observable() else arrayListOf<String>().observable()
        imageSelectors = if(r[Sources.imgSelectors].split("|").first().isNotBlank()) r[Sources.imgSelectors].split("|").observable() else arrayListOf<String>().observable()
        pagination = r[Sources.pagination]
        linkPaginationSelector = r[Sources.linkPaginationSelector]
        urlPagination = r[Sources.urlPagination]
        numberElementPagination = r[Sources.numberElementPagination]
        activeProxy = r[Sources.activeProxy]
        proxyAddress = r[Sources.proxyAddress]
        proxyPort = r[Sources.proxyPort]
        timeout = r[Sources.timeout]
        latency = r[Sources.latency]
    }

}