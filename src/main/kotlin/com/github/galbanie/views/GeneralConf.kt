package com.github.galbanie.views

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-08.
 */
class GeneralConf : View("General") {
    override val configPath = app.configBasePath.resolve("app.properties")
    init {

    }
    override val root = borderpane {
        center{
            form{
                /*fieldset("Proxy") {
                    field("Active") {
                        checkbox {
                            isSelected = config.boolean("proxy.active")
                            setOnAction {
                                with(config){
                                    set("proxy.active", isSelected.toString())
                                    save()
                                }
                            }
                        }
                    }
                    hbox(25) {
                        field("Address") {
                            textfield {
                                text = config.string("proxy.address")
                                textProperty().onChange {
                                    with(config){
                                        set("proxy.address", text)
                                        save()
                                    }
                                }
                            }

                        }
                        field("Port") {
                            textfield {
                                style {
                                    maxWidth = 90.px
                                    minWidth = maxWidth
                                }
                                text = config.string("proxy.port")
                                textProperty().onChange {
                                    with(config){
                                        set("proxy.port", text)
                                        save()
                                    }
                                }
                            }
                        }
                        field {
                            button(graphic = FontAwesomeIconView(FontAwesomeIcon.LIST)) {
                                action {
                                    find<Browser>(params = mapOf(Browser::url to "http://proxydb.net")).openWindow()
                                }
                            }
                        }
                    }
                }
                fieldset("Timeout") {
                    field("Millisecond") {
                        spinner<Int>(min = 0, max = 30000, initialValue = config.string("timeout.millis", "0").toInt(), amountToStepBy = 500, editable = true){
                            valueProperty().onChange {
                                with(config){
                                    set("timeout.millis", value.toString())
                                    save()
                                }
                            }
                        }
                    }
                }
                fieldset("latency") {
                    field("Active") {
                        checkbox {
                            isSelected = config.boolean("latency.active")
                            setOnAction {
                                with(config){
                                    set("latency.active", isSelected.toString())
                                    save()
                                }
                            }
                        }
                    }
                    field("Millisecond") {
                        spinner<Int>(min = 1000, max = 14000, initialValue = config.string("latency.millis","5000").toInt(), amountToStepBy = 500, editable = true){
                            valueProperty().onChange {
                                with(config){
                                    set("latency.millis", value.toString())
                                    save()
                                }
                            }
                        }
                    }
                }*/
                fieldset("Browser") {
                    field("Type") {
                        combobox<String> {
                            selectionModel.select(config.string("browser"))
                            items.setAll("System","Embedded")
                            selectionModel.selectedItemProperty().onChange {
                                with(config) {
                                    set("browser", selectedItem)
                                    save()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
