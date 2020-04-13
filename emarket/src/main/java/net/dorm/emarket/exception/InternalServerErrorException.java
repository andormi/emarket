package net.dorm.emarket.exception;

public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = -2127308768102706320L;

	public InternalServerErrorException(String message) {
		super(message);
	}

	public InternalServerErrorException(Throwable cause) {
		super(cause);
	}

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

}
