package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 21-May-17.
 */
public class Session implements Serializable {

    private Integer id;
    private String nume;
    private String data;
    private String ora_inc;
    private String ora_sf;
    private Sala sala;
    private Integer pret;
    private Conference conference;

    public Session() {
    }

    public Session(Integer id, String nume, String data, String ora_inc, String ora_sf, Sala sala, Integer pret,Conference conference) {
        this.id = id;
        this.nume = nume;
        this.data = data;
        this.ora_inc = ora_inc;
        this.ora_sf = ora_sf;
        this.sala = sala;
        this.pret = pret;
        this.conference=conference;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra_inc() {
        return ora_inc;
    }

    public void setOra_inc(String ora_inc) {
        this.ora_inc = ora_inc;
    }

    public String getOra_sf() {
        return ora_sf;
    }

    public void setOra_sf(String ora_sf) {
        this.ora_sf = ora_sf;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Integer getPret() {
        return pret;
    }

    public void setPret(Integer pret) {
        this.pret = pret;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
}
