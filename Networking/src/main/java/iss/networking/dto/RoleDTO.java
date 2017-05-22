package iss.networking.dto;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 21-May-17.
 */
public class RoleDTO implements Serializable {

    private Integer id;
    private String denumire;

    public RoleDTO(Integer id, String denumire) {
        this.id = id;
        this.denumire = denumire;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
}
