package kp.company;

import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing an employee.
 */
public class Employee extends Person {

	private Department department;
	private Title title;

	/**
	 * Sets department.
	 * 
	 * @param department department
	 */
	protected void setDepartment(Department department) {

		this.department = department;
	}

	/**
	 * Gets department.
	 * 
	 * @return department
	 */
	public Department getDepartment() {

		return this.department;
	}

	/**
	 * Sets title.
	 * 
	 * @param title title
	 */
	public void setTitle(Title title) {

		this.title = title;
	}

	/**
	 * Gets title.
	 * 
	 * @return title title
	 */
	public Title getTitle() {

		return this.title;
	}

	/**
	 * Returns a string representation of the Employee object.
	 * 
	 * @return a string representation of the Employee object
	 */
	@Override
	public String toString() {

		ToStringCreator tsc = new ToStringCreator(this);
		tsc.append("id", this.getId());
		tsc.append("new", this.isNew());
		tsc.append("lastName", this.getLastName());
		tsc.append("firstName", this.getFirstName());
		tsc.append("department", this.department);
		tsc.append("title", this.title);
		return tsc.toString();
	}
}
