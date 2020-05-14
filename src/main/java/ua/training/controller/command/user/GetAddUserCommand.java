package ua.training.controller.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Role;

public class GetAddUserCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setAttribute(Attribute.ROLES, Role.values());
		return Page.ADD_USER_VIEW;
	}
}
