package ru.babaetskv.authorsprh.data.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "authors")
public class GetAuthorsResponseModel {
    @Attribute(name = "uri") String uri;
    @ElementList(inline = true, name="author", required = false) private List<AuthorModel> authors;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<AuthorModel> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorModel> authors) {
        this.authors = authors;
    }
}
