package ua.training.controller.command.order;

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
import ua.training.entity.Order;
import ua.training.locale.Message;
import ua.training.service.OrderService;

public class SearchOrdersPerPeriod implements Command {

	private final OrderService orderService;

	public SearchOrdersPerPeriod(OrderService orderService) {
		this.orderService = orderService;
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
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<Order> orders = orderService.searchOrdersByDate(LocalDate.parse(fromDate), LocalDate.parse(toDate));

		if (orders.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.ORDER_IS_NOT_FOUND);
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.ORDERS, orders);
		return Page.ALL_ORDERS_VIEW;
	}

	private List<String> validateUserInput(String fromDate, String toDate) {
		List<String> errors = new ArrayList<>();

		if (fromDate.isEmpty() || toDate.isEmpty()
				|| LocalDate.parse(fromDate).compareTo(LocalDate.parse(toDate)) > 0) {
			errors.add(Message.INVALID_DATE);
		}

		return errors;
	}
}
