package syi.n.client.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.PrivateOwned;

/**
 * This class stores a balance-account with following data: - nameBacc - id -
 * balance - arrayChanges - change This class is persistable
 */

@Entity
public class Bacc implements Serializable {

	@Transient
	private static final long serialVersionUID = 2189364363971210722L;
	@Column(name = "NAMEBACC")
	private String nameBacc;
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;
	@Column(name = "BALANCE")
	private double balance;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER")
	private User user;

	@OneToMany(mappedBy = "bacc", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "CHANGES")
	@PrivateOwned
	private Set<Change> changesSet = new HashSet<Change>();

	public Bacc() {
	}

	/**
	 * Methods
	 */

	public void setNameBacc(String nameBacc) {
		this.nameBacc = nameBacc;
	}

	public String getNameBacc() {
		return nameBacc;
	}

	// Setter and Getter for "balance"
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void changeBalance() {
		setBalance(0);
		for (Change elem : getChangesSet()) {
			this.balance += elem.getChange();
		}
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<Change> getChangesSet() {
		return changesSet;
	}

	public void setChangesSet(Set<Change> changesSet) {
		this.changesSet = changesSet;
	}

}
