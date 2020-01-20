package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private double driveSpeedAxis;
    private double driveTurnAxis;

    public ToggleButton driveSideToggle;
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
    public ToggleButton intakeArmToggle;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);
        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);

        driveSideToggle = new ToggleButton(mobilityController, Constants.SWITCH_SIDE_BUTTON);
        intakeArmToggle = new ToggleButton(manipulatorController, Constants.INTAKE_ARM_BUTTON);
    }

    public void updateControllerValues() {
        // Mobility
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        driveSideToggle.toggle();
        climberUpTrigger = mobilityController.getRawAxis(Constants.CLIMBER_UP_TRIGGER);
        climberDownTrigger = mobilityController.getRawAxis(Constants.CLIMBER_DOWN_TRIGGER);

        // Manipulator
        shooterButton = manipulatorController.getRawButton(Constants.SHOOTER_BUTTON);
        intakeButton = manipulatorController.getRawButton(Constants.INTAKE_BUTTON);
        feederButton = manipulatorController.getRawButton(Constants.FEEDER_BUTTON);
        shootingPosButton = manipulatorController.getRawButtonPressed(Constants.SHOOTING_POSITION_BUTTON);
        startPosButton = manipulatorController.getRawButtonPressed(Constants.STARTING_POSITION_BUTTON);
        climbingPosButton = manipulatorController.getRawButtonPressed(Constants.CLIMBING_POSITION_BUTTON);
        intakeArmToggle.toggle();
    }

    // Mobilty
    public double getDriveSpeedAxis() {
        return driveSpeedAxis;
    }

    public double getDriveTurnAxis() {
        return driveTurnAxis;
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
}