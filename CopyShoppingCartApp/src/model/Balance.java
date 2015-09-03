package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BALANCE database table.
 * 
 */
@Entity
@NamedQuery(name="Balance.findAll", query="SELECT b FROM Balance b")
public class Balance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private double balance;

	private long userid;

	public Balance() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double credit) {
		this.balance = credit;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

}