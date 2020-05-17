package ua.training.controller.command.category;

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
import ua.training.entity.Category;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.validator.entity.CategoryValidator;

public class PostUpdateCategoryCommand implements Command {

	private CategoryService categoryService;

	public PostUpdateCategoryCommand(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Category category = getUserInput(request);
		List<String> errors = validateUserInput(category);

		if (errors.isEmpty()) {
			categoryService.updateCategory(category);
			redirectToAllCategoriesPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		addRequesAttributes(request, category, errors);
		return Page.ADD_UPDATE_CATEGORY_VIEW;
	}

	private Category getUserInput(HttpServletRequest request) {
		return new Category.Builder().setId(Long.parseLong(request.getParameter(Attribute.ID_CATEGORY)))
				.setName(request.getParameter(Attribute.NAME)).build();
	}

	private List<String> validateUserInput(Category category) {
		return CategoryValidator.getInstance().validate(category);
	}

	private void redirectToAllCategoriesPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_CATEGORY_UPDATE);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_CATEGORIES, urlParams);
	}

	private void addRequesAttributes(HttpServletRequest request, Category category, List<String> errors) {

		request.setAttribute(Attribute.CATEGORY, category);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
