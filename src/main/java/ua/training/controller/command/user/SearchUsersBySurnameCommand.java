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
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class SearchUsersBySurnameCommand implements Command {

	private final UserService userService;

	public SearchUsersBySurnameCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String surname = request.getParameter(Attribute.SURNAME);
		List<String> errors = validateUserInput(surname);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, errors.get(0));
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_USERS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<User> users = userService.searchUsersBySurname(surname);

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

	private List<String> validateUserInput(String surname) {
		List<String> errors = new ArrayList<>();

		AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();
		fieldValidator.validateField(FieldValidatorKey.SURNAME, surname, errors);
		return errors;
	}
}
