package com.github.galbanie.controllers

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by Galbanie on 2017-10-14.
 */
class CheckPartsController : Controller() {

    val resultViewTypeProperty = SimpleStringProperty("List")
    val resultViewType: String? get() = resultViewTypeProperty.value

    init {

    }

}