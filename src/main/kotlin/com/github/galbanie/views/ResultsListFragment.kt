package com.github.galbanie.views

import com.github.galbanie.SearchRequest
import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.CheckPartsModel
import com.github.galbanie.models.Result
import javafx.beans.binding.Bindings
import javafx.scene.control.SelectionMode
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.FontWeight
import tornadofx.*
import java.net.URL
import java.util.function.Predicate
import javafx.beans.binding.Bindings.createObjectBinding



/**
 * Created by Galbanie on 2017-08-01.
 */
class ResultsListFragment : Fragment() {
    override val configPath = app.configBasePath.resolve("app.properties")
    val check : CheckParts by param()
    val checkPartsModel = CheckPartsModel()
    val searchView : SearchView by inject()
    lateinit var data : SortedFilteredList<Result>
    lateinit var url : URL
    init {
        checkPartsModel.item = check
        //data.filteredItems.predicateProperty().bind(Bindings.createObjectBinding( { -> },searchView.root.textProperty()))
        //data.filterWhen(searchView.root.textProperty(), { query, item -> item.matches(query) })
    }

    override fun onDock() {
        data = SortedFilteredList<Result>(checkPartsModel.item.resultsProperty.value).bindTo(root)
        data.filterWhen(searchView.root.textProperty(), { query, item -> item.matches(query) })
        //data.filteredItems.predicate = Predicate{ it.matches(searchView.root.text) }
    }
    override val root = listview<Result>(){
        selectionModel.selectionMode = SelectionMode.MULTIPLE
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
                                val browser = config.string("browser","System")
                                if(browser.isEmpty() or browser.equals("System")) hostServices.showDocument(result.url)
                                else find<Browser>(params = mapOf(Browser::url to result.url)).openWindow()
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
                                val browser = config.string("browser", "System")
                                if(browser.isEmpty() or browser.equals("System")) hostServices.showDocument("${url.protocol}://${url.host}")
                                else find<Browser>(params = mapOf(Browser::url to "${url.protocol}://${url.host}")).openWindow()
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
        subscribe<SearchRequest> { event ->
            //data.filteredItems.predicate = Predicate{ it.matches(event.query, event.parts, event.sources) }
            //data.filteredItems.predicateProperty().bind(Bindings.createObjectBinding({{}},))
            //data.filteredItems.predicateProperty().bind(Bindings.createObjectBinding({ { result ->  &&  } }))
            /*filteredList.predicateProperty().bind(Bindings.createObjectBinding({ { person -> person.getFirstName().contains(firstNameFilterTextField.getText()) && person.getLastName().contains(lastNameFilterTextField.getText()) } },
                    firstNameFilterTextField.textProperty(),
                    lastNameFilterTextField.textProperty()
            ))*/
        }
    }
}