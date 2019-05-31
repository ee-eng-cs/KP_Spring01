package kp.company;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for
 * objects needing this property.
 */
public class BaseEntity {

	private Integer id;

	/**
	 * Sets id.
	 * 
	 * @param id id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets id.
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Checks if it is new.
	 * 
	 * @return check result
	 */
	public boolean isNew() {
		return (this.id == null);
	}
}
