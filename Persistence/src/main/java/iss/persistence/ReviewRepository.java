package iss.persistence;

import iss.model.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by flori on 6/8/2017.
 */
public class ReviewRepository {
    private SessionFactory sessionFactory;

    public ReviewRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void save(Review review) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        boolean okStatus=true;
        try{
            tx = session.beginTransaction();
            session.save(review);
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
            throw new Exception("Problem encountered while reviewing paper.");
    }
}
