package ua.training.controller.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionManager.getInstance().invalidateSession(request.getSession());
		RedirectionManager.getInstance().redirect(new HttpWrapper(request, response), ServletPath.HOME);
		return RedirectionManager.REDIRECTION;
	}
}
