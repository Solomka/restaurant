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
import ua.training.locale.Message;
import ua.training.service.OrderService;

public class DeleteOrderCommand implements Command {

	private final OrderService orderService;

	public DeleteOrderCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long userId = Long.parseLong(request.getParameter(Attribute.ID_ORDER));

		orderService.deleteOrder(userId);

		redirectToAllOrdersPageWithSuccessMessage(request, response);
		return RedirectionManager.REDIRECTION;
	}

	private void redirectToAllOrdersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_ORDER_DELETE);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
	}
}
