package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the USER_PROD database table.
 * 
 */
@Entity
@Table(name="USER_PROD")
@NamedQuery(name="UserProd.findAll", query="SELECT u FROM UserProd u")
public class UserProd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name="PROD_ID")
	private long prodId;

	private long quantity;

	@Column(name="USER_ID")
	private long userId;

	public UserProd() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProdId() {
		return this.prodId;
	}

	public void setProdId(long productid) {
		this.prodId = productid;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(long qty) {
		this.quantity = qty;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userid) {
		this.userId = userid;
	}

}