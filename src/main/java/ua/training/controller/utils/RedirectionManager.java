package ua.training.controller.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ua.training.exception.ServerException;
import ua.training.locale.MessageUtils;

/**
 * Class that is responsible for redirection execution and generation of the new
 * request url
 * 
 * @author Solomka
 *
 */
public class RedirectionManager {

	public static String REDIRECTION = "REDIRECTION";
	private static String MESSAGE_ENCODING = "UTF-8";

	RedirectionManager() {
	}

	private static final class Holder {
		static final RedirectionManager INSTANCE = new RedirectionManager();
	}

	public static RedirectionManager getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * Executes redirection to the concrete resources with params
	 * 
	 * @param httpWrapper
	 *            httpRequest/httpResponse wrapper
	 * @param redirectionPath
	 *            command redirection url
	 * @param urlParameters
	 *            parameter added to the redirection url
	 * @throws IOException
	 *             If an input or output exception occurs
	 */

	public void redirectWithParams(HttpWrapper httpWrapper, String redirectionPath, Map<String, String> urlParameters)
			throws IOException {
		String urlPathWithParams = generateUrlPath(httpWrapper.getRequest(), redirectionPath)
				+ generateUrlParams(urlParameters);
		try {
			httpWrapper.getResponse().sendRedirect(urlPathWithParams);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	public void redirect(HttpWrapper httpWrapper, String path) {
		try {
			httpWrapper.getResponse().sendRedirect(generateUrlPath(httpWrapper.getRequest(), path));
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	/**
	 * Generates redirection url
	 * 
	 * @param request
	 *            incomming request
	 * @param path
	 *            command redirection url
	 * @return redirection url
	 */
	private String generateUrlPath(HttpServletRequest request, String path) {
		return new StringBuffer(request.getContextPath()).append(request.getServletPath()).append(path).toString();
	}

	/**
	 * Generates url parameters from map
	 * 
	 * @param urlParameters
	 * @return url parameters
	 * @throws UnsupportedEncodingException
	 *             If the named encoding is not supported
	 */
	public String generateUrlParams(Map<String, String> urlParameters) throws UnsupportedEncodingException {
		StringBuffer stringBuffer = new StringBuffer(MessageUtils.INTERROGATION_MARK);
		for (String urlParamName : urlParameters.keySet()) {
			stringBuffer.append(urlParamName).append(MessageUtils.EQUALITY_SIGN)
					.append(URLEncoder.encode(urlParameters.get(urlParamName), MESSAGE_ENCODING))
					.append(MessageUtils.AMPERSAND);
		}
		deleteLastAmpersand(stringBuffer);
		return stringBuffer.toString();
	}

	private void deleteLastAmpersand(StringBuffer stringBuffer) {
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
	}
}
