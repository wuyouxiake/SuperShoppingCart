package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CARTCOMMENT database table.
 * 
 */
@Entity
@NamedQuery(name="Cartcomment.findAll", query="SELECT c FROM Cartcomment c")
public class Cartcomment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long productid;

	private String review;

	private long star;

	private long userid;

	public Cartcomment() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductid() {
		return this.productid;
	}

	public void setProductid(long productid) {
		this.productid = productid;
	}

	public String getReview() {
		return this.review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public long getStar() {
		return this.star;
	}

	public void setStar(long star) {
		this.star = star;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

}