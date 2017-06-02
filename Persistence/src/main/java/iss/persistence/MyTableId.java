package iss.persistence;

import java.io.Serializable;

import iss.model.*;

/**
 * Created by flori on 5/21/2017.
 */
public class MyTableId implements Serializable {
    private User user;
    private Role rol;
    private Conference conference;
    private Session session;

    public MyTableId(User user, Role rol, Conference conference, Session session) {
        this.user = user;
        this.rol = rol;
        this.conference = conference;
        this.session = session;
    }

    public MyTableId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyTableId)) return false;

        MyTableId myTableId = (MyTableId) o;

        if (!getUser().equals(myTableId.getUser())) return false;
        if (!getRol().equals(myTableId.getRol())) return false;
        if (!getConference().equals(myTableId.getConference())) return false;
        return getSession().equals(myTableId.getSession());
    }

    @Override
    public int hashCode() {
        int result = getUser().hashCode();
        result = 31 * result + getRol().hashCode();
        result = 31 * result + getConference().hashCode();
        result = 31 * result + getSession().hashCode();
        return result;
    }
}
