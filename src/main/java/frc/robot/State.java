package frc.robot;

public class State {

	private double driveRightAxisState;
	private double driveLeftAxisState;

	public double getDriveRightAxisState() {
		return driveRightAxisState;
	}

	public void setDriveRightAxisState(double driveForwardAxisState) {
		this.driveRightAxisState = driveForwardAxisState;
	}

	public double getDriveLeftAxisState() {
		return driveLeftAxisState;
	}

	public void setDriveLeftAxisState(double driveTurnAxisState) {
		this.driveLeftAxisState = driveTurnAxisState;
	}

}
