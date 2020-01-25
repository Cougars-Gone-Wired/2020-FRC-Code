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

        s.setDriveSpeedAxisState(controllers.getDriveSpeedAxis());
        s.setDriveTurnAxisState(controllers.getDriveTurnAxis());
        s.setSwitchSideState(controllers.isDriveSideToggle());
        s.setLimelightState(controllers.getLimelightButton());

        states.add(s);
    }

    public List<State> getStates() {
        return states;
    }
}
