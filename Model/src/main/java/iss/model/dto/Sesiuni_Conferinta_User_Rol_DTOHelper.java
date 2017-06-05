package iss.model.dto;

import iss.model.Conference;
import iss.model.Role;
import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 05-Jun-17.
 */
public class Sesiuni_Conferinta_User_Rol_DTOHelper implements Serializable {

    private Conference conference;
    private User user;
    private Role role;

    public Sesiuni_Conferinta_User_Rol_DTOHelper() {
    }

    public Sesiuni_Conferinta_User_Rol_DTOHelper(Conference conference, User user, Role role) {
        this.conference = conference;
        this.user = user;
        this.role = role;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
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

}
