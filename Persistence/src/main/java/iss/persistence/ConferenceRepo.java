package iss.persistence;

import iss.model.Conference;
import iss.model.Role;
import iss.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.DateFormat;
import java.util.Date;
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
        List<MyTable> relatii=new ArrayList<>();
        List<iss.model.Session> sessions = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            relatii= session.createQuery("FROM MyTable as mt where mt.conference= :conf ", MyTable.class)
                    .setParameter("conf", conf)
                    .list();
            tx.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
        for(MyTable mt :relatii){
            if(!sessions.contains(mt.getSession()))
                sessions.add(mt.getSession());
        }
        return sessions;
    }
    public List<Conference> getAll(User user, Role role){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<MyTable> relatii=new ArrayList<>();
        List<Conference> conferences = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            relatii= session.createQuery("FROM MyTable as mt where mt.user= :user AND mt.rol= :role", MyTable.class)
                    .setParameter("user", user)
                    .setParameter("role",role)
                    .list();
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
        for(MyTable mt :relatii){
            if(!conferences.contains(mt.getConference()))
            conferences.add(mt.getConference());
        }
        return conferences;
    }

    public List<Conference> getAllConferencesDeadline() throws ParseException {
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
        List<Conference> rezultat=new ArrayList<>();
        DateFormat dataFormat=new SimpleDateFormat("dd-MM-yyyy");
        Date dataCurenta=new Date();
        for(Conference conf:conferences){
            Date dataConferintaAbstract=dataFormat.parse(conf.getDeadline_abs());
            if(dataConferintaAbstract.after(dataCurenta))
                rezultat.add(conf);
            else{
                Date dataConferintaFull=dataFormat.parse(conf.getDeadline_full());
                if(dataConferintaFull.after(dataCurenta))
                    rezultat.add(conf);
            }
        }
        return rezultat;
    }
}
