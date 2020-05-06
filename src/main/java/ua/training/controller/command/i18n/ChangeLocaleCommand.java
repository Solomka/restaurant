package ua.training.controller.command.i18n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.locale.MessageLocale;

public class ChangeLocaleCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setLocale(request);
		return Page.HOME_VIEW;
	}

	private void setLocale(HttpServletRequest request) {
		String selectedLanguage = request.getParameter(Attribute.LANG);
		Locale chosenLocale = AppLocale.forValue(selectedLanguage);
		
		request.getSession().setAttribute(Attribute.LOCALE, chosenLocale);
		MessageLocale.setResourceBundleLocale(chosenLocale);
	}
}
