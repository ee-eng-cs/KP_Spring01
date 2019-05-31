package kp.company.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import kp.company.Department;
import kp.company.Employee;

/**
 * <code>Validator</code> for <code>Employee</code> forms.
 */
public class EmployeeValidator {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Validates employee form data.
	 * 
	 * @param employee
	 *            employee
	 * @param errors
	 *            errors
	 */
	public void validate(Employee employee, Errors errors) {

		String firstName = employee.getFirstName();
		if (!StringUtils.hasLength(firstName)) {
			errors.rejectValue("firstName", "required", "required");
		}
		String lastName = employee.getLastName();
		if (!StringUtils.hasLength(lastName)) {
			errors.rejectValue("lastName", "required", "required");
		}
		if (employee.isNew()) {
			Department department = employee.getDepartment();
			if (department.getEmployee(firstName, lastName, true) != null) {
				errors.rejectValue("firstName", "duplicate", "already exists");
				errors.rejectValue("lastName", "duplicate", "already exists");
			}
		}
		logger.debug("validate(): " + "firstName[" + firstName + "], lastName["
				+ lastName + "]");
	}
}