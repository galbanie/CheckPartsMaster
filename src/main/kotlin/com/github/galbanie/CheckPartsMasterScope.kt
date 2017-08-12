package com.github.galbanie

import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.Result
import com.github.galbanie.models.Source
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Galbanie on 2017-07-31.
 */
class CheckPartsMasterScope : Scope() {
    val checks = FXCollections.observableArrayList<CheckParts>(CheckParts().apply {name = "test name"; results.addAll(Result().apply { part = "testpartnum" }) },CheckParts().apply {name = "test"; results.addAll(Result().apply { part = "partnum" }) })
    val sources = FXCollections.observableArrayList<Source>(Source().apply { name = "gogo" })
}