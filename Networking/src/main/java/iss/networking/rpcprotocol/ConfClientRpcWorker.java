package iss.networking.rpcprotocol;

import iss.model.User;
import iss.networking.dto.DTOUtils;
import iss.networking.dto.UserDTO;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Bitten Apple on 15-May-17.
 */
public class ConfClientRpcWorker implements Runnable, IConfClient {

    //referinta la server + conexiunea
    private IConfServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ConfClientRpcWorker(IConfServer server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

//    @Override
//    public void conferencesUpdate(List<Conference> conferenceList) throws ConfException {
//        Conference[] conferences = new Conference[conferenceList.size()];
//        for (int i = 0; i < conferenceList.size(); i++) {
//            conferences[i] = conferenceList.get(i);
//        }
//        ConferenceDTO[] conferenceDTOS = DTOUtils.getDTO(conferences);
//        Response resp = new Response.Builder().type(ResponseType.CONFERENCES_UPDATE).data(conferenceDTOS).build();
//        System.out.println("Conferences received");
//        try {
//            sendResponse(resp);
//        } catch (IOException e) {
//            throw new ConfException("Sending error: " + e);
//        }
//    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request) {
        Response response = null;

        //daca request-ul e de login
        if (request.type() == RequestType.LOGIN) {
            System.out.println("Login request ..." + request.type());
            UserDTO userDTO = (UserDTO) request.data();
            User user = DTOUtils.getFromDTO(userDTO);
            try {
                server.login(user, this);
                return okResponse;
            } catch (ConfException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        //daca request-ul e de logout
        if (request.type() == RequestType.LOGOUT) {
            System.out.println("Logout request");
            // LogoutRequest logReq=(LogoutRequest)request;
            UserDTO userDTO = (UserDTO) request.data();
            User user = DTOUtils.getFromDTO(userDTO);
            try {
                server.logout(user, this);
                connected = false;
                return okResponse;

            } catch (ConfException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        //daca request-ul e de la register
        if (request.type() == RequestType.REGISTER){
            System.out.println("Register request");
            UserDTO userDTO = (UserDTO) request.data();
            User user = DTOUtils.getFromDTO(userDTO);
            try{
                server.register(user);
                return okResponse;
            } catch (ConfException e) {
//                e.printStackTrace();
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        //daca request-ul e de conferences
        if (request.type() == RequestType.CONFERENCES) {
            System.out.println("Conferences request ..." + request.type());
            try {
                Conference[] conferences = server.getAllConferences();
                ConferenceDTO[] conferenceDTOS = DTOUtils.getDTO(conferences);
                return new Response.Builder().type(ResponseType.OK).data(conferenceDTOS).build();
            } catch (ConfException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        //daca request-ul e de sessions for conference
        if (request.type() == RequestType.SESSIONS_FOR_CONFERENCE) {
            System.out.println("Sessions for conference request ..." + request.type());
            ConferenceDTO conferenceDTO = (ConferenceDTO) request.data();
            Conference conference = DTOUtils.getFromDTO(conferenceDTO);
            try {
                Session[] sessions = server.getSessions(conference);
                SessionDTO[] sessionDTOS = DTOUtils.getDTO(sessions);
                return new Response.Builder().type(ResponseType.OK).data(sessionDTOS).build();
            } catch (ConfException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        //daca request-ul e de roles for user
        if (request.type() == RequestType.ROLES_FOR_USER) {
            System.out.println("Roles for user request ..." + request.type());
            UserDTO userDTO = (UserDTO) request.data();
            User user = DTOUtils.getFromDTO(userDTO);
            try {
                Role[] roles = server.getRoles(user);
                RoleDTO[] roleDTOS = DTOUtils.getDTO(roles);
                return new Response.Builder().type(ResponseType.OK).data(roleDTOS).build();
            } catch (ConfException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        //daca request-ul e de conferences for user and role
        if (request.type() == RequestType.CONFERENCES_FOR_USER_AND_ROLE) {
            System.out.println("Conferences for user and role request ..." + request.type());
            UserRoleDTO userRoleDTO = (UserRoleDTO) request.data();
            UserRole userRole = DTOUtils.getFromDTO(userRoleDTO);
            User user = userRole.getUser();
            Role role = userRole.getRole();
            try {
                Conference[] conferences = server.getConferences(user, role);
                ConferenceDTO[] conferenceDTOS = DTOUtils.getDTO(conferences);
                return new Response.Builder().type(ResponseType.OK).data(conferenceDTOS).build();
            } catch (ConfException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        //daca request-ul e de conferences with deadline not expired
        if (request.type() == RequestType.CONFERENCES_WITH_DEADLINE_NOT_EXPIRED) {
            System.out.println("Conferences with deadline not expired request ..." + request.type());
            try {
                Conference[] conferences = server.getAllConferencesDeadline();
                ConferenceDTO[] conferenceDTOS = DTOUtils.getDTO(conferences);
                return new Response.Builder().type(ResponseType.OK).data(conferenceDTOS).build();
            } catch (ConfException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        output.writeObject(response);
        output.flush();
    }

}
