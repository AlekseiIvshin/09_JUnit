package store.dao;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import car.dao.modifiacation.Modification;

/**
 * Entity for table 'store'.
 * 
 * @author Aleksei_Ivshin
 *
 */
@Entity
@Table(name = "store")
public class Store {

	/**
	 * id of store entity.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_store")
	private int id;

	/**
	 * Store count.
	 */
	@Column(name = "count")
	private int count;

	/**
	 * Price for car.
	 */
	@Column(name = "price")
	private BigDecimal price;

	/**
	 * Can test drive car.
	 */
	@Column(name = "testdrive_avaible", length = 1, columnDefinition = "BIT")
	private boolean testDriveAvaible;

	/**
	 * Car.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_car_modification")
	private Modification modification;

	/**
	 * Default constructor.
	 */
	public Store() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isTestDriveAvaible() {
		return testDriveAvaible;
	}

	public void setTestDriveAvaible(boolean testDriveAvaible) {
		this.testDriveAvaible = testDriveAvaible;
	}

	public Modification getModification() {
		return modification;
	}

	public void setModification(Modification modification) {
		this.modification = modification;
	}
}
