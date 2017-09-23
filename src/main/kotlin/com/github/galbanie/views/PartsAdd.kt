package com.github.galbanie.views

import com.github.galbanie.PartsAdded
import com.github.galbanie.utils.AddType
import javafx.scene.control.ButtonBar
import javafx.scene.control.TextArea
import tornadofx.*
import java.util.*

/**
 * Created by Galbanie on 2017-08-11.
 */
class PartsAdd : Fragment("Add Parts") {
    val checkId : UUID by param()
    lateinit var area : TextArea
    override val root = vbox {
        area = textarea {
            //prefHeightProperty().bind(primaryStage.heightProperty())
            style{
                fontSize = 1.em
            }
            isWrapText = true
            prefWidth = 360.0
            prefHeight = 560.0
            //text = "42444-35050\n42444-35050\n42423-20010\n90310-50001\n90310-50006\n86510-35050\n86510-48070\n86510-35060"
        }
        buttonbar {
            style{
                padding = box(10.px)
            }
            button("Clear and Add", ButtonBar.ButtonData.RIGHT).setOnAction {
                fire(PartsAdded(checkId,area.text,AddType.clearAndAdd))
                area.clear()
                close()
            }
            button("Add", ButtonBar.ButtonData.RIGHT).setOnAction {
                fire(PartsAdded(checkId,area.text,AddType.add))
                area.clear()
                close()
            }

        }
    }
}
