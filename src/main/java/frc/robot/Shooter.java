package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Shooter {
    private static final double SHOOTER_SPEED = 0.45;	

    private WPI_TalonSRX shooterMotor;
    private boolean shooterTriggerBool;

	public Shooter() {
		shooterMotor = new WPI_TalonSRX(Constants.SHOOTER_MOTOR_ID);
		currentShooterState = ShooterStates.NOT_MOVING;
	}
	
	public void initialize() {
        shooterMotor.set(0);
        currentShooterState = ShooterStates.NOT_MOVING;
    }

	public enum ShooterStates {
		NOT_MOVING, SPINNING
	}

	private ShooterStates currentShooterState;
	
	public void shoot(double shooterTrigger) {
        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);

		switch(currentShooterState) {
			case NOT_MOVING:
				if (shooterTriggerBool) {
					shooterMotor.set(SHOOTER_SPEED);
					currentShooterState = ShooterStates.SPINNING;
				}
				break;
			case SPINNING:
				if (!shooterTriggerBool) {
					shooterMotor.set(0);
					currentShooterState = ShooterStates.NOT_MOVING;
				}
				break;	
		}
	}	
}