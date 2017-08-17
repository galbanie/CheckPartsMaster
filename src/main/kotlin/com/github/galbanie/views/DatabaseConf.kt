package com.github.galbanie.views

import com.github.galbanie.models.Data
import javafx.util.converter.DefaultStringConverter
import tornadofx.*

/**
 * Created by Galbanie on 2017-08-08.
 */
class DatabaseConf : View("DataBase") {
    override val configPath = app.configBasePath.resolve("app.properties")
    override val root = borderpane {
        center{
            form{
                fieldset {
                    field("Driver") {
                        combobox<String>{
                            selectionModel.select(config.string("database.driver"))
                            items.setAll("org.h2.Driver")
                            selectionModel.selectedItemProperty().onChange {
                                with(config){
                                    set("database.driver", selectedItem)
                                    save()
                                }
                            }
                        }
                    }
                    field("Path") {
                        textfield{
                            text = config.string("database.path")
                            textProperty().onChange {
                                with(config){
                                    set("database.path", text)
                                    save()
                                }
                            }
                        }
                    }
                    field("User") {
                        textfield{
                            text = config.string("database.user")
                            textProperty().onChange {
                                with(config){
                                    set("database.user", text)
                                    save()
                                }
                            }
                        }
                    }
                    field("Password") {
                        passwordfield{
                            text = config.string("database.password")
                            textProperty().onChange {
                                with(config){
                                    set("database.password", text)
                                    save()
                                }
                            }
                        }
                    }
                    field("Url") {
                        textfield{
                            text = config.string("database.url")
                            textProperty().onChange {
                                with(config){
                                    set("database.url", text)
                                    save()
                                }
                            }
                        }
                        combobox<String>{
                            selectionModel.select(config.string("database.type"))
                            items.setAll("file")
                            selectionModel.selectedItemProperty().onChange {
                                with(config){
                                    set("database.type", selectedItem)
                                    save()
                                }
                            }
                        }
                    }
                    field("Option") {
                        tableview<Data> {
                            items.setAll(config.string("database.options").split(";").map { Data().apply { key = it.split("=").first(); value = it.split("=").last() } })
                            isEditable = true
                            column("Key",Data::key){
                                useTextField(DefaultStringConverter())
                                weigthedWidth(1.0)
                            }
                            column("Value",Data::value){
                                useTextField(DefaultStringConverter())
                                weigthedWidth(1.0)
                            }
                            columnResizePolicy = SmartResize.POLICY
                        }
                    }
                }
            }
        }
    }
}
