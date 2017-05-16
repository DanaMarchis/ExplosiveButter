package iss.model;

/**
 * Created by Bitten Apple on 15-May-17.
 */
public class User {

    //    ### ATTRIBUTES ###
    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String email;


    //    ### CONSTRUCTORS ###
    public User() {
        username = "";
        password = "";
        nume = "";
        prenume = "";
        email = "";
    }

    public User(String username, String password, String nume, String prenume, String email) {
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }


    //    ### GETTERS ###
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getEmail() {
        return email;
    }


    //    ### SETTERS ###
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}