package syi.n.client.services;

import java.util.List;

import syi.n.client.model.Admin;
import syi.n.client.model.Bacc;
import syi.n.client.model.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("useroperations")
public interface UserService extends RemoteService {

	void createNewUser(final String nick, final String password);

	void createNewAdmin(final String nickName, final String password);

	User getUser(final String nickName);

	Admin getAdmin(final String nickName);

	void deleteUser(final String nickName);

	User createBacc(final String nickName, final String nameBacc);

	Bacc getBacc(final String nickName, final String nameBacc);

	void deleteBacc(String nickName, String nameBacc);

	User createChange(final String nickName, final String nameBacc,
			final Double change, final String descriptionChange);

	void deleteChange(final String nickName, final List<Integer> changeId);

	void setMonthlyBalance(final String nickName,
			final double typedMonthlyBalance);

	void changeUserBalance(final String nickName);

	void setAvailableBalance(final String nickName);

	void writeXML();

	void deleteDB();

}
