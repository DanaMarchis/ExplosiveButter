package iss.networking.dto;

import iss.model.*;

/**
 * Created by Bitten Apple on 15-May-17.
 */
public class DTOUtils {

    //    ### USER ###
    public static User getFromDTO(UserDTO userDTO) {
        Integer id=userDTO.getId();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String nume = userDTO.getNume();
        String prenume = userDTO.getPrenume();
        String email = userDTO.getEmail();
        return new User(id,username, password, nume, prenume, email);
    }

    public static UserDTO getDTO(User user) {
        Integer id=user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        String nume = user.getNume();
        String prenume = user.getPrenume();
        String email = user.getEmail();
        return new UserDTO(id,username, password, nume, prenume, email);
    }


//    ### CONFERENCE ###
    public static Conference getFromDTO(ConferenceDTO conferenceDTO) {
        Integer id = conferenceDTO.getId();
        String nume = conferenceDTO.getNume();
        String deadline_abs = conferenceDTO.getDeadline_abs();
        String deadline_full = conferenceDTO.getDeadline_full();
        String data_inc = conferenceDTO.getData_inc();
        String data_sf = conferenceDTO.getData_sf();
        return new Conference(id, nume, deadline_abs, deadline_full, data_inc, data_sf);
    }

    public static ConferenceDTO getDTO(Conference conference) {
        Integer id = conference.getId();
        String nume = conference.getNume();
        String deadline_abs = conference.getDeadline_abs();
        String deadline_full = conference.getDeadline_full();
        String data_inc = conference.getData_inc();
        String data_sf = conference.getData_sf();
        return new ConferenceDTO(id, nume, deadline_abs, deadline_full, data_inc, data_sf);
    }

    public static ConferenceDTO[] getDTO(Conference[] conferences) {
        ConferenceDTO[] conferenceDTOS = new ConferenceDTO[conferences.length];
        for (int i = 0; i < conferences.length; i++)
            conferenceDTOS[i] = getDTO(conferences[i]);
        return conferenceDTOS;
    }

    public static Conference[] getFromDTO(ConferenceDTO[] conferenceDTOS) {
        Conference[] conferences = new Conference[conferenceDTOS.length];
        for (int i = 0; i < conferenceDTOS.length; i++) {
            conferences[i] = getFromDTO(conferenceDTOS[i]);
        }
        return conferences;
    }


//    ### SALA ###
    public static Sala getFromDTO(SalaDTO salaDTO) {
        Integer id = salaDTO.getId();
        String nume = salaDTO.getNume();
        return new Sala(id, nume);
    }

    public static SalaDTO getDTO(Sala sala) {
        Integer id = sala.getId();
        String nume = sala.getNume();
        return new SalaDTO(id, nume);
    }


//    ### SESSION ###
    public static Session getFromDTO(SessionDTO sessionDTO) {
        Integer id = sessionDTO.getId();
        String nume = sessionDTO.getNume();
        String data = sessionDTO.getData();
        String ora_inc = sessionDTO.getOra_inc();
        String ora_sf = sessionDTO.getOra_sf();
        SalaDTO salaDTO = sessionDTO.getSala();
        Sala sala = getFromDTO(salaDTO);
        Integer pret = sessionDTO.getPret();
        return new Session(id, nume, data, ora_inc, ora_sf, sala, pret);
    }

    public static SessionDTO getDTO(Session session) {
        Integer id = session.getId();
        String nume=session.getNume();
        String data = session.getData();
        String ora_inc = session.getOra_inc();
        String ora_sf = session.getOra_sf();
        Sala sala = session.getSala();
        SalaDTO salaDTO = getDTO(sala);
        Integer pret = session.getPret();
        return new SessionDTO(id, nume,data, ora_inc, ora_sf, salaDTO, pret);
    }

    public static SessionDTO[] getDTO(Session[] sessions) {
        SessionDTO[] sessionDTOS = new SessionDTO[sessions.length];
        for (int i = 0; i < sessions.length; i++)
            sessionDTOS[i] = getDTO(sessions[i]);
        return sessionDTOS;
    }

    public static Session[] getFromDTO(SessionDTO[] sessionDTOS) {
        Session[] sessions = new Session[sessionDTOS.length];
        for (int i = 0; i < sessionDTOS.length; i++) {
            sessions[i] = getFromDTO(sessionDTOS[i]);
        }
        return sessions;
    }


//    ### ROLE ###
    public static Role getFromDTO(RoleDTO roleDTO) {
        Integer id = roleDTO.getId();
        String denumire = roleDTO.getDenumire();
        return new Role(id, denumire);
    }

    public static RoleDTO getDTO(Role role) {
        Integer id = role.getId();
        String denumire = role.getDenumire();
        return new RoleDTO(id, denumire);
    }

    public static RoleDTO[] getDTO(Role[] roles) {
        RoleDTO[] roleDTOS = new RoleDTO[roles.length];
        for (int i = 0; i < roles.length; i++)
            roleDTOS[i] = getDTO(roles[i]);
        return roleDTOS;
    }

    public static Role[] getFromDTO(RoleDTO[] roleDTOS) {
        Role[] roles = new Role[roleDTOS.length];
        for (int i = 0; i < roleDTOS.length; i++) {
            roles[i] = getFromDTO(roleDTOS[i]);
        }
        return roles;
    }


//    ### USERROLE ###
    public static UserRole getFromDTO(UserRoleDTO userRoleDTO) {
        UserDTO userDTO = userRoleDTO.getUser();
        User user = getFromDTO(userDTO);
        RoleDTO roleDTO = userRoleDTO.getRole();
        Role role = getFromDTO(roleDTO);
        return new UserRole(user, role);
    }

    public static UserRoleDTO getDTO(UserRole userRole) {
        User user = userRole.getUser();
        UserDTO userDTO = getDTO(user);
        Role role = userRole.getRole();
        RoleDTO roleDTO = getDTO(role);
        return new UserRoleDTO(userDTO, roleDTO);
    }

}