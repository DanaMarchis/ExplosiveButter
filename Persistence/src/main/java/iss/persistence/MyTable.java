package iss.persistence;

import java.io.Serializable;
import iss.model.*;

/**
 * Created by flori on 5/21/2017.
 */
public class MyTable implements Serializable{
        private String username;
        private int id_role;
        private int id_conference;
        private int id_session;

        public MyTable(String username, int id_role, int id_conference, int id_session) {
            this.username= username;
            this.id_role = id_role;
            this.id_conference = id_conference;
            this.id_session = id_session;
        }

        public MyTable() {
        }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId_role() {
            return id_role;
        }

        public void setId_role(int id_role) {
            this.id_role = id_role;
        }

        public int getId_conference() {
            return id_conference;
        }

        public void setId_conference(int id_conference) {
            this.id_conference = id_conference;
        }

        public int getId_session() {
            return id_session;
        }

        public void setId_session(int id_session) {
            this.id_session = id_session;
        }

}
