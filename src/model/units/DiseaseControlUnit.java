package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class DiseaseControlUnit extends MedicalUnit {

	public DiseaseControlUnit(String unitID, Address location,
			int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);
		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getToxicity() > 0) {
			target.setToxicity(target.getToxicity() - getTreatmentAmount());
			if (target.getToxicity() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getToxicity() == 0)
			heal();

	}

	@Override
	public void respond(Rescuable r) throws IncompatibleTargetException,CannotTreatException {
		
		if(!(r instanceof Citizen))
			throw new IncompatibleTargetException(this, r, "Disease Control Unit Can Only Respond To Citizen Rescue Call");
		if(!canTreat(r))
			throw new CannotTreatException(this, r, "The citizen toxicity is already equal to zero or dead");
		
		
		if (getTarget() != null && ((Citizen) getTarget()).getToxicity() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}
	
	@Override
	public boolean canTreat(Rescuable r) {
		Citizen c = (Citizen) r;
		
		if(c.getToxicity()<=0||c.getState()==CitizenState.DECEASED) {
			return false;
		}
		
		return true;
	}

}
