package iss.persistence;

import iss.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flori on 5/17/2017.
 */
public class UserRepository {

    private SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAll(){
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        List<User> users=new ArrayList<>();
        try{
            tx = session.beginTransaction();
            users=session.createQuery("FROM User",User.class).list();
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
        return users;
    }

    public void save(User user) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        boolean okStatus=true;
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
            throw new Exception("Problem encountered while saving user.");
    }
}
