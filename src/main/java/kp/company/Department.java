package kp.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

/**
 * Simple JavaBean business object representing a department.
 */
public class Department extends NamedEntity {

	private Set<Employee> employees;

	/**
	 * Sets employees - internal.
	 * 
	 * @param employees employees
	 */
	protected void setEmployeesInternal(Set<Employee> employees) {

		this.employees = employees;
	}

	/**
	 * Gets employees - internal.
	 * 
	 * @return set of employees
	 */
	protected Set<Employee> getEmployeesInternal() {

		if (this.employees == null) {
			this.employees = new HashSet<Employee>();
		}
		return this.employees;
	}

	/**
	 * Gets employees.
	 * 
	 * @return list of employees
	 */
	public List<Employee> getEmployees() {

		List<Employee> sortedEmployees = new ArrayList<Employee>(getEmployeesInternal());
		MutableSortDefinition msd = new MutableSortDefinition("lastName", true, true);
		PropertyComparator.sort(sortedEmployees, msd);
		return Collections.unmodifiableList(sortedEmployees);
	}

	/**
	 * Adds employee.
	 * 
	 * @param employee employee
	 */
	public void addEmployee(Employee employee) {

		getEmployeesInternal().add(employee);
		employee.setDepartment(this);
	}

	/**
	 * Removes employee.
	 * 
	 * @param employee employee
	 */
	public void removeEmployee(Employee employee) {

		getEmployeesInternal().remove(employee);
	}

	/**
	 * Return the Employee with the given last name, or null if none found for this
	 * Department.
	 * 
	 * @param firstName first name
	 * @param lastName  last name
	 * @return true if employee name is already in use
	 */
	public Employee getEmployee(String firstName, String lastName) {

		return getEmployee(firstName, lastName, false);
	}

	/**
	 * Return the Employee with the given name, or null if none found for this
	 * Department.
	 * 
	 * @param firstName first name
	 * @param lastName  last name
	 * @param ignoreNew ignore new
	 * @return true if employee name is already in use
	 */
	public Employee getEmployee(String firstName, String lastName, boolean ignoreNew) {

		for (Employee employee : getEmployeesInternal()) {
			if (!ignoreNew || !employee.isNew()) {
				if (employee.getFirstName().equalsIgnoreCase(firstName)
						&& employee.getLastName().equalsIgnoreCase(lastName)) {
					return employee;
				}
			}
		}
		return null;
	}
}