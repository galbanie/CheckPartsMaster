package com.github.galbanie.models

/**
 * Created by Galbanie on 2017-08-14.
 */
@javax.xml.bind.annotation.XmlRootElement(name = "sources")
class SourceListWrapper {
    @javax.xml.bind.annotation.XmlElement(name = "source")
    val sources : MutableCollection<Source> = mutableListOf()
}