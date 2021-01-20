package ru.babaetskv.authorsprh.data.network.model;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "works")
class WorkModel {
    @Text private String value;
}
