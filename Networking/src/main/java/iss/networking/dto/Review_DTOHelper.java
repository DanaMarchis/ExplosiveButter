package iss.networking.dto;

import iss.model.Abstract_Details;
import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Review_DTOHelper implements Serializable {

    private Abstract_Details abstract_details;
    private String qualifier;
    private String recomandare;
    private User user;

    public Review_DTOHelper() {
    }

    public Review_DTOHelper(Abstract_Details abstract_details, String qualifier, String recomandare, User user) {
        this.abstract_details = abstract_details;
        this.qualifier = qualifier;
        this.recomandare = recomandare;
        this.user = user;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getRecomandare() {
        return recomandare;
    }

    public void setRecomandare(String recomandare) {
        this.recomandare = recomandare;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Abstract_Details getAbstract_details() {
        return abstract_details;
    }

    public void setAbstract_details(Abstract_Details abstract_details) {
        this.abstract_details = abstract_details;
    }
}
