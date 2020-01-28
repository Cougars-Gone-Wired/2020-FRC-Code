package frc.robot.recorder;

import java.util.List;
import frc.robot.Robot;
import frc.robot.Drive;

public class StateRunner {

    private Drive drive;

    List<State> states;

    int counter = 0;

    public StateRunner(Robot robot) {
        this.drive = robot.getDrive();
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

            drive.robotDrive(s.getDriveSpeedAxisState(), s.getDriveTurnAxisState(), s.getSwitchSideState());

            counter++;
        }
    }
}
