package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PAYMENT database table.
 * 
 */
@Entity
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String billingaddress;

	private long cardnumber;

	private String shippingaddress;

	public Payment() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBillingaddress() {
		return this.billingaddress;
	}

	public void setBillingaddress(String billingaddress) {
		this.billingaddress = billingaddress;
	}

	public long getCardnumber() {
		return this.cardnumber;
	}

	public void setCardnumber(long cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getShippingaddress() {
		return this.shippingaddress;
	}

	public void setShippingaddress(String shippingaddress) {
		this.shippingaddress = shippingaddress;
	}

}