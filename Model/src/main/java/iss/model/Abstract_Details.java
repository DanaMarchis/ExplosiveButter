package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Abstract_Details implements Serializable {

    private Integer id;
    private String name;
    private String keywords;
    private String topics;
    private String detalii_autori;

    public Abstract_Details() {
    }

    public Abstract_Details(Integer id, String name, String keywords, String topics, String detalii_autori) {
        this.id = id;
        this.name = name;
        this.keywords = keywords;
        this.topics = topics;
        this.detalii_autori = detalii_autori;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getDetalii_autori() {
        return detalii_autori;
    }

    public void setDetalii_autori(String detalii_autori) {
        this.detalii_autori = detalii_autori;
    }

}
