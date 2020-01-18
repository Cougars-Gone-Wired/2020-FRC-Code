package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private double driveSpeedAxis;
    private double driveTurnAxis;
    public ToggleButton driveSideToggle;

    // Manipulator
    private Joystick manipulatorController;
    private boolean shooterButton;
    private boolean intakeButton;
    private boolean feederButton;
    public ToggleButton shooterPosToggle;
    public ToggleButton startPosToggle;
    public ToggleButton climingPosToggle;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);

        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);

        driveSideToggle = new ToggleButton(mobilityController, Constants.SWITCH_SIDE_BUTTON);
        shooterPosToggle = new ToggleButton(manipulatorController, Constants.SHOOTING_POSITION_BUTTON);
        startPosToggle = new ToggleButton(manipulatorController, Constants.STARTING_POSITION_BUTTON);
        climingPosToggle = new ToggleButton(manipulatorController, Constants.CLIMING_POSITION_BUTTON);

    }

    public void updateControllerValues() {
        // Mobility
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        driveSideToggle.toggle();
        SmartDashboard.putBoolean("TOGGLE BUTTON", driveSideToggle.getValue());

        // Manipulator
        shooterButton = manipulatorController.getRawButton(Constants.SHOOTER_BUTTON);
        intakeButton = manipulatorController.getRawButton(Constants.INTAKE_BUTTON);
        feederButton = manipulatorController.getRawButton(Constants.FEEDER_BUTTON);

        shooterPosToggle.toggle();
        startPosToggle.toggle();
        climingPosToggle.toggle();
    }

    // Mobilty
    public double getDriveSpeedAxis() {
        return driveSpeedAxis;
    }

    public double getDriveTurnAxis() {
        return driveTurnAxis;
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