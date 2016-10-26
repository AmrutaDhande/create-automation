package synechron.step.exceptions;

public class STEPFrameworkInvalidIdentifierException extends Exception{
	private static final long serialVersionUID = 1L;

	public STEPFrameworkInvalidIdentifierException() {
		super("FrameworkInvalidIdentifierException: A generic Invalid Identifier exception occured.");
	}
	
	public STEPFrameworkInvalidIdentifierException(String identifier,String objectName) {
		super("FrameworkInvalidIdentifierException: Invalid Identifier "+identifier+" for the object "+objectName);
	}
}
