package iss.persistence;

import iss.model.Abstract_Details;
import iss.model.Full_Details;
import iss.model.Paper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flori on 6/7/2017.
 */
public class PaperRepository {
    private SessionFactory sessionFactory;

    public PaperRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Paper getPapersWithAbstract(Abstract_Details abstract_details){
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        Paper paper=null;
        try{
            tx = session.beginTransaction();
            paper=session.createQuery("FROM Papers WHERE abstract_details= :absdet",Paper.class)
                    .setParameter("absdet",abstract_details)
                    .setMaxResults(1).uniqueResult();
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
        return paper;
    }

    public Full_Details getFullDetails(Abstract_Details abstract_details) throws Exception {
        Session session=sessionFactory.openSession();
        Transaction tx=null;
        Full_Details full_details=null;
        Paper paper=null;
        try{
            tx = session.beginTransaction();
            paper=session.createQuery("FROM Papers WHERE abstract_details= :absdet",Paper.class)
                    .setParameter("absdet",abstract_details)
                    .setMaxResults(1).uniqueResult();
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
        if(paper==null)
            throw new Exception("Paper not found");
        return paper.getFull_details();
    }
}
