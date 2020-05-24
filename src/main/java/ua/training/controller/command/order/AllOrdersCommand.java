package ua.training.controller.command.order;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.Order;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.service.OrderService;

public class AllOrdersCommand implements Command {

	private OrderService orderService;

	public AllOrdersCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loggedInUser = SessionManager.getInstance().getUserFromSession(request.getSession());
		List<Order> orders = new ArrayList<Order>();

		if (loggedInUser.getRole().equals(Role.WAITER)) {
			orders = orderService.searchWaiterOrdersPerToday(loggedInUser.getId(), LocalDate.now());
		} else if (loggedInUser.getRole().equals(Role.CHIEF)) {
			orders = orderService.searchUpreparedOrdersForToday(LocalDate.now());
		} else if (loggedInUser.getRole().equals(Role.MANAGER)) {
			orders = orderService.getAllOrders();
		}

		request.setAttribute(Attribute.ORDERS, orders);
		return Page.ALL_ORDERS_VIEW;
	}

}
