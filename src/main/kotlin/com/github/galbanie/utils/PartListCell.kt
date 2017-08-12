package com.github.galbanie.utils

import com.github.galbanie.PartUpdated
import com.github.galbanie.models.Part
import javafx.scene.control.ContentDisplay
import javafx.scene.control.ListCell
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import tornadofx.*
import java.util.*

/**
 * Created by Galbanie on 2017-08-11.
 */
class PartListCell(val checkId : UUID) : ListCell<Part>() {
    val textfield = TextField()

    init {
        textfield.addEventFilter(KeyEvent.KEY_PRESSED){ event ->
            if(event.code == KeyCode.ESCAPE){
                cancelEdit()
            }
        }
        textfield.setOnAction {
            if(item.part != textfield.text){
                var old = item
                var new = Part(textfield.text, false)
                FX.eventbus.fire(PartUpdated(checkId, old, new))
                item.part = textfield.text
                item.check = false
                this.text = textfield.text + if(item!!.check) "\t (Done)" else "\t (To Do)"
            }
            contentDisplay = ContentDisplay.TEXT_ONLY
        }
        graphic = textfield
    }

    override fun updateItem(item: Part?, empty: Boolean) {
        super.updateItem(item, empty)
        if (isEditing){
            textfield.text = item!!.part
            contentDisplay = ContentDisplay.GRAPHIC_ONLY
        }
        else{
            contentDisplay = ContentDisplay.TEXT_ONLY
            if (empty or (item == null)){
                text = null
            }
            else{
                text = item!!.part + if(item.check) "\t (Done)" else "\t (To Do)"
            }
        }
    }

    override fun startEdit() {
        super.startEdit()
        textfield.text = item.part
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
        textfield.requestFocus()
        textfield.selectAll()
    }

    override fun cancelEdit() {
        super.cancelEdit()
        text = item.part + if(item.check) "\t (Done)" else "\t (To Do)"
        contentDisplay = ContentDisplay.TEXT_ONLY
    }
}