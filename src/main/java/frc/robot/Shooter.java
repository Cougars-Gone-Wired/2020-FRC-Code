package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Shooter {
    private static final double SHOOTER_SPEED = .45;

    private WPI_TalonFX shooterMotor;

    private boolean shooterTriggerBool;

    public Shooter() {
        shooterMotor = new WPI_TalonFX(Constants.SHOOTER_MOTOR_ID);
        initialize();
    }

    public void initialize() {
        setNotMoving();
    }

    public enum ShooterStates {
        NOT_MOVING, SHOOTING
    }

    private ShooterStates currentShooterState;

    public void shoot(double shooterTrigger) {
        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);

        switch (currentShooterState) {
        case NOT_MOVING:
            if (shooterTriggerBool && !Robot.arms.isShooterClimbingPosition()) {
                setShooting();
            }
            break;
        case SHOOTING:
            if (!shooterTriggerBool || Robot.arms.isShooterClimbingPosition()) {
                setNotMoving();
            } 
            break;
        }
    }

    public boolean isNotMoving() {
        return currentShooterState == ShooterStates.NOT_MOVING;
    }

    public void setNotMoving() {
        shooterMotor.set(0);
        currentShooterState = ShooterStates.NOT_MOVING;
    }

    public boolean isShooting() {
        return currentShooterState == ShooterStates.NOT_MOVING;
    }

    public void setShooting() {
        shooterMotor.set(SHOOTER_SPEED);
        currentShooterState = ShooterStates.SHOOTING;
    }
}
