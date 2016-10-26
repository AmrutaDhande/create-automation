package synechron.step.exceptions;

public class STEPFrameworkControllerException extends STEPFrameworkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public STEPFrameworkControllerException() {
		super("FrameworkControllerException: Generic Controller Exception occured.");
	}
	
	public STEPFrameworkControllerException(String message) {
		super(message);
	}
}
