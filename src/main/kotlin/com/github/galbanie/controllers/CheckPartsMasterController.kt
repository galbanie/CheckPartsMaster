package com.github.galbanie.controllers

import com.github.galbanie.*
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-04.
 */
class CheckPartsMasterController : Controller() {
    override val scope  = super.scope as CheckPartsMasterScope
    init {
        subscribe<CheckPartsListRequest> {
            fire(CheckPartsListFound(scope.checks))
        }
        subscribe<CheckPartsQuery> { event ->
            var check = scope.checks.filter { it.id.equals(event.id) }.firstOrNull()
            println(check)
            if (check != null) {
                fire(ResultsListFound(check.results))
            }
        }
    }
}