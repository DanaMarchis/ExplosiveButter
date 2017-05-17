package iss.services;

import iss.model.User;

/**
 * Created by Bitten Apple on 15-May-17.
 */
public interface IConfServer {

    void login(User user, IConfClient client) throws ConfException;
    void logout(User user, IConfClient client) throws ConfException;
    void register(User user) throws ConfException;

}
