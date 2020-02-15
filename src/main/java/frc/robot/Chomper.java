package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Chomper {
    private Solenoid chompSolenoid;

    public Chomper() {
        chompSolenoid = new Solenoid(Constants.CHOMPER_SOLENOID_PORT);
        initialize();
    }

    public void initialize() {
        setChompUp();
        // setChompDown();
        // setHardStopDown();
    }

    public enum ChompStates {
        UP, DOWN
    }

    private ChompStates currentChompState;

    public void controlChomper(boolean chompSolenoidUpButton, boolean chompSolenoidDownButton) {
    //public void controlChomp(boolean chompSolenoidUpButton, boolean chompSolenoidDownButton, boolean hardStopSolenoidUpButton, boolean hardStopSolenoidDownButton) {
        switch(currentChompState) {
            case UP:
                if(chompSolenoidDownButton) {
                    setChompDown();
                }
            break;

            case DOWN:
                if(chompSolenoidUpButton) {
                    setChompUp();
                }
            break;
        }
    }

    public boolean isChompUp() {
        return currentChompState == ChompStates.UP;
    }

    public boolean isChompDown() {
        return currentChompState == ChompStates.DOWN;
    }

    public void setChompUp() {
        currentChompState = ChompStates.UP;
        chompSolenoid.set(true);
    }

    public void setChompDown() {
        currentChompState = ChompStates.DOWN;
        chompSolenoid.set(false);
    }
}