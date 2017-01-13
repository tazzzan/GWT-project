package syi.n.client.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class Account implements Serializable {
	@Transient
	private static final long serialVersionUID = -7733033534348837939L;

	@Column(updatable = false, name = "USER_ID")
	@GeneratedValue
	protected int id;
	@Column(name = "NAME")
	protected String nick;
	@Column(name = "PASSWORD")
	protected String password;
	@Column(name = "PERMISSION")
	protected boolean permission = false;

	Account() {
	}

	/**
	 * Methods
	 */
	
	public void setNick(String nick){
		this.nick = nick;
	}
	
	public String getNick(){
		return nick;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	private String getPassword() {
		return password;
	}

	// Setter and Getter for "id"
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public boolean getPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public void checkPassword(String password) {

		if (password.equals(getPassword())) {
			this.permission = true;
		} else {
			this.permission = false;
		}

	}
}
