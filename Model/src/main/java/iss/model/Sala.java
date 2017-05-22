package iss.model;

/**
 * Created by Bitten Apple on 21-May-17.
 */
public class Sala {

    private Integer id;
    private String nume;

    public Sala() {
    }

    public Sala(Integer id, String nume) {
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
