package com.github.galbanie.utils

import javafx.scene.control.ContentDisplay
import javafx.scene.control.ListCell
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

/**
 * Created by Galbanie on 2017-08-11.
 */
class EditStringListCell : ListCell<String>() {

    val textfield = TextField()

    init {
        textfield.addEventFilter(KeyEvent.KEY_PRESSED){ event ->
            if(event.code == KeyCode.ESCAPE){
                cancelEdit()
            }
        }
        textfield.setOnAction {
            if(item != textfield.text){
                item = textfield.text
                this.text = textfield.text
            }
            contentDisplay = ContentDisplay.TEXT_ONLY
        }
        graphic = textfield
    }

    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)
        if (isEditing){
            textfield.text = item
            contentDisplay = ContentDisplay.GRAPHIC_ONLY
        }
        else{
            contentDisplay = ContentDisplay.TEXT_ONLY
            if (empty or (item == null)){
                text = null
            }
            else{
                text = item
            }
        }
    }

    override fun startEdit() {
        super.startEdit()
        textfield.text = item
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
        textfield.requestFocus()
        textfield.selectAll()
    }

    override fun cancelEdit() {
        super.cancelEdit()
        text = item
        contentDisplay = ContentDisplay.TEXT_ONLY
    }
}