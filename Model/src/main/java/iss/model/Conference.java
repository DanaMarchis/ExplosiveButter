package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 21-May-17.
 */
public class Conference implements Serializable {

    private Integer id;
    private String nume;
    private String deadline_abs;
    private String deadline_full;
    private String data_inc;
    private String data_sf;

    public Conference() {
    }

    public Conference(Integer id, String nume, String deadline_abs, String deadline_full, String data_inc, String data_sf) {
        this.id = id;
        this.nume = nume;
        this.deadline_abs = deadline_abs;
        this.deadline_full = deadline_full;
        this.data_inc = data_inc;
        this.data_sf = data_sf;
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

    public String getDeadline_abs() {
        return deadline_abs;
    }

    public void setDeadline_abs(String deadline_abs) {
        this.deadline_abs = deadline_abs;
    }

    public String getDeadline_full() {
        return deadline_full;
    }

    public void setDeadline_full(String deadline_full) {
        this.deadline_full = deadline_full;
    }

    public String getData_inc() {
        return data_inc;
    }

    public void setData_inc(String data_inc) {
        this.data_inc = data_inc;
    }

    public String getData_sf() {
        return data_sf;
    }

    public void setData_sf(String data_sf) {
        this.data_sf = data_sf;
    }
}
