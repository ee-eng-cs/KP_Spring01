package kp.company;

/**
 * Simple JavaBean domain object representing an person.
 */
public class Person extends BaseEntity {

	private String firstName;
	private String lastName;

	/**
	 * Gets first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets first name.
	 * 
	 * @param firstName first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets last name.
	 * 
	 * @param lastName last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}