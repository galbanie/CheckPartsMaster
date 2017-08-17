package com.github.galbanie.views

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-08.
 */
class GeneralConf : View("General") {
    override val configPath = app.configBasePath.resolve("app.properties")
    override val root = borderpane {
        center{
            form{
                fieldset("Proxy") {
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

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
