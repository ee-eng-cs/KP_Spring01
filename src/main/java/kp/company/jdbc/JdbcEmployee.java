package kp.company.jdbc;

import kp.company.Employee;

/**
 * Subclass of Employee that carries temporary id properties which are only
 * relevant for a JDBC implementation of the Company.
 */
class JdbcEmployee extends Employee {

	private int departmentId;
	private int titleId;

	/**
	 * Sets department id.
	 * 
	 * @param departmentId
	 *            department id
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * Gets department id.
	 * 
	 * @return department id
	 */
	public int getDepartmentId() {
		return this.departmentId;
	}

	/**
	 * Sets title id.
	 * 
	 * @param titleId
	 *            title id
	 */
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	/**
	 * Gets title id.
	 * 
	 * @return title id
	 */
	public int getTitleId() {
		return this.titleId;
	}
}