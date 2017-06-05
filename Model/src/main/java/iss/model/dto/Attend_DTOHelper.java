package iss.model.dto;

import iss.model.Conference;
import iss.model.Role;
import iss.model.Session;
import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 06-Jun-17.
 */
public class Attend_DTOHelper implements Serializable {

    private User user;
    private Role role;
    private Conference conference;
    private Session session;

    public Attend_DTOHelper() {
    }

    public Attend_DTOHelper(User user, Role role, Conference conference, Session session) {
        this.user = user;
        this.role = role;
        this.conference = conference;
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
