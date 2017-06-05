package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class MyTable implements Serializable {

    private Integer id_user;
    private Integer id_tip;
    private Integer id_conferinta;
    private Integer id_sesiune;

    public MyTable() {
    }

    public MyTable(Integer id_user, Integer id_tip, Integer id_conferinta, Integer id_sesiune) {
        this.id_user = id_user;
        this.id_tip = id_tip;
        this.id_conferinta = id_conferinta;
        this.id_sesiune = id_sesiune;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_tip() {
        return id_tip;
    }

    public void setId_tip(Integer id_tip) {
        this.id_tip = id_tip;
    }

    public Integer getId_conferinta() {
        return id_conferinta;
    }

    public void setId_conferinta(Integer id_conferinta) {
        this.id_conferinta = id_conferinta;
    }

    public Integer getId_sesiune() {
        return id_sesiune;
    }

    public void setId_sesiune(Integer id_sesiune) {
        this.id_sesiune = id_sesiune;
    }

}
