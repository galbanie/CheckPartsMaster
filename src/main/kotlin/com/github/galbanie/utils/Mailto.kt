package com.github.galbanie.utils

import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder

/**
 * Created by Galbanie on 2017-09-17.
 */
class Mailto {
    fun mailto(recipients : List<String>, subject : String, body : String){
        val uri = String.format("mailto:%s?subject=%s&body=%s",
                join(";", recipients), // use semicolon ";" for Outlook!
                urlEncode(subject),
                urlEncode(body))

        Desktop.getDesktop().browse(URI(uri));
    }

    private fun join(separator : String, objects : Iterable<*>) : String{
        val sb = StringBuilder()
        for (obj in objects){
            if(sb.length > 0) sb.append(separator)
            sb.append(obj)
        }
        return sb.toString()
    }

    private fun urlEncode(str : String) : String{
        return URLEncoder.encode(str,"UTF-8").replace("+","%20")
    }
}