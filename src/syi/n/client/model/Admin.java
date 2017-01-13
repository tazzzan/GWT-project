package syi.n.client.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 * This class stores an admin-account. This class is persistable.
 */
@Entity
public class Admin extends Account implements Serializable {

	@Transient
	private static final long serialVersionUID = 3840604272737346965L;

	@Id
	@Column(name = "ADMINNICK")
	private String adminNick;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "ADMIN_USER")
	private Set<User> userSet = new HashSet<User>();

	public Admin() {

	}

	public String getNick() {
		return adminNick;
	}

	public void setNick(String adminNick) {
		this.adminNick = adminNick;
	}

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addUser(User user) {
		userSet.add(user);
	}
}
