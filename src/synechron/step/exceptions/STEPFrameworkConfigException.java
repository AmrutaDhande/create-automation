package synechron.step.exceptions;

public class STEPFrameworkConfigException extends STEPFrameworkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public STEPFrameworkConfigException() {
		super("FrameworkConfigException: A generic configuration exception occured.");
	}
	
	public STEPFrameworkConfigException(String message) {
		super(message);
	}
}
