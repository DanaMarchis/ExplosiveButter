package iss.model;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Full_Details implements Serializable {

    private Integer id;
    private String filePath; //not sure this is the correct type

    public Full_Details() {
    }

    public Full_Details(Integer id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
