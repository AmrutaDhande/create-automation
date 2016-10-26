package synechron.step.exceptions;

public class STEPFrameworkExecutorException extends STEPFrameworkException {
	private static final long serialVersionUID = 1L;

	public STEPFrameworkExecutorException() {
		super("FrameworkExecutorException: A Generic Framework Executor Exception occured.");
	}
	
	public STEPFrameworkExecutorException(String message) {
		super(message);
	}
}
