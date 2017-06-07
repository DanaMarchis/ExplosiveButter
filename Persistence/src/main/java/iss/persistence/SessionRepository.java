package iss.persistence;

import iss.model.*;
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
    public List<iss.model.Session> getSesiuniConferintaUserRol(Conference conferinta, User user, Role rol){
        Session session= sessionFactory.openSession();
        Transaction tx=null;
        List<iss.model.Session> sessions=new ArrayList<>();
        List<MyTable> myTable=new ArrayList<>();
        try{
            tx = session.beginTransaction();
            myTable=session.createQuery("FROM MyTable WHERE user= :user AND conference= :conference AND rol= :rol",MyTable.class)
                    .setParameter("user",user)
                    .setParameter("conference",conferinta)
                    .setParameter("rol",rol)
                    .list();
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
        for(MyTable mt : myTable){
            if(!sessions.contains(mt.getSession()))
                sessions.add(mt.getSession());
        }
        return sessions;
    }

    public List<Abstract_Details> getNameAndTopic(iss.model.Session ourSession) {
        Session session =sessionFactory.openSession();
        Transaction tx=null;
        List<Paper> papers=new ArrayList<Paper>();
        List<Abstract_Details> abstract_details=new ArrayList<>();
        try{
            tx=session.beginTransaction();
            papers=session.createQuery("FROM Papers WHERE session= :session",Paper.class)
                    .setParameter("session",ourSession)
                    .list();
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
        for(Paper p:papers){
            abstract_details.add(p.getAbstract_details());
        }
        return abstract_details;
    }
}
