package ru.babaetskv.authorsprh.data.network.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "works")
class WorksModel {
    @ElementList(name = "works", inline = true, required = false) private List<WorkModel> works;
}

