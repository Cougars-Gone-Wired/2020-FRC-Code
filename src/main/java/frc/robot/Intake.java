package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
    private static final double INTAKE_SPEED = 1;
    
    private WPI_TalonSRX intakeMotor;
    private Solenoid intakeArmSolenoid;

    private DigitalInput feederLineBreak;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(Constants.INTAKE_MOTOR_ID);
        intakeArmSolenoid = new Solenoid(Constants.INTAKE_SOLENOID_PORT);

        feederLineBreak = new DigitalInput(Constants.FEEDER_LINEBREAK_PORT);
        initialize();
    }

    public void initialize() {
        intakeMotor.set(0);
        intakeArmSolenoid.set(false);
        currentIntakeState = IntakeStates.NOT_MOVING;
        currentIntakeArmState = IntakeArmStates.UP;
    }

    public enum IntakeStates {
        NOT_MOVING, INTAKING, OUTTAKING
    }

    private IntakeStates currentIntakeState;

    public void intake(double intakeAxis) {
        switch(currentIntakeState) {
            case NOT_MOVING:
                if (intakeAxis >= Constants.DEADZONE) {
                    intakeMotor.set(INTAKE_SPEED);
                    currentIntakeState = IntakeStates.INTAKING;
                }
                else if (intakeAxis <= -Constants.DEADZONE) {
                    intakeMotor.set(-INTAKE_SPEED);
                    currentIntakeState = IntakeStates.OUTTAKING;
                }
                break;

            case INTAKING:
                if(feederLineBreak.get()) {
                    Robot.feeder.setNotMoving();
                }

                if (intakeAxis < Constants.DEADZONE) {
                    intakeMotor.set(0);
                    currentIntakeState = IntakeStates.NOT_MOVING;
                }
                break;

            case OUTTAKING:
                if (intakeAxis > -Constants.DEADZONE) {
                    intakeMotor.set(0);
                    currentIntakeState = IntakeStates.NOT_MOVING;
                }
                break;
        }
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