package ua.training.locale;

import java.util.Locale;
import java.util.ResourceBundle;

public final class MessageLocale {

	private MessageLocale() {
	}

	public static final String MESSAGES_BUNDLE_NAME = "i18n/messages";

	// default Locale
	public static ResourceBundle BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, new Locale("en", "US"));

	public static void setResourceBundleLocale(Locale locale) {
		BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, locale);
	}
}
