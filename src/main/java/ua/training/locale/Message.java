package ua.training.locale;

public final class Message {	
	
	private Message() {
	}

	public static String ROLE_CHIEF = "restaurant.user.role.chief";
	public static String ROLE_MANAGER = "restaurant.user.role.manager";
	public static String ROLE_WAITER = "restaurant.user.role.waiter";
	public static String ROLE_CLIENT = "restaurant.user.role.client";
	public static String ROLE_COURIER = "restaurant.user.role.courier";
	
	
	public static String LOGGED_IN_AS = "library.loggedIn";
	public static String PAGE_NOT_FOUND_ERROR = "library.error.pageNotFoundError";
	public static String SERVER_ERROR = "library.error.serverError";
	public static String DIRECT_VIEW_ACCESS_ERROR = "library.error.directViewAccessError";
	public static String UNAUTHORIZED_ACCESS_ERROR = "library.error.authorizedAccessError";
	public static String SUCCESS_BOOK_ADDITION = "library.success.addBook";
	public static String BOOK_IS_NOT_FOUND = "library.error.bookIsNotFound";
	public static String READER_IS_NOT_FOUND = "library.error.readerIsNotFound";
	public static String BOOK_INSTANCE_IS_NOT_FOUND = "library.error.bookInstanceIsNotFound";
	public static String NO_AVAILABLE_BOOK_INSTANCES = "library.error.noAvailableBookIntstances";
	public static String BOOK_ORDER_IS_NOT_FOUND = "library.error.bookOrderIsNorFound";
	public static String ORDERS_ARE_NOT_FOUND = "library.error.ordersAreNotFound";
	public static String BOOK_INSTANCES_MAX_NUMBER_ORDER_CREATION_RESTRICTION = "library.error.bookInstancesMaxNumOrderCreationRestr";
	public static String SAME_BOOK_INSTANCES_ORDER_CREATION_RESTRICTION = "library.error.sameBookInstancesOrderCreationRestr";
	public static String BOOK_INSTANCE_IS_ALREADY_ORDERED = "library.error.bookInstanceIsAlreadyOrdered";
	
	public static String SUCCESS_AUTHOR_ADDITION = "library.success.addAuthor";
	public static String SUCCESS_READER_ADDITION = "library.success.addReader";
	public static String SUCCESS_BOOK_INSTANCE_ADDITION = "library.success.addBookInstance";
	public static String SUCCESS_PASSWORD_CHANGE = "library.success.changePassword";
	public static String SUCCESS_ORDER_CREATION = "library.success.orderCreation";
	public static String SUCCESS_ORDER_FULFILMENT = "library.success.orderFulfilment";
	public static String SUCCESS_ORDER_ISSUANCE = "library.success.orderIssuance";
	public static String SUCCESS_ORDER_RETURN = "library.success.orderReturn";
	public static String SUCCESS_RETURN_ORDER_TO_READING_ROOM = "library.success.orderReturnToReadingRoom";
	
	public static String INVALID_NAME_INPUT = "library.error.invalidName";
	public static String INVALID_SURNAME_INPUT = "library.error.invalidSurname";
	public static String INVALID_PATRONYMIC_INPUT = "library.error.invalidPatronymic";
	public static String INVALID_PUBLISHER_INPUT = "library.error.invalidPublisher";
	public static String INVALID_COUNTRY_INPUT = "library.error.invalidCountry";

	public static String INVALID_EMAIL = "library.error.invalidEmail";
	public static String INVALID_PASS = "library.error.invalidPass";
	public static String INVALID_CREDENTIALS = "library.error.invalidCredentials";

	public static String INVALID_ISBN = "library.error.invalidISBN";
	public static String INVALID_TITLE = "library.error.invalidTitle";
	public static String INVALID_BOOK_AUTHORS_SELECTION = "library.error.invalidBookAuthorsSelection";

	public static String INVALID_INVENTORY_NUMBER = "library.error.invalidIInventoryNumber";

	public static String INVALID_PHONE = "library.error.invalidPhone";
	public static String INVALID_ADDRESS = "library.error.invalidAddress";
	public static String INVALID_READER_CARD_NUMBER = "library.error.invalidReaderCardNumber";
	
	public static String INVALID_OLD_PASSWORD = "library.error.invalidOldPassword";
	public static String INVALID_OLD_DB_USER_PASSWORD = "library.error.invalidOldDbUserPassword";
	public static String INVALID_NEW_PASSWORD = "library.error.invalidNewPassword";
	public static String IVALID_CONFIRM_PASSWORD = "library.error.invalidConfirmPassword";
	public static String INVALID_NEW_CONFIRM_PASSWORD = "library.error.invalidNewConfirmPassword";
	
	public static String ORDER_ALREADY_FULFILLED = "library.error.orderAlreadyFulfilled";
	public static String ORDER_IS_NOT_FULFILLED = "library.error.orderIsNotFulfilled";
	public static String ORDER_RETURN_IS_NOT_FULFILLED = "library.error.orderReturnIsNotFulfilled";
	public static String BOOK_ALREADY_ISSUED = "library.error.bookAlreadyIssued";
	public static String BOOK_IS_NOT_ISSUED = "library.error.bookIsNotIssued";
	public static String BOOK_ALREADY_RETURNED = "library.error.bookAlreadyReturned";
	
	public static String AVAILABILITY_READING_ROOM = "library.book.availability.readingRoom";
	public static String AVAILABILITY_SUBSCRIPTION = "library.book.availability.subscription";

	
	public static String STATUS_AVAILABLE = "library.bookInstance.status.available";
	public static String STATUS_UNAVAILABLE = "library.bookInstance.status.unavailable";
	
	public static String ROLE_READER = "library.user.role.reader";
	public static String ROLE_LIBRARIAN = "library.user.role.librarian";		
}
