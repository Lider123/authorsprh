package ru.babaetskv.authorsprh.data.network.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "titles")
class TitlesModel {
    @ElementList(name = "isbn", inline = true, required = false)
    List<IsbnModel> values;
}
