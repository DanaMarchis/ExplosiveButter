package iss.services;

import iss.model.Conference;
import iss.model.Role;
import iss.model.Session;
import iss.model.User;

/**
 * Created by Bitten Apple on 15-May-17.
 */
public interface IConfServer {

    void login(User user, IConfClient client) throws ConfException;
    void logout(User user, IConfClient client) throws ConfException;
    void register(User user) throws ConfException;
    Conference[] getAllConferences() throws ConfException; //toate conferintele
    Session[] getSessions(Conference conf) throws ConfException; //toate sesiunile pt o anumita conferinta
    Role[] getRoles(User user) throws ConfException; //toate rolurile unui anumit user
    Conference[] getConferences(User user, Role role) throws ConfException; //toate conferintele pentru un anumit user cu un anumit rol
    Conference[] getAllConferencesDeadline() throws ConfException; //toate conferintele pt care deadline-ul nu a expirat

}
