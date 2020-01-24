package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

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
            case UP:
                if (intakePosToggle) {
                    intakeArmSolenoid.set(true);
                    currentIntakeArmState = IntakeArmStates.DOWN;
                }
                break;

            case DOWN:
                if (!intakePosToggle) {
                    intakeArmSolenoid.set(false);
                    currentIntakeArmState = IntakeArmStates.UP;
                }
                break;
        }
    }
}