package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Arm.ArmStates;

public class IntakeArm {

    private Solenoid intakeArmSolenoid;

    public IntakeArm() {
        intakeArmSolenoid = new Solenoid(Constants.INTAKE_SOLENOID_PORT);
        initialize();
    }

    public void initialize() {
        intakeArmSolenoid.set(false);
        currentIntakeArmState = IntakeArmStates.UP;
    }

    public enum IntakeArmStates {
        UP, DOWN
    }

    private IntakeArmStates currentIntakeArmState;

    public void intakeArm(boolean intakePosToggle) {
        switch(currentIntakeArmState) {
            case DOWN:
                if (!intakePosToggle && (Robot.arm.getCurrentArmState() != ArmStates.CLIMBING_POSITION)) {
                    setUpPosition();
                }
                break;

            case UP:
                if (intakePosToggle) {
                    setDownPosition();
                }
                break;
        }
    }

    public IntakeArmStates getCurrentIntakeArmState() {
        return currentIntakeArmState;
    }

    public void setUpPosition() {
        intakeArmSolenoid.set(false);
        currentIntakeArmState = IntakeArmStates.UP;
    }

    public void setDownPosition() {
        intakeArmSolenoid.set(true);
        currentIntakeArmState = IntakeArmStates.DOWN;    
    }
}