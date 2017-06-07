package iss.server.service;

import iss.model.*;
import iss.model.dto.Name_and_Topic;
import iss.persistence.*;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;

import java.io.File;
import java.text.ParseException;
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
    private SessionRepository sessionRepository;
    private MyTableRepository myTableRepository;
    private ReviewRepository reviewRepository;
    private PaperRepository paperRepository;
    private Map<String, IConfClient> loggedClients;

    public ImplementedService(UserRepository userRepo,ConferenceRepo conferenceRepo,RoleRepo roleRepo,
                              SessionRepository sessionRepository,MyTableRepository myTableRepository,
                              ReviewRepository reviewRepository,PaperRepository paperRepository) {
        this.userRepo = userRepo;
        this.conferenceRepo=conferenceRepo;
        this.roleRepo=roleRepo;
        this.sessionRepository=sessionRepository;
        this.myTableRepository=myTableRepository;
        this.reviewRepository=reviewRepository;
        this.paperRepository=paperRepository;
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
        try {
            return conferenceRepo.getAllConferencesDeadline().toArray(new Conference[1]);
        } catch (ParseException e) {
            throw new ConfException("Formatul datei este incorect",e);
        }
    }

    @Override
    public File getAbstract(Abstract_Details abstract_details) throws ConfException {
        return null;
    }

    @Override
    public File getFull(Abstract_Details abstract_details) throws ConfException {
        try {
            return new File(paperRepository.getFullDetails(abstract_details).getFilePath());
        } catch (Exception e) {
            throw new ConfException("error while trying to find full paper",e);
        }
    }

    @Override
    public Abstract_Details[] getNameAndTopic(iss.model.Session session) throws ConfException {
        return sessionRepository.getNameAndTopic(session).toArray(new Abstract_Details[0]);
    }

    @Override
    public void review(Abstract_Details abstract_details, String qualifier, String recomandarea, User userlogat) throws ConfException {
        Paper paper=paperRepository.getPapersWithAbstract(abstract_details);
        if(paper!=null){
            Review review=new Review(userlogat,paper,qualifier,recomandarea);
            try {
                reviewRepository.save(review);
            } catch (Exception e) {
                throw new ConfException("Error while submitting review", e);
            }
        }
    }

    @Override
    public Session[] getSesiuniConferintaUserRol(Conference conferinta, User user, Role rol) throws ConfException {
        return sessionRepository.getSesiuniConferintaUserRol(conferinta,user,rol).toArray(new iss.model.Session[1]);
    }

    @Override
    public void attend(User user, Role rol, Conference conference, Session session) throws ConfException {
        if(rol.getDenumire().equals("speaker")){
            for(Role rol1:myTableRepository.getRolesOfUserInConferenceAndSession(user,conference,session)){
                if(rol1.getDenumire().equals("PC Member"))
                    throw new ConfException("Userul participa deja la aceasta sesiune ca PC Member");
            }
        }
        try {
            myTableRepository.attend(user,rol,conference,session);
        } catch (Exception e) {
            throw new ConfException("attending error",e);
        }
    }

    @Override
    public boolean verifica(User userlogat, Paper paper) throws ConfException {
        return false;
    }
}
