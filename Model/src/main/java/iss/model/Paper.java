package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Paper implements Serializable {

    private Integer id_paper;
    private Integer id_user_author;
    private Integer id_abstract;
    private Integer id_full;

    public Paper() {
    }

    public Paper(Integer id_paper, Integer id_user_author, Integer id_abstract, Integer id_full) {
        this.id_paper = id_paper;
        this.id_user_author = id_user_author;
        this.id_abstract = id_abstract;
        this.id_full = id_full;
    }

    public Integer getId_paper() {
        return id_paper;
    }

    public void setId_paper(Integer id_paper) {
        this.id_paper = id_paper;
    }

    public Integer getId_user_author() {
        return id_user_author;
    }

    public void setId_user_author(Integer id_user_author) {
        this.id_user_author = id_user_author;
    }

    public Integer getId_abstract() {
        return id_abstract;
    }

    public void setId_abstract(Integer id_abstract) {
        this.id_abstract = id_abstract;
    }

    public Integer getId_full() {
        return id_full;
    }

    public void setId_full(Integer id_full) {
        this.id_full = id_full;
    }

}
