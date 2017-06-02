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
 * Created by flori on 6/2/2017.
 */
public class RoleRepo {
    private SessionFactory sessionFactory;

    public RoleRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<Role> getRoles(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<MyTable> relatii=new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            relatii= session.createQuery("FROM MyTable as mt where mt.user= :user ", MyTable.class)
                    .setParameter("user", user)
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
}
