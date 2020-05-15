package ua.training.controller.command.user;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.User;
import ua.training.service.UserService;

public class GetUpdateUserCommand implements Command {
	
	private UserService userService;
	
	public GetUpdateUserCommand (UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long userId = Long.parseLong(request.getParameter(Attribute.ID_USER));
		
		Optional<User> user = userService.getUserById(userId);
		request.setAttribute(Attribute.USER_DTO, user.get());
		
		return Page.ADD_UPDATE_USER_VIEW;
	}

}
