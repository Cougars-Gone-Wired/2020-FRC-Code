package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arms {
    static final int ARM_STATE_DELAY = 2; // in seconds

    private DoubleSolenoid armTopSolenoid;
    private DoubleSolenoid armBottomSolenoid;
    private DoubleSolenoid intakeArmSolenoid;

    private int armStateCounts;
    private int intakeArmStateCounts;
    private boolean triggerDownBool;
    private boolean toggleState;
    private boolean armSwitchingStates;
    private boolean intakeArmSwitchingStates;

    public Arms() {
        armTopSolenoid = new DoubleSolenoid(Constants.ARM_TOP_SOLENOID_PORT_1, Constants.ARM_TOP_SOLENOID_PORT_2);
        armBottomSolenoid = new DoubleSolenoid(Constants.ARM_BOTTOM_SOLENOID_PORT_1, Constants.ARM_BOTTOM_SOLENOID_PORT_2);
        intakeArmSolenoid = new DoubleSolenoid(Constants.INTAKE_ARM_SOLENOID_PORT_1, Constants.INTAKE_ARM_SOLENOID_PORT_2);
        initialize();
    }

    public void initialize() {
        armStateCounts = 0;
        intakeArmStateCounts = 0;
        triggerDownBool = false;
        toggleState = false;
        armSwitchingStates = false;
        intakeArmSwitchingStates = false;
        setStartingPosition();
        setUpPosition();
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
                } else if (!moveDownButton && moveUpButton && currentIntakeArmState != IntakeArmStates.UP) {
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
                    // armSwitchingStates = true;
                }
                // if (armSwitchingStates) {
                //     setStartingPositionWithDelay();
                // }
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
        armBottomSolenoid.set(DoubleSolenoid.Value.kReverse);
        currentArmState = ArmStates.STARTING_POSITION;
    }

    public void setStartingPositionWithDelay() {
        armTopSolenoid.set(DoubleSolenoid.Value.kReverse);
        armBottomSolenoid.set(DoubleSolenoid.Value.kReverse);
        // if (armStateCounts / 500 >= ARM_STATE_DELAY) {
        //     armStateCounts = 0;
            // armSwitchingStates = false;
            currentArmState = ArmStates.STARTING_POSITION;
        // }
        // armStateCounts++;
    }

    public void setShootingPostion() {
        armTopSolenoid.set(DoubleSolenoid.Value.kForward);
        armBottomSolenoid.set(DoubleSolenoid.Value.kReverse);
        currentArmState = ArmStates.SHOOTING_POSITION;
    }

    public void setClimbingPostion() {
        armTopSolenoid.set(DoubleSolenoid.Value.kReverse);
        armBottomSolenoid.set(DoubleSolenoid.Value.kForward);
        currentArmState = ArmStates.CLIMBING_POSITION;
    }

    public enum IntakeArmStates {
        UP, DOWN
    }

    private IntakeArmStates currentIntakeArmState;

    public void controlIntakeArm(double intakeArmTrigger) {

        switch(currentIntakeArmState) {
            case DOWN:
                if (!toggleTrigger(intakeArmTrigger) && (currentArmState != ArmStates.CLIMBING_POSITION)) {
                    setUpPosition();
                }
                break;

            case UP:
                if (toggleTrigger(intakeArmTrigger)) {
                    setDownPosition();
                    //intakeArmSwitchingStates = true;
                }
                // if (intakeArmSwitchingStates) {
                //     setDownPositionWithDelay();
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

    public void setDownPositionWithDelay() {
        intakeArmSolenoid.set(DoubleSolenoid.Value.kForward);
        // if (intakeArmStateCounts / 500 >= ARM_STATE_DELAY) {
        //     intakeArmStateCounts = 0;
            // intakeArmSwitchingStates = false;
            currentIntakeArmState = IntakeArmStates.DOWN;
        // }
        // intakeArmStateCounts++;
    }

    public boolean toggleTrigger(double intakeArmTrigger) {
        if (intakeArmTrigger > Constants.DEADZONE) {
            if (!triggerDownBool) {
                toggleState = !toggleState;
            }
            triggerDownBool = true;
        } else {
            triggerDownBool = false;
        }
        return toggleState;
    }
}