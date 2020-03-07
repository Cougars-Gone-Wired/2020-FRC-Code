package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arms {
    static final int ARM_STATE_DELAY = 2; // in seconds

    private DoubleSolenoid armTopSolenoid;
    private DoubleSolenoid armBottomSolenoid;
    private DoubleSolenoid intakeArmSolenoid;

    private boolean triggerDownBool;

    public Arms() {
        armTopSolenoid = new DoubleSolenoid(Constants.ARM_TOP_SOLENOID_PORT_1, Constants.ARM_TOP_SOLENOID_PORT_2);
        armBottomSolenoid = new DoubleSolenoid(Constants.ARM_BOTTOM_SOLENOID_PORT_1, Constants.ARM_BOTTOM_SOLENOID_PORT_2);
        intakeArmSolenoid = new DoubleSolenoid(Constants.INTAKE_ARM_SOLENOID_PORT_1, Constants.INTAKE_ARM_SOLENOID_PORT_2);
        initialize();
    }

    public void initialize() {
        triggerDownBool = false;
        // setStartingPosition();
        // setUpPosition();
        setShootingPostion();
        setDownPosition();
    }

    public enum ArmStates {
        STARTING_POSITION, SHOOTING_POSITION, CLIMBING_POSITION
    }

    private ArmStates currentArmState;

    public void controlArm(boolean moveUpButton, boolean moveDownButton) {

        switch (currentArmState) {
            case STARTING_POSITION:
                if (moveDownButton && !moveUpButton) {
                    setShootingPostion();
                } else if (!moveDownButton && moveUpButton) {
                    setClimbingPostion();
                }
                break;

            case SHOOTING_POSITION:
                if (!moveDownButton && moveUpButton) {
                    setStartingPosition();
                }
                break;

            case CLIMBING_POSITION:
                if (moveDownButton && !moveUpButton) {
                    setStartingPosition();
                }
                break;
        }
    }

    public boolean isArmStartingPosition() {
        return currentArmState == ArmStates.STARTING_POSITION;
    }

    public boolean isArmShootingPosition() {
        return currentArmState == ArmStates.SHOOTING_POSITION;
    }

    public boolean isArmClimbingPosition() {
        return currentArmState == ArmStates.CLIMBING_POSITION;
    }

    public void setStartingPosition() {
        armTopSolenoid.set(DoubleSolenoid.Value.kReverse);
        armBottomSolenoid.set(DoubleSolenoid.Value.kForward);
        currentArmState = ArmStates.STARTING_POSITION;
    }

    public void setShootingPostion() {
        armTopSolenoid.set(DoubleSolenoid.Value.kReverse);
        armBottomSolenoid.set(DoubleSolenoid.Value.kReverse);
        currentArmState = ArmStates.SHOOTING_POSITION;
    }

    public void setClimbingPostion() {
        armTopSolenoid.set(DoubleSolenoid.Value.kForward);
        armBottomSolenoid.set(DoubleSolenoid.Value.kForward);
        currentArmState = ArmStates.CLIMBING_POSITION;
    }
    

    public enum IntakeArmStates {
        UP, DOWN
    }

    private IntakeArmStates currentIntakeArmState;

    public void controlIntakeArm(boolean intakeArmDownBumper, boolean intakeArmUpBumper) {

        switch (currentIntakeArmState) {
            case DOWN:
                if (!intakeArmDownBumper && intakeArmUpBumper) {
                    setUpPosition();
                }
                break;

            case UP:
                if (intakeArmDownBumper && !intakeArmUpBumper) {
                    setDownPosition();
                    // intakeArmSwitchingStates = true;
                }
                // if (intakeArmSwitchingStates) {
                // setDownPositionWithDelay();
                // }
                break;
        }
    }

    public boolean isIntakeUpPositon() {
        return currentIntakeArmState == IntakeArmStates.UP;
    }

    public boolean isIntakeDownPositon() {
        return currentIntakeArmState == IntakeArmStates.DOWN;
    }

    public void setUpPosition() {
        intakeArmSolenoid.set(DoubleSolenoid.Value.kReverse);
        currentIntakeArmState = IntakeArmStates.UP;
    }

    public void setDownPosition() {
        intakeArmSolenoid.set(DoubleSolenoid.Value.kForward);
        currentIntakeArmState = IntakeArmStates.DOWN;
    }

    public boolean toggleTrigger(double intakeArmTrigger) {
        if(intakeArmTrigger > Constants.DEADZONE) {
            if(!triggerDownBool) {
                triggerDownBool = true;
                return true;
            }
        } else {
            triggerDownBool = false;
        }
        return false;
    }
}