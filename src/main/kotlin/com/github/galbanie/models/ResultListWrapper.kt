package com.github.galbanie.models

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * Created by Galbanie on 2017-08-13.
 */
@XmlRootElement(name = "results")
class ResultListWrapper {
    @XmlElement(name = "result")
    val results : MutableCollection<Result> = mutableListOf()
}