package synechron.step.exceptions;

public class STEPFrameworkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public STEPFrameworkException(String message) {
		 super(message);
	}
	
	public STEPFrameworkException() {
		super("A Generic Framework Exception occured.  That's all STEP knows about this.");
	}
}
