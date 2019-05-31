package kp.company.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kp.company.Company;
import kp.company.Department;
import kp.company.validation.DepartmentValidator;

/**
 * JavaBean Form controller that is used to edit an existing
 * <code>Department</code>.
 */
@Controller
@RequestMapping("/departments/{departmentId}/edit")
@SessionAttributes(types = Department.class)
public class EditDepartmentForm {

	private final Log logger = LogFactory.getLog(getClass());

	private final Company company;

	/**
	 * Constructor (autowired by Spring's dependency injection facilities).
	 * 
	 * @param company
	 *            company
	 */
	@Autowired
	public EditDepartmentForm(Company company) {

		this.company = company;
		logger.debug("EditDepartmentForm():");
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	/**
	 * Sets allowed fields.
	 * 
	 * @param dataBinder
	 *            data binder
	 */
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {

		dataBinder.setDisallowedFields(new String[] { "id" });
		logger.debug("setAllowedFields():");
	}

	/**
	 * Prepares form.
	 * 
	 * @param departmentId
	 *            department id
	 * @param model
	 *            model
	 * @return view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(@PathVariable("departmentId") int departmentId,
			Model model) {

		Department department = this.company.loadDepartment(departmentId);
		/*
		 */
		model.addAttribute(department);
		String viewName = "departments/form";

		logger.info("setupForm(): departmentId[" + departmentId
				+ "], viewName[" + viewName + "]");
		return viewName;
	}

	/**
	 * Processes submit.
	 * 
	 * @param department
	 *            department
	 * @param result
	 *            result
	 * @param status
	 *            status
	 * @return view name
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String processSubmit(@ModelAttribute Department department,
			BindingResult result, SessionStatus status) {

		new DepartmentValidator().validate(department, result);
		String viewName;
		if (result.hasErrors()) {
			viewName = "departments/form";
		} else {
			this.company.storeDepartment(department);
			status.setComplete();
			viewName = "redirect:/departments/" + department.getId();
		}

		logger.info("processSubmit(): viewName[" + viewName + "]");
		return viewName;
	}

	/**
	 * Deletes department.
	 * 
	 * @param departmentId
	 *            department id
	 * @return view name
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public String deleteDepartment(@PathVariable int departmentId) {

		this.company.deleteDepartment(departmentId);
		String viewName = "redirect:/departments/search";

		logger.info("deleteDepartment(): departmentId[" + departmentId
				+ "], viewName[" + viewName + "]");
		return viewName;
	}
	/*
	 * 
	 */
}