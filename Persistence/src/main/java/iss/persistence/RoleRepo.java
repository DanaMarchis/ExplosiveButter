package iss.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import iss.model.Role;
import iss.model.User;

/**
 * Created by flori on 6/2/2017.
 */
public class RoleRepo {
	private SessionFactory sessionFactory;

	public RoleRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		populateTable();
	}

	public List<Role> getRoles(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<MyTable> relatii = new ArrayList<>();
		List<Role> roles = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			relatii = session.createQuery("FROM MyTable as mt where mt.user= :user ", MyTable.class)
					.setParameter("user", user).list();
			tx.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		for (MyTable mt : relatii) {
			if (!roles.contains(mt.getRol())) {
				roles.add(mt.getRol());
			}
		}
		return roles;
	}

	private void save(Role role) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean okStatus = true;
		try {
			tx = session.beginTransaction();
			session.save(role);
			tx.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
				okStatus = false;
			}
		} finally {
			session.close();
		}
		if (!okStatus) {
			throw new Exception("Problem encountered while saving user.");
		}
	}

	private void populateTable() {
		try {
			save(new Role(1, "PC Member"));
			save(new Role(2, "author"));
			save(new Role(3, "listener"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
