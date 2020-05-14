package ua.training.controller;

import ua.training.controller.command.Command;
import ua.training.controller.command.HomeCommand;
import ua.training.controller.command.PageNotFoundCommand;
import ua.training.controller.command.auth.GetLoginCommand;
import ua.training.controller.command.auth.LogoutCommand;
import ua.training.controller.command.auth.PostLoginCommand;

import ua.training.controller.command.i18n.ChangeLocaleCommand;
import ua.training.controller.command.user.AllUsersCommand;
import ua.training.controller.command.user.GetAddUserCommand;
import ua.training.controller.command.user.PostAddUserCommand;
import ua.training.controller.command.user.SearchBestWaitersPerPeriod;
import ua.training.controller.command.user.SearchUserByRoleCommand;
import ua.training.controller.command.user.SearchUserBySurnameCommand;
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
	SEARCH_USER_BY_ROLE{
		{
			this.key = "POST:manager/users/role";
			this.command = new SearchUserByRoleCommand(UserService.getInstance());
		}
	},
	SEARCH_BEST_WAITERS_PER_PERIOD{
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
