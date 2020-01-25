package frc.robot;

public class State {

    private double driveSpeedAxisState;
    private double driveTurnAxisState;
    private boolean switchSideState;
    private boolean limelightState;

    public double getDriveSpeedAxisState() {
        return this.driveSpeedAxisState;
    }

    public double getDriveTurnAxisState() {
        return this.driveTurnAxisState;
    }

    public boolean getSwitchSideState() {
        return this.switchSideState;
    }

    public boolean getLimelightState() {
        return this.limelightState;
    }

    public void setDriveSpeedAxisState(double driveSpeedAxisState) {
        this.driveSpeedAxisState = driveSpeedAxisState;
    }

    public void setDriveTurnAxisState(double driveTurnAxisState) {
        this.driveTurnAxisState = driveTurnAxisState;
    }

    public void setSwitchSideState(boolean switchSideState) {
        this.switchSideState = switchSideState;
    }

    public void setLimelightState(boolean limelightState) {
        this.limelightState = limelightState;
    }

}
