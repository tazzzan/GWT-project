package syi.n.client.services;

import java.util.List;

import syi.n.client.model.Admin;
import syi.n.client.model.Bacc;
import syi.n.client.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	void createNewUser(final String nick, final String password,
			AsyncCallback<Void> callback);

	void createNewAdmin(final String nick, final String password,
			AsyncCallback<Void> callback);

	void getUser(final String nickName, AsyncCallback<User> callback);

	void getAdmin(final String nickName, AsyncCallback<Admin> callback);

	void deleteUser(final String nickName, AsyncCallback<Void> callback);

	void createBacc(final String nickName, final String nameBacc,
			AsyncCallback<User> callback);

	void getBacc(final String nickName, final String nameBacc,
			AsyncCallback<Bacc> callback);

	void deleteBacc(final String nickName, final String nameBacc,
			AsyncCallback<Void> callback);

	void createChange(final String nickName, final String nameBacc,
			final Double change, final String descriptionChange,
			AsyncCallback<User> callback);

	void deleteChange(final String nickName, final List<Integer> changeId,
			AsyncCallback<Void> callback);

	void changeUserBalance(final String nickName, AsyncCallback<Void> callback);

	void setMonthlyBalance(final String nickName,
			final double typedMonthlyBalance, AsyncCallback<Void> callback);

	void setAvailableBalance(final String nickName, AsyncCallback<Void> callback);

	void writeXML(AsyncCallback<Void> callback);

	void deleteDB(AsyncCallback<Void> callback);

}
