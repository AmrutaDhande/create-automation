package synechron.step.exceptions;

public class STEPFrameworkLocationInvalidException extends STEPFrameworkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public STEPFrameworkLocationInvalidException() {
		super("Framework Location provided is invalid");
	}
	
	public STEPFrameworkLocationInvalidException(String message) {
		super(message);
	}
}
