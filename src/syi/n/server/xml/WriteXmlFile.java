package syi.n.server.xml;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import syi.n.client.model.Admin;
import syi.n.client.model.User;
import syi.n.server.crud.GetAllUsers;

public class WriteXmlFile {
	public WriteXmlFile() {

	}

	public void writeXmlFile() {
		GetAllUsers gau = new GetAllUsers();
		List<User> users = gau.userNames();
		List<Admin> admins = gau.adminNames();

		try {
			final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("ExampleDataBase.xml")));
			for (User elem : users) {
				encoder.writeObject(elem);
				System.out.println(elem.getNick());
			}

			for (Admin elem : admins) {
				encoder.writeObject(elem);
				System.out.println(elem.getNick());
			}
			encoder.close();
		} catch (final FileNotFoundException e) {
			System.out.println("oha");
		}
	}
}
