package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Arm {

    private Solenoid solenoid1;
    private Solenoid solenoid2;
    
    public Arm() {
        solenoid1 = new Solenoid(Constants.ARM_SOLENOID_1_PORT);
        solenoid2 = new Solenoid(Constants.ARM_SOLENOID_2_PORT);
        initialize();
    }

    public void initialize() {
        solenoid1.set(false);
        solenoid2.set(true);
        currentPistonState = PistonStates.STARTING_POSITION;
    }

    private enum PistonStates {
        STARTING_POSITION, SHOOTING_POSITION, CLIMBING_POSITION 
    }

    private PistonStates currentPistonState;

    public void pistonArm(boolean armToggle, boolean climberPosButton) {
        switch (currentPistonState) {
            case STARTING_POSITION:
                if (armToggle && !climberPosButton) {
                    solenoid1.set(false);
                    solenoid2.set(false);
                    currentPistonState = PistonStates.SHOOTING_POSITION;
                } else if (!armToggle && climberPosButton) {
                    solenoid1.set(true);
                    solenoid2.set(true);
                    currentPistonState = PistonStates.CLIMBING_POSITION;
                }
                break;

            case SHOOTING_POSITION:
                if (!armToggle && !climberPosButton) {
                    solenoid1.set(false);
                    solenoid2.set(true);
                    currentPistonState = PistonStates.STARTING_POSITION;
                } else if (armToggle && climberPosButton) {
                    solenoid1.set(true);
                    solenoid2.set(true);
                    currentPistonState = PistonStates.CLIMBING_POSITION;
                }
                break;

            case CLIMBING_POSITION:
                if (!armToggle && !climberPosButton) {
                    solenoid1.set(false);
                    solenoid2.set(true);
                    currentPistonState = PistonStates.STARTING_POSITION;
                } else if (armToggle && !climberPosButton) {
                    solenoid1.set(false);
                    solenoid2.set(false);
                    currentPistonState = PistonStates.SHOOTING_POSITION;
                }
                break;
        }
    }
}