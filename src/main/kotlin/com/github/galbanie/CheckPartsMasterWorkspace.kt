package com.github.galbanie

import com.github.galbanie.controllers.CheckPartsMasterController
import com.github.galbanie.views.*
import javafx.geometry.Insets
import tornadofx.*

/**
 * Created by Galbanie on 2017-07-31.
 */
class CheckPartsMasterWorkspace : Workspace("Check Part Master", Workspace.NavigationMode.Tabs) {
    init {
        add(MainMenu::class)
        add(SearchView::class)
        //add(Control::class)
    }

    init {
        with(leftDrawer){
            item(CheckPartsList::class){
                padding = Insets(10.0)
            }
            item(SourcesList::class){
                padding = Insets(10.0)
            }
        }
        with(bottomDrawer){
            item("Output") {
                textarea {

                }
            }
        }
    }

    init {
        dockInNewScope<Home>()
        inDynamicComponentMode {
            tabContainer.tabs.onChange {
                if(tabContainer.tabs.isEmpty()){
                    /*dockedComponentProperty.unbind()
                    dockedComponentProperty.value = null
                    titleProperty.unbind()
                    titleProperty.value = "Check Parts Master"
                    refreshButton.disableProperty().unbind()
                    refreshButton.disableProperty().value = true
                    saveButton.disableProperty().unbind()
                    saveButton.disableProperty().value = true
                    deleteButton.disableProperty().unbind()
                    deleteButton.disableProperty().value = true
                    headingContainer.removeFromParent()
                    headingProperty.unbind()
                    println(headingProperty.value)
                    headingProperty.value = "Check"
                    println(headingProperty.value)
                    println(dockedComponent)*/
                    dockInNewScope<Home>()
                }
            }
        }
    }

}
