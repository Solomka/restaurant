package ua.training.exception;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String messageKey) {
		super(messageKey);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String messageKey, Throwable cause) {
		super(messageKey, cause);
	}
}
