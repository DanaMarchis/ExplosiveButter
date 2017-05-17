package iss.networking.dto;

import java.io.Serializable;

/**
 * Created by Dana on 17-May-17.
 */

//UserDTO doar cu username si parola, pt Logout
public class UserDTO_up implements Serializable{
    private String username;
    private String password;

    public UserDTO_up(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return "UserDTO_up["+username+' '+password+"]";
    }
}
