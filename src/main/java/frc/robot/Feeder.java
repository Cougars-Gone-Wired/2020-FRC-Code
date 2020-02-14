package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

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
                if (Robot.intake.isIntaking() && !Robot.shooter.isShooting() && !feederLineBreak.get()) {
                    setIntaking();

                } else if (Robot.intake.isOuttaking() && !Robot.shooter.isShooting()) {
                    setOuttaking();

                } else if (Robot.intake.isNotMoving() && Robot.shooter.isShooting() && Robot.shooter.atDesiredVelocity()) {
                    setFeedingShooter();
                }
                break;

            case INTAKING:
                if(!Robot.intake.isIntaking() || Robot.shooter.isShooting() || feederLineBreak.get()) {
                    setNotMoving();
                }
                break;

            case OUTTAKING:
                if(!Robot.intake.isOuttaking() || Robot.shooter.isShooting()) {
                    setNotMoving();
                }
                break;
                
            case FEEDING_SHOOTER:
                if (!Robot.intake.isNotMoving() || !Robot.shooter.isShooting() || !Robot.shooter.atDesiredVelocity()) {
                    setNotMoving();
                }
                break;
        }   
    }

    public boolean isNotMoving() {
        return currentFeederState == FeederStates.NOT_MOVING;
    }

    public boolean isIntaking() {
        return currentFeederState == FeederStates.INTAKING;
    }

    public boolean isOuttaking() {
        return currentFeederState == FeederStates.OUTTAKING;
    }

    public boolean isFeedingShooter() {
        return currentFeederState == FeederStates.FEEDING_SHOOTER;
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

    public void setMotorsBrake() {
        feederMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setMotorsCoast() {
        feederMotor.setNeutralMode(NeutralMode.Coast);
    }
}
