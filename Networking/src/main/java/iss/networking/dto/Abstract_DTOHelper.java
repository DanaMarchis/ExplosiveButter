package iss.networking.dto;

import iss.model.Session;
import iss.model.User;

import java.io.Serializable;

/**
 * Created by Bitten Apple on 08-Jun-17.
 */
public class Abstract_DTOHelper implements Serializable {

    private String name;
    private String topics;
    private String keywords;
    private String filepath;
    private String detalii_autor;
    private Session session;
    private User user;
    private File_DTO file_dto;

    public Abstract_DTOHelper() {
    }

    public Abstract_DTOHelper(String name, String topics, String keywords, String filepath, String detalii_autor, Session session, User user, File_DTO file_dto) {
        this.name = name;
        this.topics = topics;
        this.keywords = keywords;
        this.filepath = filepath;
        this.detalii_autor = detalii_autor;
        this.session = session;
        this.user = user;
        this.file_dto = file_dto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getDetalii_autor() {
        return detalii_autor;
    }

    public void setDetalii_autor(String detalii_autor) {
        this.detalii_autor = detalii_autor;
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
