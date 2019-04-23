package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getBloodLoss() > 0) {
			target.setBloodLoss(target.getBloodLoss() - getTreatmentAmount());
			if (target.getBloodLoss() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getBloodLoss() == 0)

			heal();

	}
	@Override
	public void respond(Rescuable r) throws IncompatibleTargetException,CannotTreatException {
		
		if(!(r instanceof Citizen))
			throw new IncompatibleTargetException(this, r, "Ambulance Can Only Respond To Citizen Rescue Call");
		
		if(!canTreat(r))
			throw new CannotTreatException(this, r, "The citizen bloodloss is already equal to zero or dead");
		
		
		if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}

	@Override
	public boolean canTreat(Rescuable r) {
		Citizen c = (Citizen) r;
		
		if(c.getBloodLoss()<=0||c.getState()==CitizenState.DECEASED) {
			return false;
		}
		
		return true;
	}

	
	
	
}
