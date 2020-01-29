package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Chomper {
    private Solenoid chompSolenoid;
    private Solenoid hardStopSolenoid;
    private boolean chomperBool;

    public Chomper() {
        chompSolenoid = new Solenoid(Constants.CHOMPER_SOLENOID_PORT);
        hardStopSolenoid = new Solenoid(Constants.HARD_STOP_SOLENOID_PORT);
        initialize();
    }

    public void initialize() {
        setChomp();
    }

    public enum ChompStates {
        CHOMPING, NOT_CHOMPING
    }

    private ChompStates currentChompState;

    public void engageShoot(double chompAxis) {
        chomperBool = (chompAxis >= Constants.DEADZONE);

        switch(currentChompState) {
            case NOT_CHOMPING:
                if (!chomperBool) {
                    setChomp();
                }
                break;

            case CHOMPING:
                if (chomperBool) {
                    setNotChomping();
                }
                break;
        }
    }

    public ChompStates getCurrentChompState() {
        return currentChompState;
    }

    public void setChomp() {
        chompSolenoid.set(true);
        hardStopSolenoid.set(false);
        currentChompState = ChompStates.CHOMPING;
    }

    public void setNotChomping() {
        chompSolenoid.set(false);
        hardStopSolenoid.set(true);
        currentChompState = ChompStates.NOT_CHOMPING;
    }
}