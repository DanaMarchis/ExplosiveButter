package iss.persistence;

import java.io.Serializable;
import iss.model.*;

/**
 * Created by flori on 5/21/2017.
 */
public class MyTable implements Serializable{
    private User user;
    private Role rol;
    private Conference conference;
    private Session session;

    public MyTable(User user, Role rol, Conference conference, Session session) {
        this.user = user;
        this.rol = rol;
        this.conference = conference;
        this.session = session;
    }

    public MyTable() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
