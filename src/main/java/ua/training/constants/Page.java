package ua.training.constants;

public final class Page {
	
	public static String PREFIX = "/WEB-INF/views/";
	public static String ERROR_PREFIX = "errors";
	public static String SUFFIX = ".jsp";

	private Page() {
	}

	public static String HOME_VIEW = "/index" + SUFFIX;	
	public static String LOGIN_VIEW = PREFIX + "login" + SUFFIX;
	
	public static String ALL_BOOKS_VIEW = PREFIX + "allBooks" + SUFFIX;
	public static String BOOK_INSTANCES_VIEW = PREFIX + "bookInstances" + SUFFIX;
	public static String ADD_BOOK_VIEW = PREFIX + "addBook" + SUFFIX;
	
	public static String ALL_AUTHORS_VIEW = PREFIX + "allAuthors" + SUFFIX;
	public static String ADD_AUTHOR_VIEW = PREFIX + "addAuthor" + SUFFIX;
	
	public static String ALL_READERS_VIEW = PREFIX + "allReaders" + SUFFIX;
	public static String ADD_READER_VIEW = PREFIX + "addReader" + SUFFIX;
	
	public static String ALL_ORDERS_VIEW = PREFIX + "allOrders" + SUFFIX;
	
	public static String CHANGE_PASSWORD_VIEW = PREFIX + "changePassword" + SUFFIX;
	
	public static String PAGE_NOT_FOUND = PREFIX + ERROR_PREFIX + "pageNotFound" + SUFFIX;	
}