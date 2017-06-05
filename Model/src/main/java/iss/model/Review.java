package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Review implements Serializable {

    private Integer id_user;
    private Integer id_paper;
    private String qualifiers;
    private String recommendations;

    public Review() {
    }

    public Review(Integer id_user, Integer id_paper, String qualifiers, String recommendations) {
        this.id_user = id_user;
        this.id_paper = id_paper;
        this.qualifiers = qualifiers;
        this.recommendations = recommendations;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_paper() {
        return id_paper;
    }

    public void setId_paper(Integer id_paper) {
        this.id_paper = id_paper;
    }

    public String getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(String qualifiers) {
        this.qualifiers = qualifiers;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
}
