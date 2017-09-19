package com.github.galbanie.views

import tornadofx.*

/**
 * Created by Galbanie on 2017-08-16.
 */
class Home : View("Home") {
    init {
        disableSave()
        disableDelete()
        disableRefresh()
    }
    override val root = borderpane {
        right {
            label {
                style {
                    padding = box(10.px)
                }
                /*text = "Check Part Master is the best check in web\n" +
                        "Every people say that so what\n" +
                        "For best content, please buy my bul shit\n" +
                        "That so prezy"*/
                isWrapText = true
            }
        }
        center = find<Browser>(params = mapOf(Browser::url to "https://www.youtube.com/embed/-W5K0x7AP9s")).root
        center.style {
            padding = box(10.px)
        }
    }
}
