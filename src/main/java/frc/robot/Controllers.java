package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private double driveSpeedAxis;
    private double driveTurnAxis;
    private boolean driveSide;

    // Manipulator
    private Joystick manipulatorController;
    private boolean shooterButton;
    private boolean intakeButton;
    private boolean feederButton;
    private boolean shooterPosition;
    private boolean startPosition;
    private boolean climingPosition;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);

        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);

    }

    public void updateControllerValues() {
        // Mobility
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        driveSide = mobilityController.getRawButtonPressed(Constants.SWITCH_SIDE_BUTTON);

        // Manipulator
        shooterButton = manipulatorController.getRawButton(Constants.SHOOTER_BUTTON);
        intakeButton = manipulatorController.getRawButton(Constants.INTAKE_BUTTON);
        feederButton = manipulatorController.getRawButton(Constants.FEEDER_BUTTON);

        shooterPosition = manipulatorController.getRawButtonPressed(Constants.SHOOTING_POSITION_BUTTON);
        startPosition = manipulatorController.getRawButtonPressed(Constants.STARTING_POSITION_BUTTON);
        climingPosition = manipulatorController.getRawButtonPressed(Constants.CLIMING_POSITION_BUTTON);
    }

    // Mobilty
    public double getDriveSpeedAxis() {
        return driveSpeedAxis;
    }

    public double getDriveTurnAxis() {
        return driveTurnAxis;
    }

    public boolean isDriveSideButton() {
        return driveSide;
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

    public boolean isShootingPosButton() {
        return shooterPosition;
    }

    public boolean isStartingPosButton() {
        return startPosition;
    }

    public boolean isClimbingPosButton() {
        return climingPosition;
    }
}