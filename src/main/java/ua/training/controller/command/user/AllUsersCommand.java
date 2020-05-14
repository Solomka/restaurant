package ua.training.controller.command.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.service.UserService;

public class AllUsersCommand implements Command {

	private UserService userService;

	public AllUsersCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> users = userService.getAllUsers();
		
		request.setAttribute(Attribute.USERS, users);
		request.setAttribute(Attribute.ROLES, Role.values());
		return Page.ALL_USERS_VIEW;
	}
}
