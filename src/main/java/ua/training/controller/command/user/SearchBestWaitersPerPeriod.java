package ua.training.controller.command.user;

import java.io.IOException;
import java.time.LocalDate;
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

public class SearchBestWaitersPerPeriod implements Command {

	private UserService userService;

	public SearchBestWaitersPerPeriod(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fromDate = request.getParameter(Attribute.FROM_DATE);
		String toDate = request.getParameter(Attribute.TO_DATE);

		List<String> errors = validateUserInput(fromDate, toDate);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, errors.get(0));
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_USERS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<User> users = userService.searchBestWaitersPerPeriod(LocalDate.parse(fromDate), LocalDate.parse(toDate));

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

	private List<String> validateUserInput(String fromDate, String toDate) {
		List<String> errors = new ArrayList<>();
		
		if(fromDate.isEmpty() || toDate.isEmpty() || LocalDate.parse(fromDate).compareTo(LocalDate.parse(toDate)) > 0 ) {
			errors.add(Message.INVALID_DATE);
		}

		return errors;
	}
}
