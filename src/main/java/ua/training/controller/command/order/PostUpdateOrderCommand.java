package ua.training.controller.command.order;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Order;
import ua.training.entity.Status;
import ua.training.locale.Message;
import ua.training.service.OrderService;

public class PostUpdateOrderCommand implements Command {

	private OrderService orderService;

	public PostUpdateOrderCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Order order = getUserInput(request);
		orderService.updateOrder(order);
		redirectToAllOrdersPageWithSuccessMessage(request, response);
		return RedirectionManager.REDIRECTION;

	}

	private Order getUserInput(HttpServletRequest request) {
		return new Order.Builder().setId(Long.parseLong(request.getParameter(Attribute.ID_ORDER)))
				.setStatus(Status.forValue(request.getParameter(Attribute.STATUS))).build();
	}

	private void redirectToAllOrdersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_ORDER_UPDATE);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
	}
}
