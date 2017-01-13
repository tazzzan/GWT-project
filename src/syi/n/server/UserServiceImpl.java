package syi.n.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import syi.n.client.model.Admin;
import syi.n.client.model.Bacc;
import syi.n.client.model.Change;
import syi.n.client.model.Settings;
import syi.n.client.model.User;
import syi.n.client.services.UserService;
import syi.n.server.crud.ClearDB;
import syi.n.server.xml.WriteXmlFile;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This class makes changes on the data base
 * 
 * @author ilja
 * 
 */

public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {
	private static final long serialVersionUID = 233333249875291373L;

	public UserServiceImpl() {

	}

	@Override
	public void createNewUser(final String nickName, final String password) {
		final User newUser = new User();
		newUser.setNick(nickName);
		newUser.setPassword(password);
		newUser.setBalance(0);

		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		manager.persist(newUser); // persist the new account
		tx.commit(); // end transaction

		manager.close();
		factory.close();
	}

	public void createNewAdmin(final String nickName, final String password) {

		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin();

		System.out.println("blabla");
		final Admin admin = new Admin();
		admin.setNick(nickName);
		admin.setPassword(password);

		manager.persist(admin); // persist the new account

		tx.commit(); // end transaction

		// finish the request
		manager.close();
		factory.close();
	}

	public User getUser(final String nickName) {

		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		// get user
		User user = new User();
		try {
			user = manager.find(User.class, nickName);

		} catch (java.lang.Exception e) {
			user=null;
		}
		tx.commit(); // end transaction

		// finish the request
		manager.close();
		factory.close();

		return user; // return the found user
	}

	public Admin getAdmin(final String nickName) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		// get admin
		Admin admin = new Admin();
		try {
			admin = manager.find(Admin.class, nickName);
		} 

		catch (java.lang.Exception e) {
			admin = null;
		}
		tx.commit(); // end transaction

		// finish the request
		manager.close();
		factory.close();

		return admin; // return the found admin
	}

	public void deleteUser(String nickName) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		User user = manager.find(User.class, nickName);

		// remove user
		try {
			manager.remove(user);
		} catch (java.lang.Exception e) {
		} // error on removing user

		tx.commit(); // end transaction

		// finish the request
		manager.close();
		factory.close();

	}

	public User createBacc(String nickName, String nameBacc) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		User neededUser = manager.find(User.class, nickName);

		// create bacc
		final Bacc newBacc = new Bacc();
		newBacc.setNameBacc(nameBacc);
		newBacc.setUser(neededUser);

		neededUser.getBaccsSet().add(newBacc);

		tx.commit(); // end transaction
		// finish the request
		manager.close();
		factory.close();

		return neededUser;
	}

	public Bacc getBacc(String nickName, String nameBacc) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin();
		User neededUser = new User();
		neededUser = manager.find(User.class, nickName);
		int baccId = 0;
		for (Bacc elem : neededUser.getBaccsSet()) {
			if (elem.getNameBacc().equals(nameBacc)) {
				baccId = elem.getId();
				break;
			}
		}

		Bacc returnedBacc = manager.find(Bacc.class, baccId);

		tx.commit(); // end transaction

		// finish the request
		manager.close();
		factory.close();

		return returnedBacc;

	}

	public User createChange(String nickName, String nameBacc, Double change,
			String descriptionChange) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		// Getting user to find "bacc"
		User neededUser = manager.find(User.class, nickName);
		// Looking for the right "bacc" in user's baccsset
		// - First getting "id"
		int baccId = 0;
		for (Bacc elem : neededUser.getBaccsSet()) {
			if (elem.getNameBacc().equals(nameBacc)) {
				baccId = elem.getId();
				break;
			}
		}
		// -Now looking for the "bacc" with the "id"
		Bacc returnedBacc = manager.find(Bacc.class, baccId);

		// Create "change"
		Change chng = new Change();

		chng.setChange(change, descriptionChange);
		chng.setBacc(returnedBacc);
		returnedBacc.getChangesSet().add(chng);

		returnedBacc.changeBalance();

		tx.commit(); // end transaction
		// finish the request
		manager.close();
		factory.close();

		changeUserBalance(nickName);
		setAvailableBalance(nickName);
		neededUser = getUser(nickName);
		return neededUser;

	}

	public void deleteChange(String nickName, List<Integer> changeId) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		List<Change> changesToDelete = new ArrayList<Change>();
		for (Integer elem : changeId) {
			changesToDelete.add(manager.find(Change.class, elem));
		}

		List<Bacc> neededBaccs = new ArrayList<Bacc>();
		for (Change elem : changesToDelete) {
			if (neededBaccs.contains(elem.getBacc()) == false) {
				neededBaccs.add(manager
						.find(Bacc.class, elem.getBacc().getId()));
			}
		}

		for (Bacc elem : neededBaccs) {
			for (Change item : changesToDelete) {
				elem.getChangesSet().remove(item);
				elem.changeBalance();
			}
		}

		tx.commit(); // end transaction

		changeUserBalance(nickName);

		// finish the request
		manager.close();
		factory.close();

		setAvailableBalance(nickName);
	}

	public void deleteBacc(String nickName, String nameBacc) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		User neededUser = manager.find(User.class, nickName);
		int baccId = 0;
		for (Bacc elem : neededUser.getBaccsSet()) {
			if (elem.getNameBacc().equals(nameBacc)) {
				baccId = elem.getId();
				break;
			}
		}

		Bacc bacc = manager.find(Bacc.class, baccId);
		// remove bacc
		try {
			neededUser.getBaccsSet().remove(bacc);
		} catch (java.lang.Exception e) {
		} // error on removing change

		neededUser.changeBalance();

		tx.commit(); // end transaction
		// finish the request
		manager.close();
		factory.close();

		setAvailableBalance(nickName);
	}

	public void setMonthlyBalance(String nickName, double typedMonthlyBalance) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		User neededUser = manager.find(User.class, nickName);

		if (neededUser.getSettings() == null) {
			neededUser.setSettings(new Settings());
			neededUser.getSettings().setMonthlyBalance(typedMonthlyBalance);
			neededUser.getSettings().setUser(neededUser);

		} else {
			neededUser.getSettings().setMonthlyBalance(typedMonthlyBalance);
		}

		tx.commit(); // end transaction
		setAvailableBalance(neededUser.getNick());
		// finish the request
		manager.close();
		factory.close();

	}

	/**
	 * you find the created file in war folder
	 * name: ExampleDataBase.xml
	 */
	public void writeXML() {
		// now try to create xml
		WriteXmlFile wxf = new WriteXmlFile();
		wxf.writeXmlFile();
	}

	public void setAvailableBalance(String nickName) {
		// start the request on database
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		User user = manager.find(User.class, nickName);
		try {
			double payoffs = 0;
			for (Bacc elem : user.getBaccsSet()) {
				for (Change item : elem.getChangesSet()) {
					if (item.getNameChange().equals("Payoff")) {
						payoffs -= item.getChange();
					}
				}
			}
			user.setAvailableBalance(user.getSettings().getMonthlyBalance()
					- payoffs);

		} catch (java.lang.Exception e) {
		}
		tx.commit(); // end transaction
		// finish the request
		manager.close();
		factory.close();
	}

	public void deleteDB() {
		ClearDB ddb = new ClearDB();
		ddb.clearDB();
	}

	public void changeUserBalance(String nickName) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		User user = manager.find(User.class, nickName);
		user.changeBalance();
		System.out.println("users changed balance: " + user.getBalance());
		tx.commit(); // end transaction
		// finish the request
		manager.close();
		factory.close();
	}

	public void changeBaccBalance(String nickName, String baccName) {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Syi");
		final EntityManager manager = factory.createEntityManager();

		final EntityTransaction tx = manager.getTransaction();
		tx.begin(); // begin transaction

		User user = manager.find(User.class, nickName);
		for (Bacc elem : user.getBaccsSet()) {
			if (elem.getNameBacc().equals(baccName)) {
				elem.changeBalance();
			}
		}
		user.changeBalance();

		tx.commit(); // end transaction
		// finish the request
		manager.close();
		factory.close();
	}

}
