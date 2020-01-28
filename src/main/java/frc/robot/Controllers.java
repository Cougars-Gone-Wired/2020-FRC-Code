package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private double driveSpeedAxis;
    private double driveTurnAxis;
    private boolean driveSideToggle;
    private boolean limelightButton;
    private double climberUpTrigger;
    private double climberDownTrigger;

    // Manipulator
    private Joystick manipulatorController;
    private double shooterTrigger;
    private double intakeAxis;
    private double feederAxis;
    private boolean armUpButton;
    private boolean armDownButton;
    private boolean intakeArmToggle;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);
        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);
    }

    public void updateControllerValues() {
        // Mobility
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        driveSideToggle= mobilityController.getRawButtonPressed(Constants.SWITCH_SIDE_BUTTON);
        climberUpTrigger = mobilityController.getRawAxis(Constants.CLIMBER_UP_TRIGGER);
        climberDownTrigger = mobilityController.getRawAxis(Constants.CLIMBER_DOWN_TRIGGER);
        limelightButton = mobilityController.getRawButton(Constants.LIMELIGHT_BUTTON);

        // Manipulator
        shooterTrigger = manipulatorController.getRawAxis(Constants.SHOOTER_TRIGGER);
        intakeAxis = manipulatorController.getRawAxis(Constants.INTAKE_AXIS);
        feederAxis = manipulatorController.getRawAxis(Constants.FEEDER_AXIS);
        armUpButton = manipulatorController.getRawButtonPressed(Constants.ARM_UP_BUTTON);
        armDownButton = manipulatorController.getRawButtonPressed(Constants.ARM_DOWN_BUTTON);
        intakeArmToggle = manipulatorController.getRawButtonPressed(Constants.INTAKE_ARM_BUTTON);
    }

    // Mobilty
    public double getDriveSpeedAxis() {
        return driveSpeedAxis;
    }

    public double getDriveTurnAxis() {
        return driveTurnAxis;
    }

    public boolean getDriveSideToggle() {
        return driveSideToggle;
    }

    public boolean getLimelightButton() {
        return limelightButton;
    }

    public double getClimberUpTrigger() {
        return climberUpTrigger;
    }

    public double getClimberDownTrigger() {
        return climberDownTrigger;
    }

    // Manipulator
    public double getShooterTrigger() {
        return shooterTrigger;
    }

    public double getIntakeAxis() {
        return intakeAxis;
    }

    public double getFeederAxis() {
        return feederAxis;
    }

    public boolean isArmUpButton() {
        return armUpButton;
    }

    public boolean isArmDownButton() {
        return armDownButton;
    }

    public boolean getIntakeArmToggle() {
        return intakeArmToggle;
    }
}