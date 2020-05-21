package ua.training.controller.command.dish;

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
import ua.training.dto.DishDto;
import ua.training.entity.Category;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.DishService;
import ua.training.validator.entity.DishDtoValidator;

public class PostUpdateDishCommand implements Command {

	private DishService dishService;
	private CategoryService categoryService;

	public PostUpdateDishCommand(DishService dishService, CategoryService categoryService) {
		this.dishService = dishService;
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DishDto dishDto = getUserInput(request);
		List<String> errors = validateUserInput(dishDto);

		if (errors.isEmpty()) {
			dishService.updateDish(dishDto);
			redirectToAllDishesPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		addRequesAttributes(request, dishDto, errors);
		return Page.ADD_UPDATE_DISH_VIEW;
	}

	private DishDto getUserInput(HttpServletRequest request) {
		return new DishDto.Builder().setId(Long.parseLong(request.getParameter(Attribute.ID_DISH)))
				.setName(request.getParameter(Attribute.NAME))
				.setDescription(request.getParameter(Attribute.DESCRIPTION))
				.setWeight(request.getParameter(Attribute.WEIGHT)).setCost(request.getParameter(Attribute.COST))
				.setCategory(
						new Category.Builder().setId(Long.parseLong(request.getParameter(Attribute.CATEGORY))).build())
				.build();
	}

	private List<String> validateUserInput(DishDto dishDto) {
		return DishDtoValidator.getInstance().validate(dishDto);
	}

	private void redirectToAllDishesPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_DISH_UPDATE);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_DISHES, urlParams);
	}

	private void addRequesAttributes(HttpServletRequest request, DishDto dishDto, List<String> errors) {

		request.setAttribute(Attribute.CATEGORIES, categoryService.getAllCategories());
		request.setAttribute(Attribute.DISH, dishDto);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
