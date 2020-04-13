package net.dorm.emarket.exception;

public class AccessDeniedException extends IllegalArgumentException {
	private static final long serialVersionUID = 4185828137065296198L;

	public AccessDeniedException(String s) {
		super(s);
	}
}
