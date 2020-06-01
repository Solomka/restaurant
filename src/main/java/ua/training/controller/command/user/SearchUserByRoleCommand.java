package ua.training.controller.command.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.locale.Message;
import ua.training.service.UserService;

public class SearchUserByRoleCommand implements Command{
	
	private final UserService userService;

	public SearchUserByRoleCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String role = request.getParameter(Attribute.ROLE);
		List<String> errors = validateUserInput(role);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, errors.get(0));
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_USERS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<User> users = userService.searchUsersByRole(Role.forValue(role));

		if (users.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.USER_IS_NOT_FOUND);
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_USERS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.USERS, users);
		request.setAttribute(Attribute.ROLES, Role.values());
		return Page.ALL_USERS_VIEW;
	}

	private List<String> validateUserInput(String role) {
		List<String> errors = new ArrayList<>();
		
		if(role.isEmpty()) {
			errors.add(Message.INVALID_ROLE);
		}

		return errors;
	}

}
