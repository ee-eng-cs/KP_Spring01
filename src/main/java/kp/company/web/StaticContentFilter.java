package kp.company.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Static content filter.
 */
public class StaticContentFilter implements Filter {

	private final Log logger = LogFactory.getLog(getClass());
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;
	private RequestDispatcher requestDispatcher = null;

	/**
	 * Called before the filter goes into service. Sets the filter's
	 * configuration object.
	 * 
	 * @param filterConfig the filter config
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;
		ServletContext servletContext = filterConfig.getServletContext();
		requestDispatcher = servletContext.getNamedDispatcher("default");
		logger.debug("init():");
	}

	/**
	 * Performs the filtering work.
	 * 
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String servletPath = httpServletRequest.getServletPath();
		if (servletPath.startsWith("/static")) {
			requestDispatcher.forward(request, response);
			logger.debug("doFilter(): forward, servletPath[" + servletPath
					+ "]");
			return;
		}
		chain.doFilter(request, response);
		logger.debug("doFilter(): chain, servletPath[" + servletPath + "]");
	}

	/**
	 * Called after the filter has been taken out of service.
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

		filterConfig = null;
		requestDispatcher = null;
		logger.debug("destroy():");
	}

}
