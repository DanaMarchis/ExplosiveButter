package iss.model.dto;

import iss.model.Session;
import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 06-Jun-17.
 */
public class Verifica_DTOHelper implements Serializable {

    private User user;
    private Session session;

    public Verifica_DTOHelper() {
    }

    public Verifica_DTOHelper(User user, Session session) {
        this.user = user;
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
