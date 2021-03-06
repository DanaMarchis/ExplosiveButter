package iss.networking.dto;

import iss.model.Role;
import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 21-May-17.
 */
public class UserRole implements Serializable {

    private User user;
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
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
