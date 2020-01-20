package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
    private static final double INTAKE_SPEED = 1;
    
    private WPI_TalonSRX intakeMotor;
    private Solenoid intakeArmSolenoid;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(0);
        currentIntakeState = IntakeStates.NOT_MOVING;
        intakeArmSolenoid = new Solenoid(0);
        currentIntakeArmState = IntakeArmStates.UP;
    }

    public void initialize() {
        intakeMotor.set(0);
        intakeArmSolenoid.set(false);
        currentIntakeState = IntakeStates.NOT_MOVING;
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

    public enum IntakeStates {
        NOT_MOVING, INTAKING
    }

    private IntakeStates currentIntakeState;

    public void intake(boolean intakeButton) {
        switch(currentIntakeState) {
            case NOT_MOVING:
                if (intakeButton) {
                    intakeMotor.set(INTAKE_SPEED);
                    currentIntakeState = IntakeStates.INTAKING;
                }
                break;

            case INTAKING:
                if (!intakeButton) {
                    intakeMotor.set(0);
                    currentIntakeState = IntakeStates.NOT_MOVING;
                }
                break;
        }
    }
}