package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Arms.IntakeArmStates;
import frc.robot.Arms.ShooterArmStates;

public class Intake {
    private static final double INTAKE_SPEED = 0.8;
    
    private WPI_TalonSRX intakeMotor;


    public Intake() {
        intakeMotor = new WPI_TalonSRX(Constants.INTAKE_MOTOR_ID);
        initialize();
    }

    public void initialize() {
        setNotMoving();
    }

    public enum IntakeStates {
        NOT_MOVING, INTAKING, OUTTAKING
    }

    private IntakeStates currentIntakeState;

    public void intake(double intakeAxis) {
        switch(currentIntakeState) {
            case NOT_MOVING:
                if (intakeAxis >= Constants.DEADZONE
                    && Robot.arms.getCurrentIntakeArmState() != IntakeArmStates.UP 
                    && Robot.arms.getCurrentShooterArmState() == ShooterArmStates.SHOOTING_POSITION) {
                    setIntaking();
                }
                else if (intakeAxis <= -Constants.DEADZONE) {
                    setOuttaking();
                }
                break;

            case INTAKING:
                if (intakeAxis < Constants.DEADZONE
                || Robot.arms.getCurrentIntakeArmState() == IntakeArmStates.UP
                || Robot.arms.getCurrentShooterArmState() != ShooterArmStates.SHOOTING_POSITION) {
                    setNotMoving();
                }
                break;

            case OUTTAKING:
                if (intakeAxis > -Constants.DEADZONE 
                || Robot.arms.getCurrentIntakeArmState() == IntakeArmStates.UP
                || Robot.arms.getCurrentShooterArmState() != ShooterArmStates.SHOOTING_POSITION) {
                    setNotMoving();
                }
                break;
        }
    }

    public IntakeStates getCurrentIntakeState() {
        return currentIntakeState;
    }

    public void setNotMoving() {
        intakeMotor.set(0);
        currentIntakeState = IntakeStates.NOT_MOVING;
    }

    public void setIntaking() {
        intakeMotor.set(INTAKE_SPEED);
        currentIntakeState = IntakeStates.INTAKING;
    }

    public void setOuttaking() {
        intakeMotor.set(-INTAKE_SPEED);
        currentIntakeState = IntakeStates.OUTTAKING;
    }
}