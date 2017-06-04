package iss.server.service;

import iss.model.Conference;
import iss.model.Role;
import iss.model.Session;
import iss.model.User;
import iss.persistence.ConferenceRepo;
import iss.persistence.RoleRepo;
import iss.persistence.UserRepository;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created by flori on 5/17/2017.
 */
public class ImplementedService implements IConfServer{

    private UserRepository userRepo;
    private ConferenceRepo conferenceRepo;
    private RoleRepo roleRepo;
    private Map<String, IConfClient> loggedClients;

    public ImplementedService(UserRepository userRepo,ConferenceRepo conferenceRepo,RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.conferenceRepo=conferenceRepo;
        this.roleRepo=roleRepo;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized User login(User user, IConfClient client) throws ConfException {
        List<User> users=userRepo.getAll();
        for(User u:users){
            if(u.getUsername().equals(user.getUsername())&&u.getPassword().equals(user.getPassword())){
                if (loggedClients.get(user.getUsername()) != null)
                    throw new ConfException("User already logged in.");
                loggedClients.put(user.getUsername(), client);
                System.out.println(u.toString());
                return u;
            }
        }
        throw new ConfException("Authentication failed.");
    }
    @Override
    public synchronized void logout(User user, IConfClient client) throws ConfException {
        IConfClient localClient=loggedClients.remove(user.getUsername());
        if (localClient==null)
            throw new ConfException("User "+user.getUsername()+" is not logged in.");
    }

    @Override
    public void register(User user) throws ConfException {
        boolean emailOK = Pattern.matches("([a-zA-Z]+)([a-zA-Z0-9_.]*)([a-zA-Z0-9]+)@([a-zA-Z0-9]+)([.]{1})([a-z]{2,3})", user.getEmail());
        if(emailOK&&!user.getUsername().equals("")&&!user.getPassword().equals("")&&!user.getNume().equals("")&&!user.getPrenume().equals("")){
            List<User> users=userRepo.getAll();
            for(User u:users){
                if(u.getUsername().equals(user.getUsername())&&u.getPassword().equals(user.getPassword())){
                    throw new ConfException("User already registered.");
                }
            }
            try {
                userRepo.save(user);
                return;
            }
            catch (Exception ex){
                throw new ConfException("RegistrationError",ex);
            }
        }
        else{
            throw new ConfException("Invalid input data");
        }
    }

    @Override
    public Conference[] getAllConferences() throws ConfException {
        return conferenceRepo.getAll().toArray(new Conference[1]);
    }

    @Override
    public Session[] getSessions(Conference conf) throws ConfException {
        return conferenceRepo.getSessions(conf).toArray(new Session[1]);
    }

    @Override
    public Role[] getRoles(User user) throws ConfException {
        return roleRepo.getRoles(user).toArray(new Role[1]);
    }

    @Override
    public Conference[] getConferences(User user, Role role) throws ConfException {
        return conferenceRepo.getAll(user,role).toArray(new Conference[1]);
    }

    @Override
    public Conference[] getAllConferencesDeadline() throws ConfException {
        return new Conference[0];
    }
}
