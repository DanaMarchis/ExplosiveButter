package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class PC_Details implements Serializable {

    private Integer id;
    private String webpage;
    private String affiliation;

    public PC_Details() {
    }

    public PC_Details(Integer id, String webpage, String affiliation) {
        this.id = id;
        this.webpage = webpage;
        this.affiliation = affiliation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
