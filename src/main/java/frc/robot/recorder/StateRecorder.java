package frc.robot.recorder;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Robot;

public class StateRecorder {

    List<State> states;

    public void initialize() {
        states = new ArrayList<>();
    }

    public void record() {
        State s = new State();

        s.setDriveSpeedAxisState(Robot.controllers.getDriveSpeedAxis());
        s.setDriveTurnAxisState(Robot.controllers.getDriveTurnAxis());
        s.setSwitchSideState(Robot.controllers.isDriveSideToggle());
        s.setLimelightState(Robot.controllers.isLimelightButton());

        states.add(s);
    }

    public List<State> getStates() {
        return states;
    }
}
