package ua.training.controller.command.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.service.DishService;

public class GetAddOrderCommand implements Command {

	private DishService dishService;

	public GetAddOrderCommand(DishService dishService) {
		this.dishService = dishService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute(Attribute.DISHES, dishService.getAllDishes());
		return Page.ADD_UPDATE_ORDER_VIEW;
	}
}
