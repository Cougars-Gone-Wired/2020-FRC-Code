package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// the system that "feeds" balls from the intake to the shooter
// assists in intaking balls and feeds shooter when shooting
public class Feeder {
    private static final double FEED_SHOOTER_SPEED = 0.82; // speed for feeding balls to the shooter
    private static final double AUTO_FEED_SHOOTER_SPEED = 0.7; // speed for feeding balls to the shooter in auto (slower since driver can't fix jams)
    private static final double FEED_INTAKE_SPEED = 0.6; // speed for intaking balls into the feeder

    private WPI_TalonSRX feederMotor;

    // line breaks used to indicate when the feeder had 4 balls in it, so that the feeder could stop intaking while the 
    // intake could continue to get one more ball, commented out in code since testing was not completed
    private DigitalInput feederUpperLineBreak;
    private DigitalInput feederLowerLineBreak;

    private boolean feederTriggerBool;
    private double feedShooterSpeed; // the speed at which the feeder feeds balls into the shooter when shooting

    public Feeder() {
        feederMotor = new WPI_TalonSRX(Constants.FEEDER_MOTOR_ID);
        feederUpperLineBreak = new DigitalInput(Constants.FEEDER_UPPER_LINEBREAK_PORT);
        feederLowerLineBreak = new DigitalInput(Constants.FEEDER_LOWER_LINEBREAK_PORT);
        initialize();
    }

    public void initialize() {
        setNotMoving();
    }

    public enum FeederStates {
        NOT_MOVING, INTAKING, OUTTAKING, FEEDING_SHOOTER
    }

    private FeederStates currentFeederState;

    public void controlFeeder(double feederOuttakeTrigger) {
        SmartDashboard.putBoolean("Upper Line Break", feederUpperLineBreak.get());
        SmartDashboard.putBoolean("Lower Line Break", feederLowerLineBreak.get());

        feederTriggerBool = (feederOuttakeTrigger >= Constants.DEADZONE);

        switch (currentFeederState) {
            case NOT_MOVING:
                if (Robot.intake.isIntaking() && !Robot.shooter.isShooting()
                        // && (feederUpperLineBreak.get() || feederLowerLineBreak.get())
                        ) {
                    setIntaking();

                } else if (feederTriggerBool && !Robot.shooter.isShooting()) {
                    setOuttaking();

                } else if (Robot.intake.isNotMoving() && Robot.shooter.isShooting()
                        && Robot.shooter.atInitialDesiredVelocity()) {
                    setFeedingShooter();
                }
                break;

            case INTAKING:
                if (!Robot.intake.isIntaking() || Robot.shooter.isShooting()
                        // || (!feederUpperLineBreak.get() && !feederLowerLineBreak.get())
                        ) {
                    setNotMoving();
                }
                break;

            case OUTTAKING:
                if (!feederTriggerBool || Robot.shooter.isShooting()) {
                    setNotMoving();
                }
                break;

            case FEEDING_SHOOTER:
                if (!Robot.shooter.atDesiredVelocity() 
                    // && feederUpperLineBreak.get()
                    ) {
                    setStop();
                } else {
                    setFeedingShooter();
                }
                if (!Robot.intake.isNotMoving() || !Robot.shooter.isShooting()) {
                    setNotMoving();
                }
                break;
        }
    }

    // makes feeder controlled soley by other systems in commands rather than a trigger
    public void controlFeederAuto() {
        setFeedShooterSpeed(AUTO_FEED_SHOOTER_SPEED);
        controlFeeder(0);
    }

    // gives driver some control over the feeder in teleop
    public void controlFeederTeleop(double feederOuttakeTrigger) {
        setFeedShooterSpeed(FEED_SHOOTER_SPEED);
        controlFeeder(feederOuttakeTrigger);
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
        feederMotor.set(feedShooterSpeed);
        currentFeederState = FeederStates.FEEDING_SHOOTER;
    }

    public void setStop() {
        feederMotor.set(0);
    }

    public void setFeedShooterSpeed(double feedShooterSpeed) {
        this.feedShooterSpeed = feedShooterSpeed;
    }

    public void setMotorsBrake() {
        feederMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setMotorsCoast() {
        feederMotor.setNeutralMode(NeutralMode.Coast);
    }
}
