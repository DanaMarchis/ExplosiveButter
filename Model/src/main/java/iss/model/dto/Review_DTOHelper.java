package iss.model.dto;

import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Review_DTOHelper implements Serializable {

    private String name;
    private String topic;
    private String qualifier;
    private String recomandare;
    private User user;

    public Review_DTOHelper() {
    }

    public Review_DTOHelper(String name, String topic, String qualifier, String recomandare, User user) {
        this.name = name;
        this.topic = topic;
        this.qualifier = qualifier;
        this.recomandare = recomandare;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

}
