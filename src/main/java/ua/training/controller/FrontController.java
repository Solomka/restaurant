package ua.training.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.command.i18n.AppLocale;
import ua.training.controller.utils.CommandKeyGenerator;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.exception.ServiceException;

/**
 * Application HTTP Front Servlet that processes all the incoming requests
 */

@WebServlet(urlPatterns = { "/controller/*" }, loadOnStartup = 1)
public class FrontController extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(FrontController.class);
	private static final long serialVersionUID = 1L;

	public FrontController() {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		getServletContext().setAttribute(Attribute.LOCALES, AppLocale.getAppLocales());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
		processRequest(req, resp);
	}

	/**
	 * Processes all the requests by proper concrete command that implements
	 * {@link Command} interface depends on the request path
	 * 
	 * @param request
	 *            incoming request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		String commandKey = CommandKeyGenerator.generateCommandKeyFromRequest(request);
		Command command = CommandFactory.getCommand(commandKey);
		try {
			String commandResultedResource = command.execute(request, response);
			forwardToCommandResultedPage(httpWrapper, commandResultedResource);
		} catch (ServiceException ex) {
			LOGGER.error("Error has occured while command execution with key: " + commandKey);
			redirecToHomePageWithErrorMessage(httpWrapper, ex);
		}
	}

	/**
	 * Forwards to the returned by the concrete command that implements
	 * {@link Command} interface resulted resource or do nothing if redirection
	 * has occured
	 * 
	 * @param httpWrapper
	 * @param resultRedirectResource
	 *            returned by the concrete command resulted
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardToCommandResultedPage(HttpWrapper httpWrapper, String resultedRedirectResource)
			throws ServletException, IOException {
		if (!resultedRedirectResource.contains(RedirectionManager.REDIRECTION)) {
			httpWrapper.getRequest().getRequestDispatcher(resultedRedirectResource).forward(httpWrapper.getRequest(),
					httpWrapper.getResponse());
		}
	}

	/**
	 * Redirect to the Home page with appropriate error message if error in
	 * service layer occured
	 * 
	 * @param httpWrapper
	 * @param ex
	 * @throws IOException
	 */
	private void redirecToHomePageWithErrorMessage(HttpWrapper httpWrapper, ServiceException ex) throws IOException {
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ERROR, ex.getMessage());
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.HOME, urlParams);
	}
}
