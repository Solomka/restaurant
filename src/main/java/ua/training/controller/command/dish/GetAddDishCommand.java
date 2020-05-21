package ua.training.controller.command.dish;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.service.CategoryService;

public class GetAddDishCommand implements Command {

	private CategoryService categoryService;

	public GetAddDishCommand(CategoryService categoryService) {
		this.categoryService = categoryService;

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute(Attribute.CATEGORIES, categoryService.getAllCategories());
		return Page.ADD_UPDATE_DISH_VIEW;
	}
}
