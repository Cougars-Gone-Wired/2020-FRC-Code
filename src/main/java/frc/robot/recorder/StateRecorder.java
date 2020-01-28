package frc.robot.recorder;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Controllers;

public class StateRecorder {

    List<State> states;

    public void initialize() {
        states = new ArrayList<>();
    }

    public void record(Controllers controllers) {
        State s = new State();

        s.setDriveSpeedAxisState(controllers.getDriveSpeedAxis());
        s.setDriveTurnAxisState(controllers.getDriveTurnAxis());
        s.setSwitchSideState(controllers.getDriveSideToggle());
        s.setLimelightState(controllers.getLimelightButton());

        states.add(s);
    }

    public List<State> getStates() {
        return states;
    }
}
