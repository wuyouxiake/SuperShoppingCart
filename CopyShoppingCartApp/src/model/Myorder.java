package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


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

	@Temporal(TemporalType.DATE)
	private Date orderdate;

	private long orderid;

	private long productid;

	private long quantity;

	private long userid;

	public Myorder() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public long getOrderid() {
		return this.orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public long getProductid() {
		return this.productid;
	}

	public void setProductid(long productid) {
		this.productid = productid;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(long qty) {
		this.quantity = qty;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

}