/**
 * Build-Id: EMPTY
 */
package com.vraidsys.shared;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;

/**
 * @author jzerbe
 */
public class FooObj {

    private int myId;
    private String myName;
    @Email
    @Length(max = 254)
    private String myEmail;
    private List<String> myList;

    @JsonProperty("Email")
    public String getEmail() {
        return this.myEmail;
    }

    @JsonProperty("Id")
    public int getId() {
        return this.myId;
    }

    @JsonProperty("List")
    public List<String> getList() {
        return this.myList;
    }

    @JsonProperty("Name")
    public String getName() {
        return this.myName;
    }

    @JsonProperty("Email")
    public void setEmail(final String theEmail) {
        this.myEmail = theEmail;
    }

    @JsonProperty("Id")
    public void setId(final int theId) {
        this.myId = theId;
    }

    @JsonProperty("List")
    public void setList(final List<String> theList) {
        this.myList = theList;
    }

    @JsonProperty("Name")
    public void setName(final String theName) {
        this.myName = theName;
    }
}
