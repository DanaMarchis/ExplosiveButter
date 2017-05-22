package iss.networking.dto;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 21-May-17.
 */
public class UserRoleDTO implements Serializable {

    private UserDTO user;
    private RoleDTO role;

    public UserRoleDTO(UserDTO user, RoleDTO role) {
        this.user = user;
        this.role = role;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
