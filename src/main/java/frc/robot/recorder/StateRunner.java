package frc.robot.recorder;

import java.util.List;
import frc.robot.Robot; 

public class StateRunner {

    List<State> states;

    int counter = 0;

    public StateRunner() {
    }

    public void counterInitialize() {
        counter = 0;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public void run() {
        if (counter < states.size()) {
            State s = states.get(counter);

            Robot.drive.robotDrive(s.getDriveSpeedAxisState(), s.getDriveTurnAxisState(), s.getSwitchSideState());

            counter++;
        }
    }
}
