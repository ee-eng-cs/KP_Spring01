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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kp.company.Company;
import kp.company.Department;
import kp.company.Employee;
import kp.company.Title;
import kp.company.validation.EmployeeValidator;

/**
 * JavaBean form controller that is used to add a new <code>Employee</code> to
 * the system.
 */
@Controller
@RequestMapping("/departments/{departmentId}/employees/new")
@SessionAttributes("employee")
public class AddEmployeeForm {

	private final Log logger = LogFactory.getLog(getClass());

	private final Company company;

	/**
	 * Constructor (autowired by Spring's dependency injection facilities).
	 * 
	 * @param company
	 *            company
	 */
	@Autowired
	public AddEmployeeForm(Company company) {

		this.company = company;
		logger.debug("AddEmployeeForm():");
	}

	/**
	 * Populates titles.
	 * 
	 * @return view name
	 */
	@ModelAttribute("titles")
	public Collection<Title> populateTitles() {

		Collection<Title> resultList = this.company.getTitles();

		logger.debug("populateTitles():");
		return resultList;
	}

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
		Employee employee = new Employee();
		department.addEmployee(employee);
		model.addAttribute("employee", employee);
		String viewName = "employees/form";

		logger.info("setupForm(): departmentId[" + departmentId
				+ "], viewName[" + viewName + "]");
		return viewName;
	}

	/**
	 * Processes submit.
	 * 
	 * @param employee
	 *            employee
	 * @param result
	 *            result
	 * @param status
	 *            status
	 * @return view name
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("employee") Employee employee,
			BindingResult result, SessionStatus status) {

		new EmployeeValidator().validate(employee, result);
		String viewName;
		if (result.hasErrors()) {
			viewName = "employees/form";
		} else {
			this.company.storeEmployee(employee);
			status.setComplete();
			viewName = "redirect:/departments/"
					+ employee.getDepartment().getId();
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
	 */
}