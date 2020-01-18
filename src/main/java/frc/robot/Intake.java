package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
    private static final double INTAKE_SPEED = 1;
    private static final double PIVOT_SPEED = 1;
    private WPI_TalonSRX intakeMotor;
    private WPI_TalonSRX pivotMotor;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(0);
        currentIntakeState = IntakeStates.NOT_MOVING;
        pivotMotor = new WPI_TalonSRX(0);
        currentPivotState = PivotStates.NOT_MOVING;
    }

    public void initialize() {
        intakeMotor.set(0);
        pivotMotor.set(0);
        currentIntakeState = IntakeStates.NOT_MOVING;
        currentPivotState = PivotStates.NOT_MOVING;
    }

    public enum PivotStates {
        NOT_MOVING, PIVOTING_UP, PIVOTING_DOWN
    }

    private PivotStates currentPivotState;

    public void pivotAxis(double pivotAxis) {
        switch(currentPivotState) {
            case NOT_MOVING:
                if (pivotAxis > 0.15) {
                    pivotMotor.set(PIVOT_SPEED);
                    currentPivotState = PivotStates.PIVOTING_UP;
                } else if (pivotAxis < -0.15) {
                    pivotMotor.set(-PIVOT_SPEED);
                    currentPivotState = PivotStates.PIVOTING_DOWN;
                }
                break;

            case PIVOTING_UP:
                if (pivotAxis <= 0.15) {
                    pivotMotor.set(0);
                    currentPivotState = PivotStates.NOT_MOVING;
                }
                break; 

            case PIVOTING_DOWN:
                if (pivotAxis >= -0.15) {
                    pivotMotor.set(0);
                    currentPivotState = PivotStates.NOT_MOVING;
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