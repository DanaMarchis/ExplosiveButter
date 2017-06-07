package iss.networking.rpcprotocol;

import iss.model.*;
import iss.model.dto.*;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Bitten Apple on 15-May-17.
 */
public class ConfServerRpcProxy implements IConfServer {

    //connection details
    private String host;
    private int port;

    //userul logat + clientul (pt fiecare client se face cate un proxy)
    private User userLogat;
    private IConfClient client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ConfServerRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
    }

    @Override
    public User login(User user, IConfClient client) throws ConfException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.LOGIN).data(user).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            System.out.println("User logat cu succes"); //s-a logat cu succes
            this.userLogat = (User) response.data();
            this.client = client;
            return userLogat;
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new ConfException(err);
        }
        return null;
    }

    @Override
    public void logout(User user, IConfClient client) throws ConfException {
        //folosesc userLogat nu user (Dana imi trimite un user la misto, doar ca sa fie, pt ca asa e in interfata)
//        UserDTO userDTO = DTOUtils.getDTO(userLogat);
//        Request req = new Request.Builder().type(RequestType.LOGOUT).data(userDTO).build();
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(userLogat).build();

        sendRequest(req);

        Response response = readResponse();

//        closeConnection();
        if (response.type() == ResponseType.OK) {
            System.out.println("user delogat"); //s-a delogat cu succes
            closeConnection();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }
    }

    @Override
    public void register(User user) throws ConfException {
        initializeConnection();

//        UserDTO userDTO = DTOUtils.getDTO(user);
//        Request req = new Request.Builder().type(RequestType.REGISTER).data(userDTO).build();
        Request req = new Request.Builder().type(RequestType.REGISTER).data(user).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            System.out.println("User inregistrat cu succes"); //s-a inregistrat cu succes
            closeConnection();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
//            closeConnection();
            throw new ConfException(err);
        }
    }

    @Override
    public Conference[] getAllConferences() throws ConfException {
        Request req = new Request.Builder().type(RequestType.CONFERENCES).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
//            Conference[] conferences = DTOUtils.getFromDTO((ConferenceDTO[]) response.data());
            return (Conference[]) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public Session[] getSessions(Conference conf) throws ConfException {
        Request req = new Request.Builder().type(RequestType.SESSIONS_FOR_CONFERENCE).data(conf).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            return (Session[]) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }
        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public Role[] getRoles(User user) throws ConfException {
//        UserDTO userDTO = DTOUtils.getDTO(user);
//        Request req = new Request.Builder().type(RequestType.ROLES_FOR_USER).data(userDTO).build();
        Request req = new Request.Builder().type(RequestType.ROLES_FOR_USER).data(userLogat).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
//            Role[] roles = DTOUtils.getFromDTO((RoleDTO[]) response.data());
            return (Role[]) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public Conference[] getConferences(User user, Role role) throws ConfException {
        UserRole userRole = new UserRole(userLogat, role);
//        UserRoleDTO userRoleDTO = DTOUtils.getDTO(userRole);
//        Request req = new Request.Builder().type(RequestType.CONFERENCES_FOR_USER_AND_ROLE).data(userRoleDTO).build();
        Request req = new Request.Builder().type(RequestType.CONFERENCES_FOR_USER_AND_ROLE).data(userRole).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
//            Conference[] conferences = DTOUtils.getFromDTO((ConferenceDTO[]) response.data());
            return (Conference[]) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public Conference[] getAllConferencesDeadline() throws ConfException {
        Request req = new Request.Builder().type(RequestType.CONFERENCES_WITH_DEADLINE_NOT_EXPIRED).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
//            Conference[] conferences = DTOUtils.getFromDTO((ConferenceDTO[]) response.data());
            return (Conference[]) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public File getAbstract(String nume, String topic) throws ConfException {
        Name_and_Topic name_and_topic = new Name_and_Topic(nume, topic);
        Request req = new Request.Builder().type(RequestType.ABSTRACT).data(name_and_topic).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            return (File) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public File getFull(String nume, String topic) throws ConfException {
        Name_and_Topic name_and_topic = new Name_and_Topic(nume, topic);
        Request req = new Request.Builder().type(RequestType.FULL).data(name_and_topic).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            return (File) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public Name_and_Topic[] getNameAndTopic() throws ConfException {
        Request req = new Request.Builder().type(RequestType.NAME_AND_TOPIC).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            return (Name_and_Topic[]) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public void review(String name, String topic, String qualifier, String recomandarea, User userlogat) throws ConfException {
        Review_DTOHelper review_dtoHelper = new Review_DTOHelper(name, topic, qualifier, recomandarea, userLogat);
        Request req = new Request.Builder().type(RequestType.REVIEW).data(review_dtoHelper).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            System.out.println("review facut cu succes");
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }
    }

    @Override
    public Session[] getSesiuniConferintaUserRol(Conference conferinta, User user, Role rol) throws ConfException {
        Sesiuni_Conferinta_User_Rol_DTOHelper sesiuni_conferinta_user_rol_dtoHelper = new Sesiuni_Conferinta_User_Rol_DTOHelper(conferinta, userLogat, rol);
        Request req = new Request.Builder().type(RequestType.SESIUNI_CONFERINTA_USER_ROL).data(sesiuni_conferinta_user_rol_dtoHelper).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            return (Session[]) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public void attend(User user, Role rol, Conference conference, Session session) throws ConfException {
        Attend_DTOHelper attend_dtoHelper = new Attend_DTOHelper(userLogat, rol, conference, session);
        Request req = new Request.Builder().type(RequestType.ATTEND).data(attend_dtoHelper).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            System.out.println("attend facut cu succes");
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }
    }

    @Override
    public boolean verifica(User userlogat, Session session) throws ConfException {
        Verifica_DTOHelper verifica_dtoHelper = new Verifica_DTOHelper(userLogat, session);
        Request req = new Request.Builder().type(RequestType.VERIFICA).data(verifica_dtoHelper).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            return (Boolean) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return false; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws ConfException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ConfException("Error sending object " + e);
        }

    }

    private Response readResponse() throws ConfException {
        Response response = null;
        try {
            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws ConfException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
//        aici prinde response-urile de la worker, inca nu am nimic deoarece interfata IConfClient e goala
    }

    private boolean isUpdate(Response response) {
//        aici verifica daca response-ul citit mai jos este de tip update
        return false; //placeholder
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {

                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

}

