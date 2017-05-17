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

    //    ### USER doar cu Username si Password ###
    public static UserDTO_up getDTO_up(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        return new UserDTO_up(username, password);
    }

    public static User getFromDTO_up(UserDTO_up userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        return new User(username, password);
    }
}