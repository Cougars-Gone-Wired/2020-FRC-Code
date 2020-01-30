package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Arms {

    private Solenoid smallSolenoid;
    private Solenoid bigSolenoid;
    private Solenoid intakeArmSolenoid;

    private boolean triggerDown = false;

    public Arms() {
        smallSolenoid = new Solenoid(Constants.ARM_SMALL_SOLENOID_PORT);
        bigSolenoid = new Solenoid(Constants.ARM_BIG_SOLENOID_PORT);
        intakeArmSolenoid = new Solenoid(Constants.INTAKE_SOLENOID_PORT);
        initialize();
    }

    public void initialize() {
        setStartingPosition();
        setUpPosition();
    }

    public enum ShooterArmStates {
        STARTING_POSITION, SHOOTING_POSITION, CLIMBING_POSITION
    }

    private ShooterArmStates currentShooterArmState;

    public void shooterArm(boolean upButton, boolean downButton) {
        switch (currentShooterArmState) {
            case STARTING_POSITION:
                if (downButton && !upButton) {
                    setShootingPostion();
                } else if (!downButton && upButton && (currentIntakeArmState != IntakeArmStates.UP)) {
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

    public boolean isShooterStartingPosition() {
        return currentShooterArmState == ShooterArmStates.STARTING_POSITION;
    }

    public void setStartingPosition() {
        smallSolenoid.set(false);
        bigSolenoid.set(true);
        currentShooterArmState = ShooterArmStates.STARTING_POSITION;
    }

    public boolean isShooterShootingPosition() {
        return currentShooterArmState == ShooterArmStates.SHOOTING_POSITION;
    }

    public void setShootingPostion() {
        smallSolenoid.set(false);
        bigSolenoid.set(false);
        currentShooterArmState = ShooterArmStates.SHOOTING_POSITION;
    }

    public boolean isShooterClimbingPosition() {
        return currentShooterArmState == ShooterArmStates.CLIMBING_POSITION;
    }

    public void setClimbingPostion() {
        smallSolenoid.set(true);
        bigSolenoid.set(true);
        currentShooterArmState = ShooterArmStates.CLIMBING_POSITION;
    }
    

    public enum IntakeArmStates {
        UP, DOWN
    }

    private IntakeArmStates currentIntakeArmState;

    public void intakeArm(double intakeArmAxis) {
        switch(currentIntakeArmState) {
            case DOWN:
                if (toggle(intakeArmAxis) && (currentShooterArmState != ShooterArmStates.CLIMBING_POSITION)) {
                    setUpPosition();
                }
                break;

            case UP:
                if (toggle(intakeArmAxis)) {
                    setDownPosition();
                }
                break;
        }
    }

    public boolean isIntakeUpPositon() {
        return currentIntakeArmState == IntakeArmStates.UP;
    }

    public void setUpPosition() {
        intakeArmSolenoid.set(false);
        currentIntakeArmState = IntakeArmStates.UP;
    }

    public boolean isIntakeDownPositon() {
        return currentIntakeArmState == IntakeArmStates.DOWN;
    }

    public void setDownPosition() {
        intakeArmSolenoid.set(true);
        currentIntakeArmState = IntakeArmStates.DOWN;    
    }

    public boolean toggle(double intakeArmAxis) {
        if(intakeArmAxis > Constants.DEADZONE) {
            if(!triggerDown) {
                triggerDown = true;
                return true;
            }
        } else {
            triggerDown = false;
        }
        return false;
    }
}