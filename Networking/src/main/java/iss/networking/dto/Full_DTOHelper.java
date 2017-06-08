package iss.networking.dto;

import iss.model.Session;
import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 08-Jun-17.
 */
public class Full_DTOHelper implements Serializable {

    private String filepath;
    private Session session;
    private User user;
    private File_DTO file_dto;

    public Full_DTOHelper() {
    }

    public Full_DTOHelper(String filepath, Session session, User user, File_DTO file_dto) {
        this.filepath = filepath;
        this.session = session;
        this.user = user;
        this.file_dto = file_dto;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File_DTO getFile_dto() {
        return file_dto;
    }

    public void setFile_dto(File_DTO file_dto) {
        this.file_dto = file_dto;
    }
}
