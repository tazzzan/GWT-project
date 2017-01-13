package syi.n.client.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.PrivateOwned;

/**
 * This class stores a user-account. This class is persistable.
 */

@Table(name = "USER")
@Entity
public class User extends Account implements Serializable {
	@Transient
	private static final long serialVersionUID = 6861708710548289711L;

	@Id
	@Column(name = "USERNICK")
	private String userNick;

	@Column(name = "BALANCE")
	private double balance = 0;

	@Column(name = "AVAILABLEBALANCE")
	private double availableBalance;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "BACC")
	@PrivateOwned
	private Set<Bacc> baccsSet = new HashSet<Bacc>();

	@OneToOne(mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "SETTINGS")
	@PrivateOwned
	private Settings settings;

	public User() {
	}

	/**
	 * Methods
	 */

	public String getNick() {
		return userNick;
	}

	public void setNick(String userNick) {
		this.userNick = userNick;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Set<Bacc> getBaccsSet() {
		return baccsSet;
	}

	public void setBaccsSet(Set<Bacc> baccsSet) {
		this.baccsSet = baccsSet;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void changeBalance() {
		setBalance(0);
		for (Bacc elem : getBaccsSet()) {
			this.balance += elem.getBalance();
		}
	}
}
