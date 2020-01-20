package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private double driveSpeedAxis;
    private double driveTurnAxis;
    private boolean driveSide;
    private double climberUpTrigger;
    private double climberDownTrigger;

    // Manipulator
    private Joystick manipulatorController;
    private boolean shooterButton;
    private boolean intakeButton;
    private boolean feederButton;
    private boolean shootingPosButton;
    private boolean startPosButton;
    private boolean climbingPosButton;
    private boolean intakeArmButton;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);
        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);
    }

    public void updateControllerValues() {
        // Mobility
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        driveSide = mobilityController.getRawButtonPressed(Constants.SWITCH_SIDE_BUTTON);
        climberUpTrigger = mobilityController.getRawAxis(Constants.CLIMBER_UP_TRIGGER);
        climberDownTrigger = mobilityController.getRawAxis(Constants.CLIMBER_DOWN_TRIGGER);

        // Manipulator
        shooterButton = manipulatorController.getRawButton(Constants.SHOOTER_BUTTON);
        intakeButton = manipulatorController.getRawButton(Constants.INTAKE_BUTTON);
        feederButton = manipulatorController.getRawButton(Constants.FEEDER_BUTTON);

        shootingPosButton = manipulatorController.getRawButtonPressed(Constants.SHOOTING_POSITION_BUTTON);
        startPosButton = manipulatorController.getRawButtonPressed(Constants.STARTING_POSITION_BUTTON);
        climbingPosButton = manipulatorController.getRawButtonPressed(Constants.CLIMING_POSITION_BUTTON);
        intakeArmButton = manipulatorController.getRawButtonPressed(Constants.INTAKE_ARM_BUTTON);
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

    public double getClimberUpTrigger() {
        return climberUpTrigger;
    }

    public double getClimberDownTrigger() {
        return climberDownTrigger;
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
        return shootingPosButton;
    }

    public boolean isStartingPosButton() {
        return startPosButton;
    }

    public boolean isClimbingPosButton() {
        return climbingPosButton;
    }

    public boolean isIntakeArmButton() {
        return intakeArmButton;
    }
}