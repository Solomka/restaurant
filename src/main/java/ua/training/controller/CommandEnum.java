package ua.training.controller;

import ua.training.controller.command.Command;
import ua.training.controller.command.HomeCommand;
import ua.training.controller.command.PageNotFoundCommand;
import ua.training.controller.command.i18n.ChangeLocaleCommand;

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
