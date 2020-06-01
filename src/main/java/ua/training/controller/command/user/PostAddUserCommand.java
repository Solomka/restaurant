package ua.training.controller.command.user;

import java.io.IOException;
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
import ua.training.dto.UserDto;
import ua.training.entity.Role;
import ua.training.locale.Message;
import ua.training.service.UserService;
import ua.training.validator.entity.UserDtoValidator;

public class PostAddUserCommand implements Command {

	private final UserService userService;

	public PostAddUserCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDto userDto = getUserInput(request);
		List<String> errors = validateUserInput(userDto);

		if (errors.isEmpty()) {
			userService.createUser(userDto);
			redirectToAllUsersPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		addRequesAttributes(request, userDto, errors);
		return Page.ADD_UPDATE_USER_VIEW;
	}

	private UserDto getUserInput(HttpServletRequest request) {
		return new UserDto.Builder().setName(request.getParameter(Attribute.NAME))
				.setSurname(request.getParameter(Attribute.SURNAME)).setAddress(request.getParameter(Attribute.ADDRESS))
				.setPhone(request.getParameter(Attribute.PHONE))
				.setRole(Role.forValue(request.getParameter(Attribute.ROLE)))
				.setEmail(request.getParameter(Attribute.EMAIL)).setPassword(request.getParameter(Attribute.PASSWORD))
				.setConfirmPassword(request.getParameter(Attribute.CONFIRM_PASSWORD)).build();
	}

	private List<String> validateUserInput(UserDto user) {
		return UserDtoValidator.getInstance().validate(user);
	}

	private void redirectToAllUsersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_USER_ADDITION);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_USERS, urlParams);
	}

	private void addRequesAttributes(HttpServletRequest request, UserDto userDto, List<String> errors) {
		
		request.setAttribute(Attribute.ROLES, Role.values());
		request.setAttribute(Attribute.USER_DTO, userDto);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
