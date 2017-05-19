package iss.networking.dto;

import iss.model.User;

/**
 * Created by Bitten Apple on 15-May-17.
 */
public class DTOUtils {

    //    ### USER ###
    public static User getFromDTO(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String nume = userDTO.getNume();
        String prenume = userDTO.getPrenume();
        String email = userDTO.getEmail();
        return new User(username, password, nume, prenume, email);
    }

    public static UserDTO getDTO(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String nume = user.getNume();
        String prenume = user.getPrenume();
        String email = user.getEmail();
        return new UserDTO(username, password, nume, prenume, email);
    }


//    ### CONFERENCE ###
    public static Conference getFromDTO(ConferenceDTO conferenceDTO) {
        Integer id = conferenceDTO.getId();
        String nume = conferenceDTO.getNume();
        String deadline_abs = conferenceDTO.getDeadlineAbs();
        String deadline_full = conferenceDTO.getDeadlineFull();
        String data_inc = conferenceDTO.getDataInc();
        String data_sf = conferenceDTO.getDataSf();
        return new Conference(nume, deadline_abs, deadline_full, data_inc, data_sf);
    }

    public static ConferenceDTO getDTO(Conference conference) {
        Integer id = conference.getId();
        String nume = conference.getNume();
        String deadline_abs = conference.getDeadlineAbs();
        String deadline_full = conference.getDeadlineFull();
        String data_inc = conference.getDataInc();
        String data_sf = conference.getDataSf();
        return new ConferenceDTO(nume, deadline_abs, deadline_full, data_inc, data_sf);
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


//    ### SESSION ###
    public static Session getFromDTO(SessionDTO sessionDTO) {
        Integer id = sessionDTO.getId();
        String data = sessionDTO.getData();
        String ora_inc = sessionDTO.getOraInc();
        String ora_sf = sessionDTO.getOraSf();
        Sala sala = sessionDTO.getSala();
        Integer pret = sessionDTO.getPret();
        return new Session(id, data, ora_inc, ora_sf, sala, pret);
    }

    public static SessionDTO getDTO(Session session) {
        Integer id = session.getId();
        String data = session.getData();
        String ora_inc = session.getOraInc();
        String ora_sf = session.getOraSf();
        Sala sala = session.getSala();
        Integer pret = session.getPret();
        return new SessionDTO(id, data, ora_inc, ora_sf, sala, pret);
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
        User user = userRoleDTO.getUser();
        Role role = userRoleDTO.getRole();
        return new UserRole(user, role);
    }

    public static UserRoleDTO getDTO(UserRole userRole) {
        User user = userRole.getUser();
        Role role = userRole.getRole();
        return new UserRoleDTO(user, role);
    }

}