package ua.training.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Class that represents filter responsible for changing request/response
 * encoding to one defined by the filter parameter
 * 
 * @author Solomka
 *
 */
@WebFilter(urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Parameter") })
public class EncodingFilter implements Filter {

	private static String ENCODING = "encoding";
	private String code;

	public void init(FilterConfig fConfig) throws ServletException {
		code = fConfig.getInitParameter(ENCODING);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String codeRequest = request.getCharacterEncoding();

		if (code != null && !code.equalsIgnoreCase(codeRequest)) {
			request.setCharacterEncoding(code);
			response.setCharacterEncoding(code);
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		code = null;
	}
}
