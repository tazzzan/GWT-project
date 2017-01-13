package syi.n.client.model;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.PrivateOwned;

/**
 * This class represents the settings of users This class is persistable
 * 
 * @author ilja
 * 
 */
@Entity
public class Settings implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column(name = "MOUNTHLYBALANCE")
	private double monthlyBalance = 0;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER")
	@PrivateOwned
	@Id
	private User user;

	public Settings() {
		super();
	}

	/**
	 * Methods
	 */

	public double getMonthlyBalance() {
		return monthlyBalance;
	}

	public void setMonthlyBalance(double monthlyBalance) {
		this.monthlyBalance = monthlyBalance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
