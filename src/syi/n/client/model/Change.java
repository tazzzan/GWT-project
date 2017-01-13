package syi.n.client.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import static javax.persistence.GenerationType.AUTO;

/**
 * This class represents the transactions on the Balance-Accounts
 * This class is persistable
 * @author ilja
 *
 */
@Table(name = "CHANGES")
@Entity
public class Change implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDCHANGE")
	@GeneratedValue(strategy = AUTO)
	private int id;
	@Column(name = "GHANGES")
	private double change;
	@Column(name = "NAMECHANGE")
	private String nameChange;
	@Column(name = "DESCRIPTIONCHANGE")
	private String descriptionChange;
	@Column(name = "INCOME")
	private double income;
	@Transient
	private static String nameIncome = "Income";
	@Column(name = "PAYOFF")
	private double payoff;
	@Transient
	private static String namePayoff = "Payoff";

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "BACC")
	private Bacc bacc;

	public Change() {

	}

/** 
 * Methods
 */

	public void setChange(double tchange, String descriptionChange) {
		if (tchange <= 0) {
			setNameChange(namePayoff);
			setPayoff(tchange);
			setChange(tchange);
			setDescriptionChange(descriptionChange);
		} else {
			setNameChange(nameIncome);
			setIncome(tchange);
			setChange(tchange);
			setDescriptionChange(descriptionChange);
		}
	}

	public String getDescriptionChange() {
		return descriptionChange;
	}

	public void setDescriptionChange(String descriptionChange) {
		this.descriptionChange = descriptionChange;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getChange() {
		return change;
	}

	public String changeChangeTypeToString(double change) {
		return String.valueOf(change);
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getIncome() {
		return income;
	}

	public void setPayoff(double payoff) {
		this.payoff = payoff;
	}

	public double getPayoff() {
		return payoff;
	}

	public void setNameChange(String nameChange) {
		this.nameChange = nameChange;
	}

	public String getNameChange() {
		return nameChange;
	}

	public int getId() {
		return id;
	}

	public Bacc getBacc() {
		return bacc;
	}

	public void setBacc(Bacc bacc) {
		this.bacc = bacc;
	}

	public static String getNameIncome() {
		return nameIncome;
	}

	public static void setNameIncome(String nameIncome) {
		Change.nameIncome = nameIncome;
	}

	public static String getNamePayoff() {
		return namePayoff;
	}

	public static void setNamePayoff(String namePayoff) {
		Change.namePayoff = namePayoff;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}