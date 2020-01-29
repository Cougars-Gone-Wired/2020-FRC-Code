package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Intake.IntakeStates;
import frc.robot.Shooter.ShooterStates;

public class Feeder {
    private static final double FEED_SHOOTER_SPEED = 0.6;
    private static final double FEEDER_SPEED = 0.8;

    private WPI_TalonSRX feederMotor;

    private DigitalInput feederLineBreak;

    public Feeder() {
        feederMotor = new WPI_TalonSRX(Constants.FEEDER_MOTOR_ID);
        feederLineBreak = new DigitalInput(Constants.FEEDER_LINEBREAK_PORT);
        initialize();
    }

    public void initialize() {
        setNotMoving();
    }

    public enum FeederStates {
        NOT_MOVING, INTAKING, OUTTAKING, FEEDING_SHOOTER
    }

    private FeederStates currentFeederState;

    public void feed() {
        switch(currentFeederState) {
            case NOT_MOVING:
                if (Robot.intake.getCurrentIntakeState() == IntakeStates.INTAKING) {
                    setIntaking(FEEDER_SPEED);
                } else if (Robot.intake.getCurrentIntakeState() == IntakeStates.OUTTAKING) {
                    setOuttaking(-FEEDER_SPEED);
                } else if (Robot.shooter.getCurrentShooterState() == ShooterStates.SPINNING){
                    setFeedingShooter(FEED_SHOOTER_SPEED);
                }
                break;

            case INTAKING:
                if(feederLineBreak.get() || Robot.intake.getCurrentIntakeState() != IntakeStates.INTAKING) {
                    setNotMoving();
                }
                break;

            case OUTTAKING:
                if (Robot.intake.getCurrentIntakeState() != IntakeStates.OUTTAKING) {
                    setNotMoving();
                }
                break;

            case FEEDING_SHOOTER:
                if (Robot.shooter.getCurrentShooterState() != ShooterStates.SPINNING) {
                    setNotMoving();
                }
                break;

        }   
    }

    public FeederStates getCurrentFeederState() {
        return currentFeederState;
    }

    public void setNotMoving() {
        feederMotor.set(0);
        currentFeederState = FeederStates.NOT_MOVING;
    }

    public void setIntaking(double intakeSpeed) {
        feederMotor.set(intakeSpeed);
        currentFeederState = FeederStates.INTAKING;
    }

    public void setOuttaking(double outtakeSpeed) {
        feederMotor.set(outtakeSpeed);
        currentFeederState = FeederStates.OUTTAKING;
    }

    public void setFeedingShooter(double feedingShooterSpeed) {
        feederMotor.set(feedingShooterSpeed);
        currentFeederState = FeederStates.FEEDING_SHOOTER;
    }
}
