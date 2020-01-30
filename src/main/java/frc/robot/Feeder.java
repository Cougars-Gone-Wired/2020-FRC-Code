package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Intake.IntakeStates;
import frc.robot.Shooter.ShooterStates;

public class Feeder {
    private static final double FEED_SHOOTER_SPEED = 0.6;
    private static final double FEED_INTAKE_SPEED = 0.8;

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
                if (Robot.intake.getCurrentIntakeState() == IntakeStates.INTAKING 
                && !feederLineBreak.get()
                && Robot.shooter.getCurrentShooterState() != ShooterStates.SPINNING) {
                    setIntaking();
                } else if (Robot.intake.getCurrentIntakeState() == IntakeStates.OUTTAKING
                && Robot.shooter.getCurrentShooterState() != ShooterStates.SPINNING) {
                    setOuttaking();
                } else if (Robot.shooter.getCurrentShooterState() == ShooterStates.SPINNING){
                    setFeedingShooter();
                }
                break;

            case INTAKING:
                if(Robot.intake.getCurrentIntakeState() != IntakeStates.INTAKING
                || Robot.shooter.getCurrentShooterState() != ShooterStates.SPINNING
                || feederLineBreak.get() ) {
                    setNotMoving();
                }
                break;

            case OUTTAKING:
                if(Robot.intake.getCurrentIntakeState() != IntakeStates.INTAKING
                || Robot.shooter.getCurrentShooterState() != ShooterStates.SPINNING) {
                    setNotMoving();
                }
                break;
                
            case FEEDING_SHOOTER:
                if (Robot.intake.getCurrentIntakeState() != IntakeStates.NOT_MOVING
                || Robot.shooter.getCurrentShooterState() != ShooterStates.SPINNING) {
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

    public void setIntaking() {
        feederMotor.set(FEED_INTAKE_SPEED);
        currentFeederState = FeederStates.INTAKING;
    }

    public void setOuttaking() {
        feederMotor.set(-FEED_INTAKE_SPEED);
        currentFeederState = FeederStates.OUTTAKING;
    }

    public void setFeedingShooter() {
        feederMotor.set(FEED_SHOOTER_SPEED);
        currentFeederState = FeederStates.FEEDING_SHOOTER;
    }
}
