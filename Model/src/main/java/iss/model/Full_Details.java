package iss.model;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Full_Details implements Serializable {

    private Integer id;
    private File file; //not sure this is the correct type

    public Full_Details() {
    }

    public Full_Details(Integer id, File file) {
        this.id = id;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
