package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Arm.ArmStates;

public class Intake {
    private static final double INTAKE_SPEED = 0.8;
    private static final double FEEDER_SPEED = 0.8;
    
    private WPI_TalonSRX intakeMotor;

    private DigitalInput feederLineBreak;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(Constants.INTAKE_MOTOR_ID);

        feederLineBreak = new DigitalInput(Constants.FEEDER_LINEBREAK_PORT);
        initialize();
    }

    public void initialize() {
        intakeMotor.set(0);
        currentIntakeState = IntakeStates.NOT_MOVING;
    }

    public enum IntakeStates {
        NOT_MOVING, INTAKING, OUTTAKING
    }

    private IntakeStates currentIntakeState;

    public void intake(double intakeAxis) {
        switch(currentIntakeState) {
            case NOT_MOVING:
                if (intakeAxis >= Constants.DEADZONE && Robot.arm.getCurrentArmState() == ArmStates.SHOOTING_POSITION) {
                    intakeMotor.set(INTAKE_SPEED);
                    Robot.feeder.setFeeding(FEEDER_SPEED);
                    currentIntakeState = IntakeStates.INTAKING;
                }
                else if (intakeAxis <= -Constants.DEADZONE) {
                    intakeMotor.set(-INTAKE_SPEED);
                    Robot.feeder.setOuttaking(FEEDER_SPEED);
                    currentIntakeState = IntakeStates.OUTTAKING;
                }
                break;

            case INTAKING:
                if(feederLineBreak.get()) {
                    Robot.feeder.setNotMoving();
                }

                if (intakeAxis < Constants.DEADZONE) {
                    intakeMotor.set(0);
                    Robot.feeder.setNotMoving();
                    currentIntakeState = IntakeStates.NOT_MOVING;
                }
                break;

            case OUTTAKING:
                if (intakeAxis > -Constants.DEADZONE) {
                    intakeMotor.set(0);
                    Robot.feeder.setNotMoving();
                    currentIntakeState = IntakeStates.NOT_MOVING;
                }
                break;
        }
    }
}