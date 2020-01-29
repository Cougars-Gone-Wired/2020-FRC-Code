package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.robot.Arms.ShooterArmStates;

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
        NOT_MOVING, SPINNING
    }

    private ShooterStates currentShooterState;

    public void shoot(double shooterTrigger) {
        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);

        switch (currentShooterState) {
        case NOT_MOVING:
            if (shooterTriggerBool && Robot.arms.getCurrentShooterArmState() != ShooterArmStates.CLIMBING_POSITION) {
                setSpinning();
            }
            break;
        case SPINNING:
            if (!shooterTriggerBool || Robot.arms.getCurrentShooterArmState() == ShooterArmStates.CLIMBING_POSITION) {
                setNotMoving();
            } 
            break;
        }
    }

    public ShooterStates getCurrentShooterState() {
        return currentShooterState;
    }

    public void setNotMoving() {
        shooterMotor.set(0);
        currentShooterState = ShooterStates.NOT_MOVING;
    }

    public void setSpinning() {
        shooterMotor.set(SHOOTER_SPEED);
        currentShooterState = ShooterStates.SPINNING;
    }
}
