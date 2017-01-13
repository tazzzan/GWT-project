package syi.n.server.crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import syi.n.client.model.Admin;
import syi.n.client.model.Bacc;
import syi.n.client.model.Change;
import syi.n.client.model.Settings;
import syi.n.client.model.User;

/**
 * This class "ClearDB" clears the complete DataBase At the end of deleting
 * there is no Admin-Account anymore!!! At the Admin-Interface the Logout-Button
 * will be disabled: The Admin who deleted the DataBase will have to create a
 * new Admin-Account to have the possibility to logout
 * 
 * @author ilja
 * 
 */
public class ClearDB {
	public ClearDB() {

	}

	public void clearDB() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("Syi");
		EntityManager manager = emf.createEntityManager();
		EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction
		/**
		 * Changes will be deleted it is necessary to do it first because it has
		 * a relationship to Balance-Accounts and accordingly to User
		 */
		String queryStringChange = "DELETE FROM Change c";
		TypedQuery<Change> queryChange = manager.createQuery(queryStringChange,
				Change.class);
		queryChange.executeUpdate();
		/**
		 * Balance-Accounts will be deleted
		 */
		String queryStringBacc = "DELETE FROM Bacc d";
		TypedQuery<Bacc> queryBacc = manager.createQuery(queryStringBacc,
				Bacc.class);
		queryBacc.executeUpdate();
		/**
		 * Settings will be deleted
		 */
		String queryStringSettings = "DELETE FROM Settings e";
		TypedQuery<Settings> querySettings = manager.createQuery(
				queryStringSettings, Settings.class);
		querySettings.executeUpdate();
		/**
		 * Users will be deleted
		 */
		String queryStringUser = "DELETE FROM User f";
		TypedQuery<User> queryUser = manager.createQuery(queryStringUser,
				User.class);
		queryUser.executeUpdate();
		/**
		 * Admins will be deleted
		 */
		String queryStringAdmin = "DELETE FROM Admin g";
		TypedQuery<Admin> queryAdmin = manager.createQuery(queryStringAdmin,
				Admin.class);
		queryAdmin.executeUpdate();

		tx.commit(); // finish transaction
		manager.close();
		emf.close();
	}
}