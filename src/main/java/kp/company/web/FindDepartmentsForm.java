package kp.company.web;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kp.company.Company;
import kp.company.Department;

/**
 * JavaBean Form controller that is used to search for <code>Department</code>s
 * by last name.
 * 
 * 
 */
@Controller
public class FindDepartmentsForm {

	private final Log logger = LogFactory.getLog(getClass());

	private final Company company;

	/**
	 * Constructor (autowired by Spring's dependency injection facilities).
	 * 
	 * @param company
	 *            company
	 */
	@Autowired
	public FindDepartmentsForm(Company company) {

		this.company = company;
		logger.debug("FindDepartmentsForm():");
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
	 * 
	 * 
	 * @param model
	 *            model
	 * @return view name
	 */
	@RequestMapping(value = "/departments/search", method = RequestMethod.GET)
	public String setupForm(Model model) {
		/*
		 */
		Department department = new Department();
		/*
		 */
		model.addAttribute("department", department);
		String viewName = "departments/search";

		logger.info("setupForm(): viewName[" + viewName + "]");
		return viewName;
	}

	/**
	 * Processes submit.
	 * 
	 * @param department
	 *            department
	 * @param result
	 *            result
	 * @param model
	 *            model
	 * @return view name
	 */
	@RequestMapping(value = "/departments", method = RequestMethod.GET)
	public String processSubmit(Department department, BindingResult result,
			Model model) {

		// find departments by name
		String name = department.getName();
		// allow parameterless GET request for /departments to return all
		// records
		if (name == null) {
			// setting empty string signifies broadest possible search
			name = "";
		}
		Collection<Department> results = this.company.findDepartments(name);

		String viewName, msg;
		if (results.size() < 1) {
			result.rejectValue("name", "notFound", "not found");
			msg = "no departments found,";
			viewName = "departments/search";
		} else if (results.size() > 1) {
			model.addAttribute("departments", results);
			msg = "multiple departments found,";
			viewName = "departments/list";
		} else {
			department = results.iterator().next();
			msg = "department found,";
			viewName = "redirect:/departments/" + department.getId();
		}
		logger.info("processSubmit(): " + msg + " name[" + name
				+ "], viewName[" + viewName + "]");
		return viewName;

	}
	/*
	 * 
	 */
}