package ua.training.controller.command.category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Page;
import ua.training.controller.command.Command;

public class GetAddCategoryCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		return Page.ADD_UPDATE_CATEGORY_VIEW;
	}
}
