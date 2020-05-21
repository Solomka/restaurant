package ua.training.controller;

import ua.training.controller.command.Command;
import ua.training.controller.command.HomeCommand;
import ua.training.controller.command.PageNotFoundCommand;
import ua.training.controller.command.auth.GetLoginCommand;
import ua.training.controller.command.auth.LogoutCommand;
import ua.training.controller.command.auth.PostLoginCommand;
import ua.training.controller.command.category.AllCategoriesCommand;
import ua.training.controller.command.category.DeleteCategoryCommand;
import ua.training.controller.command.category.GetAddCategoryCommand;
import ua.training.controller.command.category.GetUpdateCategoryCommand;
import ua.training.controller.command.category.PostAddCategoryCommand;
import ua.training.controller.command.category.PostUpdateCategoryCommand;
import ua.training.controller.command.category.SearchCategoriesByName;
import ua.training.controller.command.dish.AllDishesCommand;
import ua.training.controller.command.dish.DeleteDishCommand;
import ua.training.controller.command.dish.GetAddDishCommand;
import ua.training.controller.command.dish.GetUpdateDishCommand;
import ua.training.controller.command.dish.PostAddDishCommand;
import ua.training.controller.command.dish.PostUpdateDishCommand;
import ua.training.controller.command.dish.SearchDishesByCategoryCommand;
import ua.training.controller.command.dish.SearchDishesByNameCommand;
import ua.training.controller.command.dish.SearchMostPopularDishesPerPeriodCommand;
import ua.training.controller.command.i18n.ChangeLocaleCommand;
import ua.training.controller.command.user.AllUsersCommand;
import ua.training.controller.command.user.DeleteUserCommand;
import ua.training.controller.command.user.GetAddUserCommand;
import ua.training.controller.command.user.GetUpdateUserCommand;
import ua.training.controller.command.user.PostAddUserCommand;
import ua.training.controller.command.user.PostUpdateUserCommand;
import ua.training.controller.command.user.SearchBestWaitersPerPeriod;
import ua.training.controller.command.user.SearchUserByRoleCommand;
import ua.training.controller.command.user.SearchUserBySurnameCommand;
import ua.training.service.CategoryService;
import ua.training.service.DishService;
import ua.training.service.UserService;

enum CommandEnum {

