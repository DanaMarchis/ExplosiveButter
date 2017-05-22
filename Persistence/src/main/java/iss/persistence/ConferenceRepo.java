package iss.persistence;

import iss.model.Conference;
import iss.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flori on 5/21/2017.
 */
public class ConferenceRepo {
    private SessionFactory sessionFactory;

    public ConferenceRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Conference> getAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Conference> conferences = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            conferences = session.createQuery("FROM Conference", Conference.class).list();
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
        return conferences;
    }

    public List<iss.model.Session> getSessions(Conference conf) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<iss.model.Session> sessions = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            sessions = session.createQuery("SELECT DISTINCT session FROM myTable mt INNER JOIN Session session WHERE mt.id_conferinta=:conferinta ", iss.model.Session.class)
                    .setParameter("conferinta", conf.getId())
                    .list();
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
        return sessions;
    }

}
