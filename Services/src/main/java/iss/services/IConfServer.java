package iss.services;

import iss.model.*;
import iss.model.dto.Name_and_Topic;

import java.io.File;

/**
 * Created by Bitten Apple on 15-May-17.
 */
public interface IConfServer {

    User login(User user, IConfClient client) throws ConfException;
    void logout(User user, IConfClient client) throws ConfException;
    void register(User user) throws ConfException;
    Conference[] getAllConferences() throws ConfException; //toate conferintele
    Session[] getSessions(Conference conf) throws ConfException; //toate sesiunile pt o anumita conferinta
    Role[] getRoles(User user) throws ConfException; //toate rolurile unui anumit user
    Conference[] getConferences(User user, Role role) throws ConfException; //toate conferintele pentru un anumit user cu un anumit rol
    Conference[] getAllConferencesDeadline() throws ConfException; //toate conferintele pt care deadline-ul nu a expirat
    File getAbstract(Abstract_Details abstract_details) throws ConfException; //getAbstract primeste numele lucrarii si topicul, intoarce file
    File getFull(Abstract_Details abstract_details) throws ConfException; //getFull la fel (legat de abstract prin Paper)
    Abstract_Details[] getNameAndTopic(iss.model.Session session) throws ConfException; //getName&Topic care intoarce o lista cu numele si topicul lucrarilor
    void review(Abstract_Details abstract_details, String qualifier, String recomandarea, User userlogat) throws ConfException; //review care primeste name, topic(astea pt paper), qualifier, recomandarea si userul logat ca reviewer si salveaza in tabela review
    Session[] getSesiuniConferintaUserRol(Conference conferinta, User user, Role rol) throws ConfException; //getSesiuni pt o conferinta, user si rol ... Asta pt tabelul din MyConferences
    void attend(User user, Role rol, Conference conference, Session session) throws ConfException; //attend .. Care primeste userul, rolul, conferinta si sesiunea si salveaza in bd in tabela MyTable
    boolean verifica(User userlogat, Paper paper) throws ConfException; //O metoda care intoarce true sau false care (momentan, cred eu ca) primeste userlogat si sesiunea ... Si verifica daca idAbstract e completat, adica nu e null sau -1.. Nu stiu exact cum se retine in bd
    void submitAbstract(String name, String topics, String keywords, String filepath, String detalii_autori, Session session, User user) throws ConfException;
    void submitFull(String filepath, Session session, User user) throws ConfException;
}
