package exceptions;

import model.disasters.Disaster;

public abstract class DisasterException extends SimulationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Disaster disaster;
	
	public DisasterException(Disaster disaster) {
		this.disaster = disaster; 
	}
	
	public DisasterException(Disaster disaster,String message) {
		super(message);
		this.disaster = disaster;
	}

	public Disaster getDisaster() {
		return disaster;
	}
	
	
}
