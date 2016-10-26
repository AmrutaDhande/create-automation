package synechron.step.exceptions;


public class STEPFrameworkMissingORObjectException extends Exception{
	private static final long serialVersionUID = 1L;

	public STEPFrameworkMissingORObjectException() {
		super("FrameworkInvalidIdentifierException: A generic Invalid Identifier exception occured.");
	}
	
	public STEPFrameworkMissingORObjectException(String objectName) {
		super("FrameworkMissingORObject: Object  "+objectName+" is missing in the object Repository");
	}
}