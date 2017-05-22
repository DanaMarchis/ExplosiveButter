package iss.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flori on 5/21/2017.
 */
public class SessionRepository {
    private SessionFactory sessionFactory;

    public SessionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<iss.model.Session> getAll(){
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        List<iss.model.Session> sessions=new ArrayList<>();
        try{
            tx = session.beginTransaction();
            sessions=session.createQuery("FROM Session",iss.model.Session.class).list();
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
        return sessions;
    }
}
