package ru.babaetskv.authorsprh.data.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "author")
public class AuthorModel {
    @Attribute(name = "uri") private String uri;
    @Element(name = "approved") private String approved;
    @Element(name = "authordisplay") private String displayName;
    @Element(name = "authorid") private Long authorId;
    @Element(name = "authorfirst", required = false) private String firstName;
    @Element(name = "authorlast") private String lastName;
    @Element(name = "authorfirstlc", required = false) private String firstNameLc;
    @Element(name = "authorlastlc") private String lastNameLc;
    @Element(name = "authorlastfirst") private String fullNameUc;
    @Element(name = "lastinitial") private String lastInitial;
    @Element(name = "photocredit", required = false) private String photoCredit;
    @Element(name = "titles") private TitlesModel titles;
    @Element(name = "works") private WorksModel works;
    @Element(name = "spotlight", required = false) private String spotlight;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstNameLc() {
        return firstNameLc;
    }

    public void setFirstNameLc(String firstNameLc) {
        this.firstNameLc = firstNameLc;
    }

    public String getLastNameLc() {
        return lastNameLc;
    }

    public void setLastNameLc(String lastNameLc) {
        this.lastNameLc = lastNameLc;
    }

    public String getFullNameUc() {
        return fullNameUc;
    }

    public void setFullNameUc(String fullNameUc) {
        this.fullNameUc = fullNameUc;
    }

    public String getLastInitial() {
        return lastInitial;
    }

    public void setLastInitial(String lastInitial) {
        this.lastInitial = lastInitial;
    }

    public String getPhotoCredit() {
        return photoCredit;
    }

    public void setPhotoCredit(String photoCredit) {
        this.photoCredit = photoCredit;
    }

    public String getSpotlight() {
        return spotlight;
    }

    public void setSpotlight(String spotlight) {
        this.spotlight = spotlight;
    }
}
