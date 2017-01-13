package syi.n.server.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import syi.n.client.model.Admin;
import syi.n.client.model.User;

/**
 * This class "GettAllUsers" allows the possibility to get all Users and Admins
 * quickly
 * 
 * @author ilja
 * 
 */
public class GetAllUsers {
	public GetAllUsers() {
	}

	/**
	 * This method returns an ArrayList with all Users in the DataBase
	 * 
	 * @return List<User>
	 */
	public List<User> userNames() {
		List<User> result = null;
		try {
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("Syi");
			EntityManager manager = emf.createEntityManager();
			EntityTransaction tx = manager.getTransaction();
			tx.begin(); // begin transaction

			String queryString = "SELECT c FROM User c";
			TypedQuery<User> query = manager.createQuery(queryString,
					User.class);
			result = query.getResultList();

			tx.commit(); // finish transaction
			manager.close();
			emf.close();

		} catch (Exception ex) {
		}
		return result;
	}

	/**
	 * This method returns an ArrayList with all Admins in the DataBase
	 * 
	 * @return List<Admin>
	 */
	public List<Admin> adminNames() {
		List<Admin> result = null;
		try {
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("Syi");
			EntityManager manager = emf.createEntityManager();
			EntityTransaction tx = manager.getTransaction();
			tx.begin(); // begin transaction

			String queryString = "SELECT c FROM Admin c";
			TypedQuery<Admin> query = manager.createQuery(queryString,
					Admin.class);
			result = query.getResultList();

			tx.commit(); // finish transaction
			manager.close();
			emf.close();

		} catch (Exception ex) {
		}
		return result;
	}
}