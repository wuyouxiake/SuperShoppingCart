package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MYORDER database table.
 * 
 */
@Entity
@NamedQuery(name="Myorder.findAll", query="SELECT m FROM Myorder m")
public class Myorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name="PROD_ID")
	private BigDecimal prodId;

	private BigDecimal quantity;

	@Column(name="USER_ID")
	private BigDecimal userId;

	public Myorder() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getProdId() {
		return this.prodId;
	}

	public void setProdId(BigDecimal prodId) {
		this.prodId = prodId;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

}