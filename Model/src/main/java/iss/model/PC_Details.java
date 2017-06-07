package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class PC_Details implements Serializable {

    private User user;
    private String webpage;
    private String affiliation;

    public PC_Details() {
    }

    public PC_Details(User user, String webpage, String affiliation) {
        this.user = user;
        this.webpage = webpage;
        this.affiliation = affiliation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user= user;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

}