	PAGE_NOT_FOUND {
		{
			this.key = "GET:pageNotFound";
			this.command = new PageNotFoundCommand();
		}
	},
	HOME {
		{
			this.key = "GET:";
			this.command = new HomeCommand();
		}
	},
	CHANGE_LOCALE {
		{
			this.key = "GET:locale";
			this.command = new ChangeLocaleCommand();
		}
	},
	GET_LOGIN {
		{
			this.key = "GET:login";
			this.command = new GetLoginCommand();
		}
	},
	LOGOUT {
		{
			this.key = "GET:logout";
			this.command = new LogoutCommand();
		}
	},
	POST_LOGIN {
		{
			this.key = "POST:login";
			this.command = new PostLoginCommand(UserService.getInstance());
		}
	},
	ALL_USERS {
		{
			this.key = "GET:manager/users";
			this.command = new AllUsersCommand(UserService.getInstance());
		}
	},
	SEARCH_USER_BY_SURNAME {
		{
			this.key = "POST:manager/users/surname";
			this.command = new SearchUserBySurnameCommand(UserService.getInstance());
		}
	},
	SEARCH_USER_BY_ROLE {
		{
			this.key = "POST:manager/users/role";
			this.command = new SearchUserByRoleCommand(UserService.getInstance());
		}
	},
	SEARCH_BEST_WAITERS_PER_PERIOD {
		{
			this.key = "POST:manager/users/bestWaiters";
			this.command = new SearchBestWaitersPerPeriod(UserService.getInstance());
		}
	},
	GET_ADD_USER {
		{
			this.key = "GET:manager/users/addUser";
			this.command = new GetAddUserCommand();
		}
	},
	POST_ADD_USER {
		{
			this.key = "POST:manager/users/addUser";
			this.command = new PostAddUserCommand(UserService.getInstance());
		}
	},
	GET_UPDATE_USER {
		{
			this.key = "GET:manager/users/updateUser";
			this.command = new GetUpdateUserCommand(UserService.getInstance());
		}
	},
	POST_UPDATE_USER {
		{
			this.key = "POST:manager/users/updateUser";
			this.command = new PostUpdateUserCommand(UserService.getInstance());
		}
	},
	DELETE_USER {
		{
			this.key = "GET:manager/users/deleteUser";
			this.command = new DeleteUserCommand(UserService.getInstance());
		}
	},
	ALL_CATEGORIES {
		{
			this.key = "GET:manager/categories";
			this.command = new AllCategoriesCommand(CategoryService.getInstance());
		}
	},
	SEARCH_CATEGORY_BY_NAME {
		{
			this.key = "POST:manager/categories/name";
			this.command = new SearchCategoriesByName(CategoryService.getInstance());
		}
	},
	GET_ADD_CATEGORY {
		{
			this.key = "GET:manager/categories/addCategory";
			this.command = new GetAddCategoryCommand();
		}
	},
	POST_ADD_CATEGORY {
		{
			this.key = "POST:manager/categories/addCategory";
			this.command = new PostAddCategoryCommand(CategoryService.getInstance());
		}
	},
	GET_UPDATE_CATEGORY {
		{
			this.key = "GET:manager/categories/updateCategory";
			this.command = new GetUpdateCategoryCommand(CategoryService.getInstance());
		}
	},
	POST_UPDATE_CATEGORY {
		{
			this.key = "POST:manager/categories/updateCategory";
			this.command = new PostUpdateCategoryCommand(CategoryService.getInstance());
		}
	},
	DELETE_CATEGORY {
		{
			this.key = "GET:manager/categories/deleteCategory";
			this.command = new DeleteCategoryCommand(CategoryService.getInstance());
		}
	},
	ALL_DISHES {
		{
			this.key = "GET:dishes";
			this.command = new AllDishesCommand(DishService.getInstance(), CategoryService.getInstance());
		}
	},
	SEARCH_DISHES_BY_NAME {
		{
			this.key = "POST:dishes/name";
			this.command = new SearchDishesByNameCommand(DishService.getInstance());
		}
	},
	SEARCH_DISHES_BY_CATEGORY {
		{
			this.key = "POST:dishes/category";
			this.command = new SearchDishesByCategoryCommand(DishService.getInstance(), CategoryService.getInstance());
		}
	},
	SEARCH_MOST_POPULAR_DISHES {
		{
			this.key = "POST:manager/dishes/bestDishes";
			this.command = new SearchMostPopularDishesPerPeriodCommand(DishService.getInstance(), CategoryService.getInstance());
		}
	},
	GET_ADD_DISH {
		{
			this.key = "GET:manager/dishes/addDish";
			this.command = new GetAddDishCommand(CategoryService.getInstance());
		}
	},
	POST_ADD_DISH {
		{
			this.key = "POST:manager/dishes/addDish";
			this.command = new PostAddDishCommand(DishService.getInstance(), CategoryService.getInstance());
		}
	},
	GET_UPDATE_DISH {
		{
			this.key = "GET:manager/dishes/updateDish";
			this.command = new GetUpdateDishCommand(DishService.getInstance(), CategoryService.getInstance());
		}
	},
	POST_UPDATE_DISH {
		{
			this.key = "POST:manager/dishes/updateDish";
			this.command = new PostUpdateDishCommand(DishService.getInstance(), CategoryService.getInstance());
		}
	},
	DELETE_DISH {
		{
			this.key = "GET:manager/dishes/deleteDish";
			this.command = new DeleteDishCommand(DishService.getInstance());
		}
	};

	String key;
	Command command;

	public Command getCommand() {
		return command;
	}

	public String getKey() {
		return key;
	}

	public static Command getCommand(String key) {
		for (final CommandEnum command : CommandEnum.values()) {
			if (command.getKey().equals(key)) {
				return command.getCommand();
			}
		}
		return PAGE_NOT_FOUND.getCommand();
	}
}
