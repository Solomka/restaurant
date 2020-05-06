package ua.training.exception;

public class ServerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerException(String messageKey) {
		super(messageKey);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

	public ServerException(String messageKey, Throwable cause) {
		super(messageKey, cause);
	}
}
