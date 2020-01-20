public class Shooter {

	private static final double SHOOTER_SPEED = 1;

	private WPI_TalonSRX shooterMotor;

	public Shooter() {
		shooterMotor = new WPI_TalonSRX(0);
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

	public void shoot(boolean shooterButton) {

		switch (currentShooterState) {
		case NOT_MOVING:
			if (shooterButton) {
				shooterMotor.set(SHOOTER_SPEED);
				currentShooterState = ShooterStates.SPINNING;
			}
			break;
		case SPINNING:
			if (!shooterButton) {
				shooterMotor.set(0);
				currentShooterState = ShooterStates.NOT_MOVING;
			}
			break;
		}

	}
}
