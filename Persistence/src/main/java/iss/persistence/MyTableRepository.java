package iss.persistence;

import iss.model.Conference;
import iss.model.Role;
import iss.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flori on 6/7/2017.
 */
public class MyTableRepository {
    private SessionFactory sessionFactory;

    public MyTableRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Role> getRolesOfUserInConferenceAndSession(User user, Conference conference, iss.model.Session ourSession){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<MyTable> relatii=new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            relatii= session.createQuery("FROM MyTable as mt where mt.user= :user AND mt.conference= :conference AND mt.session= :session", MyTable.class)
                    .setParameter("user", user)
                    .setParameter("conference",conference)
                    .setParameter("session",ourSession)
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
            if(!roles.contains(mt.getRol()))
                roles.add(mt.getRol());
        }
        return roles;
    }

    public void attend(User user, Role rol, Conference conference, iss.model.Session ourSession) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        boolean okStatus=true;
        MyTable myTable=new MyTable(user,rol,conference,ourSession);
        try{
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        }catch(RuntimeException ex){
            ex.printStackTrace();
            if (tx!=null) {
                tx.rollback();
                okStatus=false;
            }
        }finally{
            session.close();
        }
        if(!okStatus)
            throw new Exception("Problem encountered while attending session: "+ourSession.getNume()+", in conference: "+
            conference.getNume()+", for user: "+user.getUsername()+", as: "+rol.getDenumire());
    }
}
