package ua.training.controller.command.dish;

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
import ua.training.entity.Dish;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.DishService;

public class SearchMostPopularDishesPerPeriodCommand implements Command {

	private DishService dishService;
	private CategoryService categoryService;

	public SearchMostPopularDishesPerPeriodCommand(DishService dishService, CategoryService categoryService) {
		this.dishService = dishService;
		this.categoryService = categoryService;
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
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_DISHES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<Dish> dishes = dishService.searchMostPopularDishesPerPeriod(LocalDate.parse(fromDate),
				LocalDate.parse(toDate));

		if (dishes.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.DISH_IS_NOT_FOUND);
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_DISHES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.DISHES, dishes);
		request.setAttribute(Attribute.CATEGORIES, categoryService.getAllCategories());
		return Page.ALL_DISHES_VIEW;

	}

	private List<String> validateUserInput(String fromDate, String toDate) {
		List<String> errors = new ArrayList<>();
		
		if(fromDate.isEmpty() || toDate.isEmpty() || LocalDate.parse(fromDate).compareTo(LocalDate.parse(toDate)) > 0 ) {
			errors.add(Message.INVALID_DATE);
		}

		return errors;
	}
}
