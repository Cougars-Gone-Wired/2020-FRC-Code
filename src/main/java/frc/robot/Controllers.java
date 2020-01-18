package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private double driveSpeedAxis;
    private double driveTurnAxis;
    private boolean switchSideButton;

    //Manipulator
    private Joystick manipulatorController;
    private boolean shooterButton;
    private boolean intakeButton;
    private boolean feederButton;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);
        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);
    }

    public void updateControllerValues() {
        // Mobility
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        switchSideButton = mobilityController.getRawButton(Constants.SWITCH_SIDE_BUTTON);

        //Manipulator
        shooterButton = manipulatorController.getRawButton(Constants.SHOOTER_BUTTON);
        intakeButton = manipulatorController.getRawButton(Constants.INTAKE_BUTTON);
        feederButton = manipulatorController.getRawButton(Constants.FEEDER_BUTTON);
    }

    // Mobilty
    public double getDriveSpeedAxis() {
        return driveSpeedAxis;
    }
    public double getDriveTurnAxis() {
        return driveTurnAxis;
    }
    public boolean isSwitchSideButton() {
        return switchSideButton;
    }

    // Manipulator
    public boolean isShooterButton() {
        return shooterButton;
    }

    public boolean isIntakeButton() {
        return intakeButton;
    }

    public boolean isFeederButton() {
        return feederButton;
    }
}