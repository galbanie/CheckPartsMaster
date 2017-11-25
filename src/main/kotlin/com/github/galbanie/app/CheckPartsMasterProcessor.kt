package com.github.galbanie.app

import org.jsoup.Connection

/**
 * Created by Galbanie on 2017-11-19.
 */
class CheckPartsMasterProcessor {
    private val queue = ArrayList<Connection>()
    val responses = ArrayList<Connection.Response>()

    fun addToQueue(connection: Connection): CheckPartsMasterProcessor = apply { queue.add(connection) }

    fun process(): CheckPartsMasterProcessor = apply {
        responses.clear()
        queue.forEach { responses.add(it.execute()) }
        queue.clear()
    }
}