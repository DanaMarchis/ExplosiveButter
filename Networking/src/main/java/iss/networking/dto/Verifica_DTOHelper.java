package iss.networking.dto;

import iss.model.Paper;
import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 06-Jun-17.
 */
public class Verifica_DTOHelper implements Serializable {

    private User user;
    private Paper paper;

    public Verifica_DTOHelper() {
    }

    public Verifica_DTOHelper(User user, Paper paper) {
        this.user = user;
        this.paper = paper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}
