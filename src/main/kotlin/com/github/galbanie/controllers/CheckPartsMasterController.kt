package com.github.galbanie.controllers

import com.github.galbanie.*
import com.github.galbanie.models.Part
import com.github.galbanie.utils.AddType
import com.github.galbanie.views.CheckPartsArea
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-04.
 */
class CheckPartsMasterController : Controller() {
    override val scope  = super.scope as CheckPartsMasterScope
    init {
        // Subscribe Check Parts
        subscribe<CheckPartsListRequest> {
            fire(CheckPartsListFound(scope.checks))
        }
        subscribe<CheckPartsQuery> { event ->
            var check = scope.checks.filter { it.id.equals(event.id) }.firstOrNull()
            if (check != null) {
                fire(ResultsListFound(check.results))
            }
        }
        subscribe<CheckPartsCreated> {
            scope.checks.add(it.check)
            fire(CheckPartsListRequest)
            workspace.dockInNewScope<CheckPartsArea>(params = mapOf(CheckPartsArea::checkParts to it.check))
        }
        // Subscribe Sources
        subscribe<SourceListRequest> {
            fire(SourceListFound(scope.sources))
        }
        // Subscribe Parts
        subscribe<PartsAdded> { event ->
            var check = scope.checks.find { it.id.equals(event.checkId) }!!.apply {
                when(event.mode){
                    AddType.add -> parts.addAll(event.parts.split("\n").map { Part(it) })
                    AddType.clearAndAdd -> parts.setAll(event.parts.split("\n").map { Part(it) })
                }
            }
        }
    }
}