package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public class Arms {

    // private Solenoid smallSolenoid;
    // private Solenoid bigSolenoid;
    private DoubleSolenoid smallSolenoid;
    private DoubleSolenoid bigSolenoid;
    // private Solenoid intakeArmSolenoid;
    private DoubleSolenoid intakeArmSolenoid;

    private boolean triggerDown = false;

    public Arms() {
        // smallSolenoid = new Solenoid(Constants.ARM_SMALL_SOLENOID_PORT);
        // bigSolenoid = new Solenoid(Constants.ARM_BIG_SOLENOID_PORT);
        smallSolenoid = new DoubleSolenoid(Constants.ARM_SMALL_SOLENOID_PORT_1, Constants.ARM_SMALL_SOLENOID_PORT_2);
        bigSolenoid = new DoubleSolenoid(Constants.ARM_BIG_SOLENOID_PORT_1, Constants.ARM_BIG_SOLENOID_PORT_2);
        // intakeArmSolenoid = new Solenoid(Constants.INTAKE_SOLENOID_PORT);
        intakeArmSolenoid = new DoubleSolenoid(Constants.INTAKE_SOLENOID_PORT_1, Constants.INTAKE_SOLENOID_PORT_2);
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

    public void shooterArm(boolean moveUpButton, boolean moveDownButton) {
        switch (currentShooterArmState) {
            case STARTING_POSITION:
                if (moveDownButton && !moveUpButton) {
                    setShootingPostion();
                } else if (!moveDownButton && moveUpButton && (currentIntakeArmState != IntakeArmStates.UP)) {
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

    public boolean isShooterStartingPosition() {
        return currentShooterArmState == ShooterArmStates.STARTING_POSITION;
    }

    public boolean isShooterShootingPosition() {
        return currentShooterArmState == ShooterArmStates.SHOOTING_POSITION;
    }

    public boolean isShooterClimbingPosition() {
        return currentShooterArmState == ShooterArmStates.CLIMBING_POSITION;
    }

    public void setStartingPosition() {
        // smallSolenoid.set(false);
        // bigSolenoid.set(true);
        smallSolenoid.set(DoubleSolenoid.Value.kReverse);
        bigSolenoid.set(DoubleSolenoid.Value.kForward);
        currentShooterArmState = ShooterArmStates.STARTING_POSITION;
    }

    public void setShootingPostion() {
        // smallSolenoid.set(false);
        // bigSolenoid.set(false);
        smallSolenoid.set(DoubleSolenoid.Value.kReverse);
        bigSolenoid.set(DoubleSolenoid.Value.kReverse);
        currentShooterArmState = ShooterArmStates.SHOOTING_POSITION;
    }

    public void setClimbingPostion() {
        // smallSolenoid.set(true);
        // bigSolenoid.set(true);
        smallSolenoid.set(DoubleSolenoid.Value.kForward);
        bigSolenoid.set(DoubleSolenoid.Value.kForward);
        currentShooterArmState = ShooterArmStates.CLIMBING_POSITION;
    }
    

    public enum IntakeArmStates {
        UP, DOWN
    }

    private IntakeArmStates currentIntakeArmState;

    public void intakeArm(double intakeArmTrigger) {
        switch(currentIntakeArmState) {
            case DOWN:
                if (toggle(intakeArmTrigger) && (currentShooterArmState != ShooterArmStates.CLIMBING_POSITION)) {
                    setUpPosition();
                }
                break;

            case UP:
                if (toggle(intakeArmTrigger)) {
                    setDownPosition();
                }
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
        //intakeArmSolenoid.set(false);
        intakeArmSolenoid.set(DoubleSolenoid.Value.kReverse);
        currentIntakeArmState = IntakeArmStates.UP;
    }

    public void setDownPosition() {
        //intakeArmSolenoid.set(true);
        intakeArmSolenoid.set(DoubleSolenoid.Value.kForward);
        currentIntakeArmState = IntakeArmStates.DOWN;    
    }

    public boolean toggle(double intakeArmTrigger) {
        if(intakeArmTrigger > Constants.DEADZONE) {
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