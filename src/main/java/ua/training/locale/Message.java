package ua.training.locale;

public final class Message {	
	
	private Message() {
	}

	public static String ROLE_CHIEF = "restaurant.user.role.chief";
	public static String ROLE_MANAGER = "restaurant.user.role.manager";
	public static String ROLE_WAITER = "restaurant.user.role.waiter";
	
	public static String STATUS_NEW = "restaurant.order.status.new";
	public static String STATUS_IN_PROGRESS = "restaurant.order.status.inProgress";
	public static String STATUS_PREPARED = "restaurant.order.status.prepared";
	public static String STATUS_PAID = "restaurant.order.status.paid";	
	
	public static String LOGGED_IN_AS = "restaurant.loggedIn";
	public static String PAGE_NOT_FOUND_ERROR = "restaurant.error.pageNotFoundError";
	public static String SERVER_ERROR = "restaurant.error.serverError";
	public static String DIRECT_VIEW_ACCESS_ERROR = "restaurant.error.directViewAccessError";
	public static String UNAUTHORIZED_ACCESS_ERROR = "restaurant.error.authorizedAccessError";
	
	public static String USER_IS_NOT_FOUND = "restaurant.user.isNotFound";
	public static String CATEGORY_IS_NOT_FOUND = "restaurant.category.isNotFound";
	public static String DISH_IS_NOT_FOUND = "restaurant.dish.isNotFound";
		
	public static String INVALID_NAME_INPUT = "restaurant.error.invalidName";
	public static String INVALID_SURNAME_INPUT = "restaurant.error.invalidSurname";
	public static String INVALID_EMAIL = "restaurant.error.invalidEmail";
	public static String INVALID_PASS = "restaurant.error.invalidPass";
	public static String INVALID_CREDENTIALS = "restaurant.error.invalidCredentials";
	public static String INVALID_PHONE = "restaurant.error.invalidPhone";
	public static String INVALID_ADDRESS = "restaurant.error.invalidAddress";
	public static String INVALID_ROLE = "restaurant.error.invalidRole";
	public static String INVALID_DATE = "restaurant.error.invalidDate";
	public static String INVALID_NEW_PASSWORD = "restaurant.error.invalidNewPassword";
	public static String IVALID_CONFIRM_PASSWORD = "restaurant.error.invalidConfirmPassword";
	public static String INVALID_NEW_CONFIRM_PASSWORD = "restaurant.error.invalidNewConfirmPassword";
	public static String INVALID_CATEGORY = "restaurant.error.invalidCategory";
	public static String INVALID_DESCRIPTION_INPUT = "restaurant.error.invalidDescription";
	public static String INVALID_WEIGHT_INPUT = "restaurant.error.invalidWeight";
	public static String INVALID_COST_INPUT = "restaurant.error.invalidCost";
	
	public static String SUCCESS_USER_ADDITION = "restaurant.success.userSuccessfullyAdded";
	public static String SUCCESS_USER_UPDATE = "restaurant.success.userSuccessfullyUpdated";
	public static String SUCCESS_USER_DELETE = "restaurant.success.userSuccessfullyDeleted";
	public static String SUCCESS_CATEGORY_ADDITION = "restaurant.success.categorySuccessfullyAdded";
	public static String SUCCESS_CATEGORY_UPDATE = "restaurant.success.categorySuccessfullyUpdated";
	public static String SUCCESS_CATEGORY_DELETE = "restaurant.success.categorySuccessfullyDeleted";
	public static String SUCCESS_DISH_DELETE = "restaurant.success.dishSuccessfullyDeleted";
	public static String SUCCESS_DISH_ADDITION = "restaurant.success.dishSuccessfullyAdded";
	public static String SUCCESS_DISH_UPDATE = "restaurant.success.dishSuccessfullyUpdated";
}
