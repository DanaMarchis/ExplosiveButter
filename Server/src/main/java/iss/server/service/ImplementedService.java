package iss.server.service;

import iss.model.User;
import iss.persistence.UserRepository;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by flori on 5/17/2017.
 */
public class ImplementedService implements IConfServer{

    private UserRepository userRepo;
    private Map<String, IConfClient> loggedClients;

    public ImplementedService(UserRepository userRepo) {
        this.userRepo = userRepo;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized  void login(User user, IConfClient client) throws ConfException {
        List<User> users=userRepo.getAll();
        for(User u:users){
            if(u.getUsername().equals(user.getUsername())&&u.getPassword().equals(user.getPassword())){
                if (loggedClients.get(user.getUsername()) != null)
                    throw new ConfException("User already logged in.");
                loggedClients.put(user.getUsername(), client);
                return;
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
}
