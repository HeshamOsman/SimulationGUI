package exceptions;

public abstract class SimulationException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SimulationException() {
		
	}
	
    public SimulationException(String message) {
		super(message);
	}
	
	
}
