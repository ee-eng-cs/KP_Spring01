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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kp.company.Company;
import kp.company.Department;
import kp.company.validation.DepartmentValidator;

/**
 * JavaBean form controller that is used to add a new <code>Department</code> to
 * the system.
 */
@Controller
@RequestMapping("/departments/new")
@SessionAttributes(types = Department.class)
public class AddDepartmentForm {

	private final Log logger = LogFactory.getLog(getClass());

	private final Company company;

	/**
	 * Constructor (autowired by Spring's dependency injection facilities).
	 * 
	 * @param company
	 *            company
	 */
	@Autowired
	public AddDepartmentForm(Company company) {

		this.company = company;
		logger.debug("AddDepartmentForm():");
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
	 * Prepare form.
	 * 
	 * 
	 * 
	 * @param model
	 *            model
	 * @return view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		/*
		 */
		Department department = new Department();
		/*
		 */
		model.addAttribute(department);
		String viewName = "departments/form";

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
	 * @param status
	 *            status
	 * @return view name
	 */
	@RequestMapping(method = RequestMethod.POST)
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}