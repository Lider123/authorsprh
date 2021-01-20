package ru.babaetskv.authorsprh.data.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "isbn")
class IsbnModel {
    @Attribute(name = "contributortype") private String contributorType;
    @Text private String value;
}
