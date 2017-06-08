package iss.networking.rpcprotocol;

import iss.model.*;
//import iss.networking.dto.*;
import iss.networking.dto.*;
import iss.networking.dto.File_DTO;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;

import java.io.*;
import java.net.Socket;
import java.util.Map;
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
    public File getAbstract(Abstract_Details abstract_details) throws ConfException {
        Request req = new Request.Builder().type(RequestType.ABSTRACT).data(abstract_details).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
//            return (File) response.data();
            try {
                String[] split = abstract_details.getFilePath().split("/");
                String filename = split[split.length-1];
//                String abc = new String("abc");
                OutputStream out = new FileOutputStream("./Client/files/"+filename);

                File_DTO file_dto = (File_DTO) response.data();
                Map<Integer, byte[]> map = file_dto.getDictionary();

                int i;
                for (i=0; i<map.keySet().toArray().length; i++) {
                    byte[] bytes = map.get(i);
                    out.write(bytes, 0, bytes.length);
                }

                return new File("./Client/files/"+filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public File getFull(Abstract_Details abstract_details) throws ConfException {
        Request req = new Request.Builder().type(RequestType.FULL).data(abstract_details).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
//            return (File) response.data();
            try {

                File_DTO file_dto = (File_DTO) response.data();
                Map<Integer, byte[]> map = file_dto.getDictionary();

                String filename = file_dto.getFilepath();
                OutputStream out = new FileOutputStream("./Client/files/"+filename);

                int i;
                for (i=0; i<map.keySet().toArray().length; i++) {
                    byte[] bytes = map.get(i);
                    out.write(bytes, 0, bytes.length);
                }

                return new File("./Client/files/"+filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public Abstract_Details[] getNameAndTopic(iss.model.Session session) throws ConfException {
        Request req = new Request.Builder().type(RequestType.NAME_AND_TOPIC).data(session).build();

        sendRequest(req);

        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            return (Abstract_Details[]) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ConfException(err);
        }

        return null; //nu ajunge niciodata aici pt ca raspunsul e unul dintre tipurile de mai sus
    }

    @Override
    public void review(Abstract_Details abstract_details, String qualifier, String recomandarea, User userlogat) throws ConfException {
        Review_DTOHelper review_dtoHelper = new Review_DTOHelper(abstract_details, qualifier, recomandarea, userLogat);
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
    public boolean verifica(User userlogat, Paper paper) throws ConfException {
        Verifica_DTOHelper verifica_dtoHelper = new Verifica_DTOHelper(userLogat, paper);
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

    @Override
    public void submitAbstract(String name, String topics, String keywords, String filepath, String detalii_autori, Session session, User user) throws ConfException {

        try {

            File_DTO file_dto = new File_DTO();

            File file = new File(filepath);
            // Get the size of the file
            long length = file.length();
            byte[] bytes = new byte[16 * 1024];
            InputStream in = new FileInputStream(file);

            int count;
            while ((count = in.read(bytes)) > 0) {
//                    out.write(bytes, 0, count);
                file_dto.put(bytes);
            }

            Abstract_DTOHelper abstract_dtoHelper = new Abstract_DTOHelper(name, topics, keywords, filepath, detalii_autori, session, userLogat, file_dto);
            Request req = new Request.Builder().type(RequestType.SUBMIT_ABSTRACT).data(abstract_dtoHelper).build();

            sendRequest(req);

            Response response = readResponse();

            if (response.type() == ResponseType.OK) {
                System.out.println("submit abstract facut cu succes");
            }
            if (response.type() == ResponseType.ERROR) {
                String err = response.data().toString();
                throw new ConfException(err);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void submitFull(String filepath, Session session, User user) throws ConfException {

        try {

            File_DTO file_dto = new File_DTO();

            File file = new File(filepath);
            // Get the size of the file
            long length = file.length();
            byte[] bytes = new byte[16 * 1024];
            InputStream in = new FileInputStream(file);

            int count;
            while ((count = in.read(bytes)) > 0) {
//                    out.write(bytes, 0, count);
                file_dto.put(bytes);
            }

            Full_DTOHelper full_dtoHelper = new Full_DTOHelper(filepath, session, userLogat, file_dto);
            Request req = new Request.Builder().type(RequestType.SUBMIT_FULL).data(full_dtoHelper).build();

            sendRequest(req);

            Response response = readResponse();

            if (response.type() == ResponseType.OK) {
                System.out.println("submit full facut cu succes");
            }
            if (response.type() == ResponseType.ERROR) {
                String err = response.data().toString();
                throw new ConfException(err);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File submitAbstractFlorin(String name, String topics, String keywords, String filepath, String detalii_autori, Session session, User user) throws ConfException {
        return null;
    }

    @Override
    public File submitFullFlorin(String filepath, Session session, User user) throws ConfException {
        return null;
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

