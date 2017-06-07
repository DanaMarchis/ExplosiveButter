package iss.model;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Paper implements Serializable {

    private Integer id_paper;
    private User user_author;
    private Abstract_Details abstract_details;
    private Full_Details full_details;
    private Session session;

    public Paper() {
    }

    public Paper(Integer id_paper, User user_author, Abstract_Details abstract_details, Full_Details full,Session session) {
        this.id_paper = id_paper;
        this.user_author = user_author;
        this.abstract_details = abstract_details;
        this.full_details = full;
        this.session=session;
    }

    public Integer getId_paper() {
        return id_paper;
    }

    public void setId_paper(Integer id_paper) {
        this.id_paper = id_paper;
    }

    public User getUser_author() {
        return user_author;
    }

    public void setUser_author(User user_author) {
        this.user_author = user_author;
    }

    public Abstract_Details getAbstract_details() {
        return abstract_details;
    }

    public void setAbstract_details(Abstract_Details abstract_details) {
        this.abstract_details = abstract_details;
    }

    public Full_Details getFull_details() {
        return full_details;
    }

    public void setFull_details(Full_Details full_details) {
        this.full_details = full_details;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
