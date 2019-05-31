package kp.company.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import kp.company.Department;

/**
 * <code>Validator</code> for <code>Department</code> forms.
 */
public class DepartmentValidator {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Validates department form data.
	 * 
	 * @param department
	 *            department
	 * @param errors
	 *            errors
	 */
	public void validate(Department department, Errors errors) {

		String name = department.getName();
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}
		logger.debug("validate(): name[" + name + "]");
	}
}