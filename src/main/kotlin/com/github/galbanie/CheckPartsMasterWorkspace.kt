package com.github.galbanie

import com.github.galbanie.views.*
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
            item(CheckPartsList::class)
            item(SourcesList::class)
        }
        with(bottomDrawer){
            item("Output") {
                textarea {

                }
            }
        }
    }

    init {

    }

}
