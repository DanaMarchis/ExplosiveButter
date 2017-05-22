package iss.networking.dto;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 21-May-17.
 */
public class SalaDTO implements Serializable {

    private Integer id;
    private String nume;


    public SalaDTO(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
