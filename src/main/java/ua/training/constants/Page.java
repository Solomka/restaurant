package ua.training.constants;

public final class Page {
	
	public static String PREFIX = "/WEB-INF/views/";
	public static String ERROR_PREFIX = "errors";
	public static String SUFFIX = ".jsp";

	private Page() {
	}

	public static String HOME_VIEW = "/index" + SUFFIX;	
	public static String LOGIN_VIEW = PREFIX + "login" + SUFFIX;
	
	public static String ALL_USERS_VIEW = PREFIX + "allUsers" + SUFFIX;
	public static String ADD_UPDATE_USER_VIEW = PREFIX + "addUpdateUser" + SUFFIX;
	
	public static String PAGE_NOT_FOUND = PREFIX + ERROR_PREFIX + "pageNotFound" + SUFFIX;	
}