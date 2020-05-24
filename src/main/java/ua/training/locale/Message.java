package ua.training.locale;

public final class Message {	
	
	private Message() {
	}

	public static final String ROLE_CHIEF = "restaurant.user.role.chief";
	public static final String ROLE_MANAGER = "restaurant.user.role.manager";
	public static final String ROLE_WAITER = "restaurant.user.role.waiter";
	
	public static final String STATUS_NEW = "restaurant.order.status.new";
	public static final String STATUS_IN_PROGRESS = "restaurant.order.status.inProgress";
	public static final String STATUS_PREPARED = "restaurant.order.status.prepared";
	public static final String STATUS_PAID = "restaurant.order.status.paid";	
	
	public static final String LOGGED_IN_AS = "restaurant.loggedIn";
	public static final String PAGE_NOT_FOUND_ERROR = "restaurant.error.pageNotFoundError";
	public static final String SERVER_ERROR = "restaurant.error.serverError";
	public static final String DIRECT_VIEW_ACCESS_ERROR = "restaurant.error.directViewAccessError";
	public static final String UNAUTHORIZED_ACCESS_ERROR = "restaurant.error.authorizedAccessError";
	
	public static final String USER_IS_NOT_FOUND = "restaurant.user.isNotFound";
	public static final String CATEGORY_IS_NOT_FOUND = "restaurant.category.isNotFound";
	public static final String DISH_IS_NOT_FOUND = "restaurant.dish.isNotFound";
	public static final String ORDER_IS_NOT_FOUND = "restaurant.order.isNotFound";
		
	public static final String INVALID_NAME_INPUT = "restaurant.error.invalidName";
	public static final String INVALID_SURNAME_INPUT = "restaurant.error.invalidSurname";
	public static final String INVALID_EMAIL = "restaurant.error.invalidEmail";
	public static final String INVALID_PASS = "restaurant.error.invalidPass";
	public static final String INVALID_CREDENTIALS = "restaurant.error.invalidCredentials";
	public static final String INVALID_PHONE = "restaurant.error.invalidPhone";
	public static final String INVALID_ADDRESS = "restaurant.error.invalidAddress";
	public static final String INVALID_ROLE = "restaurant.error.invalidRole";
	public static final String INVALID_DATE = "restaurant.error.invalidDate";
	public static final String INVALID_NEW_PASSWORD = "restaurant.error.invalidNewPassword";
	public static final String IVALID_CONFIRM_PASSWORD = "restaurant.error.invalidConfirmPassword";
	public static final String INVALID_NEW_CONFIRM_PASSWORD = "restaurant.error.invalidNewConfirmPassword";
	public static final String INVALID_CATEGORY = "restaurant.error.invalidCategory";
	public static final String INVALID_DESCRIPTION_INPUT = "restaurant.error.invalidDescription";
	public static final String INVALID_WEIGHT_INPUT = "restaurant.error.invalidWeight";
	public static final String INVALID_COST_INPUT = "restaurant.error.invalidCost";
	public static final String INVALID_DISHES_INPUT = "restaurant.error.invalidDishes";
	
	public static final String SUCCESS_USER_ADDITION = "restaurant.success.userSuccessfullyAdded";
	public static final String SUCCESS_USER_UPDATE = "restaurant.success.userSuccessfullyUpdated";
	public static final String SUCCESS_USER_DELETE = "restaurant.success.userSuccessfullyDeleted";
	public static final String SUCCESS_CATEGORY_ADDITION = "restaurant.success.categorySuccessfullyAdded";
	public static final String SUCCESS_CATEGORY_UPDATE = "restaurant.success.categorySuccessfullyUpdated";
	public static final String SUCCESS_CATEGORY_DELETE = "restaurant.success.categorySuccessfullyDeleted";
	public static final String SUCCESS_DISH_DELETE = "restaurant.success.dishSuccessfullyDeleted";
	public static final String SUCCESS_DISH_ADDITION = "restaurant.success.dishSuccessfullyAdded";
	public static final String SUCCESS_DISH_UPDATE = "restaurant.success.dishSuccessfullyUpdated";
	public static final String SUCCESS_ORDER_ADDITION = "restaurant.success.orderSuccessfullyAdded";
	public static final String SUCCESS_ORDER_UPDATE = "restaurant.success.orderSuccessfullyUpdated";
	public static final String SUCCESS_ORDER_DELETE = "restaurant.success.orderSuccessfullyDeleted";
}
