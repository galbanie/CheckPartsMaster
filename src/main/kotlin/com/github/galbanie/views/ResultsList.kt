package com.github.galbanie.views

import com.github.galbanie.CheckPartsQuery
import com.github.galbanie.ResultsListFound
import com.github.galbanie.models.Result
import tornadofx.*
import javafx.scene.control.ListView
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.FontWeight
import java.net.URL
import java.util.*

/**
 * Created by Galbanie on 2017-08-01.
 */
class ResultsList : Fragment() {

    val checkId : UUID by param()
    lateinit var url : URL
    override val root = ListView<Result>()
    init {
        with(root){
            cellCache { result ->
                hbox(10){
                    imageview(if(result.imagesUrl.isNotEmpty()) result.imagesUrl.first() else resources.url("/com/github/galbanie/Image-not-found.gif").toString()){
                        style {
                            borderColor += box(Color.BLACK)
                            borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.MITER, StrokeLineCap.BUTT, 10.0, 0.0, listOf(25.0, 5.0))
                            borderWidth += box(5.px)
                        }
                        fitHeight = 70.0
                        fitWidth = 70.0
                        isPreserveRatio = true
                        if(result.imagesUrl.isNotEmpty()){
                            tooltip {
                                graphic = datagrid(result.imagesUrl) {
                                    cellCache {
                                        imageview(it){
                                            isPreserveRatio = true
                                        }
                                    }

                                }
                            }
                        }
                    }
                    gridpane {
                        row {
                            hyperlink(result.titre) {
                                style {
                                    fontSize = 1.6.em
                                    textFill = Color.ROYALBLUE
                                    fontWeight = FontWeight.EXTRA_BOLD
                                }
                                tooltip(result.url)
                                setOnAction {
                                    hostServices.showDocument(result.url)
                                }
                            }
                            label(result.part){
                                style {
                                    fontSize = 1.6.em
                                    textFill = Color.DARKGRAY
                                    fontWeight = FontWeight.SEMI_BOLD
                                }
                            }
                        }
                        row{
                            hyperlink(result.source.name) {
                                url = URL(result.source.url)
                                style {
                                    fontSize = 0.7.em
                                    textFill = Color.CORAL
                                    fontWeight = FontWeight.SEMI_BOLD
                                    underline = true
                                }
                                tooltip("${url.protocol}://${url.host}")
                                setOnAction {
                                    hostServices.showDocument("${url.protocol}://${url.host}")
                                }
                            }
                        }
                        result.descriptions.forEach {
                            row{
                                label(it){
                                    style {
                                        //fontSize = 0.7.em
                                        textFill = Color.BLACK
                                        fontWeight = FontWeight.MEDIUM
                                    }
                                }
                            }
                        }
                    }
                }
            }
            subscribe<ResultsListFound> {
                items.setAll(it.results)
            }
            whenDocked {
                fire(CheckPartsQuery(checkId))
            }
        }
    }
}
