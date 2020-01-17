package frc.robot;

import java.util.ArrayList;
import java.util.List;

public class StateRecorder {

	List<State> states;

	public void initialize() {
		states = new ArrayList<>();
	}

	public void record(Controllers controllers) {
		State s = new State();

		s.setDriveRightAxisState(controllers.getDriveForwardAxis());
		s.setDriveLeftAxisState(controllers.getDriveTurnAxis());

		states.add(s);
	}

	public List<State> getStates() {
		return states;
	}
}
