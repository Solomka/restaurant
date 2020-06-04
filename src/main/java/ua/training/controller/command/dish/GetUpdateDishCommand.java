package ua.training.controller.command.dish;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Dish;
import ua.training.service.CategoryService;
import ua.training.service.DishService;

public class GetUpdateDishCommand implements Command {

	private final DishService dishService;
	private final CategoryService categoryService;

	public GetUpdateDishCommand(DishService dishService, CategoryService categoryService) {
		this.dishService = dishService;
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long dishId = Long.parseLong(request.getParameter(Attribute.ID_DISH));

		Optional<Dish> dish = dishService.getDishById(dishId);
						
		request.setAttribute(Attribute.CATEGORIES, categoryService.getAllCategories());
		request.setAttribute(Attribute.DISH, dish.get());
		return Page.ADD_UPDATE_DISH_VIEW;
	}
}
