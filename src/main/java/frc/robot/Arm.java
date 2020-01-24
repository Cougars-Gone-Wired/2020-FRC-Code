package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.IntakeArm.IntakeArmStates;

public class Arm {

    private Solenoid smallPiston;
    private Solenoid bigPiston;

    public Arm() {
        smallPiston = new Solenoid(Constants.ARM_SOLENOID_1_PORT);
        bigPiston = new Solenoid(Constants.ARM_SOLENOID_2_PORT);
        initialize();
    }

    public void initialize() {
        setStartingPosition();
    }

    public enum ArmStates {
        STARTING_POSITION, SHOOTING_POSITION, CLIMBING_POSITION
    }

    private ArmStates currentArmState;

    public void pistonArm(boolean upButton, boolean downButton) {
        switch (currentArmState) {
        case STARTING_POSITION:
            if (downButton && !upButton) {
                setShootingPostion();
            } else if (!downButton && upButton && (Robot.intakeArm.getCurrentIntakeArmState() != IntakeArmStates.UP)) {
                setClimbingPostion();
            }
            break;

        case SHOOTING_POSITION:
            if (!downButton && upButton) {
                setStartingPosition();
            }
            break;

        case CLIMBING_POSITION:
            if (downButton && !upButton) {
                setStartingPosition();
            }
            break;
        }
    }

    public ArmStates getCurrentArmState() {
        return currentArmState;
    }

    public void setStartingPosition() {
        smallPiston.set(false);
        bigPiston.set(true);
        currentArmState = ArmStates.STARTING_POSITION;
    }

    public void setShootingPostion() {
        smallPiston.set(false);
        bigPiston.set(false);
        currentArmState = ArmStates.SHOOTING_POSITION;
    }

    public void setClimbingPostion() {
        smallPiston.set(true);
        bigPiston.set(true);
        currentArmState = ArmStates.CLIMBING_POSITION;
    }
}