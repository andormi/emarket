package net.dorm.emarket.exception;

public class ResourceNotFoundException extends IllegalArgumentException {
	private static final long serialVersionUID = -8164500297206549410L;

	public ResourceNotFoundException(String s) {
		super(s);
	}
}
