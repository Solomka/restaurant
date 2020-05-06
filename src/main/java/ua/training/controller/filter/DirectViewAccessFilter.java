package ua.training.controller.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;

/**
 * Class that represents filter resposible for controlling unauthorized direct
 * access to jsp-pages
 * 
 * @author Solomka
 *
 */
@WebFilter(urlPatterns = { "/views/*" })
public class DirectViewAccessFilter implements Filter {

	private final static Logger LOGGER = Logger.getLogger(DirectViewAccessFilter.class);
	private static String UNAUTHORIZED_ACCESS = "Unauthorized access to the resource: ";

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		logInfoAboutUnauthorizedAccess(httpRequest.getRequestURI());
		httpResponse.sendRedirect(toHomePageWithErrorMessage(httpRequest.getContextPath()));
	}

	public void destroy() {
	}

	private String toHomePageWithErrorMessage(String contextPath) throws UnsupportedEncodingException {
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ERROR, Message.DIRECT_VIEW_ACCESS_ERROR);
		return new StringBuffer(contextPath).append(ServletPath.HOME)
				.append(RedirectionManager.getInstance().generateUrlParams(urlParams)).toString();
	}

	private void logInfoAboutUnauthorizedAccess(String uri) {
		LOGGER.info(UNAUTHORIZED_ACCESS + uri);
	}
}
