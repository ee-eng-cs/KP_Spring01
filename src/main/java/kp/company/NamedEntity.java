package kp.company;

/**
 * Simple JavaBean domain object adds a name property to <code>BaseEntity</code>
 * . Used as a base class for objects needing these properties.
 */
public class NamedEntity extends BaseEntity {

	private String name;

	/**
	 * Sets name.
	 * 
	 * @param name name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Gets name.
	 * 
	 * @return name
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * Returns a string representation of the NamedEntity object.
	 * 
	 * @return a string representation of the NamedEntity object
	 */
	@Override
	public String toString() {

		return this.name;
	}

}
