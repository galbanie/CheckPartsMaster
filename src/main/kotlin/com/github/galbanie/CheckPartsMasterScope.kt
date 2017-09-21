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
    val checks = FXCollections.observableArrayList<CheckParts>()
    val checksSelected = FXCollections.observableArrayList<CheckParts>()
    val sources = FXCollections.observableArrayList<Source>()
    val sourcesSelected = FXCollections.observableArrayList<Source>()
    val sourcesDraged = FXCollections.observableArrayList<Source>()
}