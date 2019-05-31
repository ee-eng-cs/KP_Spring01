package kp.company.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kp.company.Company;
import kp.company.service.CompanyService;

/**
 * Annotation-driven <em>MultiActionController</em> that handles all non-form
 * URL's.
 */
@Controller
public class CompanyController {

	private final Log logger = LogFactory.getLog(getClass());

	private final Company company;
	private final CompanyService companyService;

	/**
	 * Constructor (autowired by Spring's dependency injection facilities).
	 * 
	 * @param company the company
	 * @param companyService the company service
	 */
	@Autowired
	public CompanyController(Company company, CompanyService companyService) {

		this.company = company;
		this.companyService = companyService;
		logger.debug("CompanyController():");
	}

	/**
	 * Custom handler for the welcome view.
	 * <p>
	 * Note that this handler relies on the RequestToViewNameTranslator to determine
	 * the logical view name based on the request URL: "/welcome.do" -&gt;
	 * "welcome".
	 * 
	 * @return logical view name
	 */
	@RequestMapping("/")
	public String welcomeHandler() {

		String viewName = "welcome";
		logger.info("welcomeHandler(): viewName[" + viewName + "]");
		return viewName;
	}

	/**
	 * Custom handler for displaying a department.
	 * 
	 * @param departmentId the ID of the department to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping("/departments/{departmentId}")
	public ModelAndView departmentHandler(@PathVariable("departmentId") int departmentId) {

		String viewName = "departments/show";
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject(this.company.loadDepartment(departmentId));

		logger.info("departmentHandler(): departmentId[" + departmentId + "], viewName[" + viewName + "]");
		return mav;
	}

	/**
	 * Joins two <code>Department</code>s.
	 * 
	 * @return view name
	 */
	@RequestMapping("/departments/join")
	public String joinDepartments() {

		companyService.joinDepartments();
		String viewName = "welcome";

		logger.info("joinDepartments(): viewName[" + viewName + "]");
		return viewName;
	}
}