package kp.company.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import kp.company.Company;
import kp.company.Title;

/**
 * Shared WebBindingInitializer for kp_spring01 custom editors.
 * 
 * <p>
 * Alternatively, such init-binder code may be put into
 * {@link org.springframework.web.bind.annotation.InitBinder} annotated methods
 * on the controller classes themselves.
 */
public class CompanyBindingInitializer implements WebBindingInitializer {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private Company company;

	/**
	 * Initialize the given DataBinder for the given request.
	 * 
	 * @param binder
	 *            binder
	 * @param request
	 *            request
	 */
	public void initBinder(WebDataBinder binder, WebRequest request) {

		binder.registerCustomEditor(String.class,
				new StringTrimmerEditor(false));
		binder.registerCustomEditor(Title.class, new TitleEditor(this.company));

		logger.debug("initBinder():");
	}
}