package com.github.galbanie.views

import tornadofx.*
import javafx.scene.web.WebView

/**
 * Created by Galbanie on 2017-08-01.
 */
class Browser : Fragment() {
    val url : String by param()
    val _title : String? by nullableParam()

    init {
        title = _title ?: url
    }

    override val root = webview {
        engine.apply {
            load(url)
        }
    }
}
