package net.dorm.emarket.exception;

public class ValidationException extends IllegalArgumentException {
	private static final long serialVersionUID = 1602124150417767995L;

	public ValidationException(String s) {
		super(s);
	}
}
