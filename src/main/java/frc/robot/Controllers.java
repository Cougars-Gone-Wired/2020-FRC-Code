package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Manipulator
    private Joystick manipulatorController;
    private boolean armUpButton;
    private boolean armDownButton;
    private boolean intakeArmDownBumper;
    private boolean intakeArmUpBumper;
    private double intakeAxis;
    private double shooterTrigger;
    private boolean stopCameraButton;
    private double feederAxis;
    private boolean chomperUpButton;
    private boolean chomperDownButton;

    // Mobility
    private Joystick mobilityController;
    private double climberUpTrigger;
    private double climberDownTrigger;
    private double driveSpeedAxis;
    private double driveTurnAxis;
    private boolean driveSideToggle;
    private boolean limelightButton;

    public Controllers() {
        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);
    }

    public void updateControllerValues() {
        // Manipulator
        armUpButton = manipulatorController.getRawButtonPressed(Constants.ARM_UP_BUTTON);
        armDownButton = manipulatorController.getRawButtonPressed(Constants.ARM_DOWN_BUTTON);
        intakeArmDownBumper = manipulatorController.getRawButton(Constants.INTAKE_ARM_DOWN_BUMPER);
        intakeArmUpBumper = manipulatorController.getRawButton(Constants.INTAKE_ARM_UP_BUMPER);        intakeAxis = manipulatorController.getRawAxis(Constants.INTAKE_AXIS);
        shooterTrigger = manipulatorController.getRawAxis(Constants.SHOOTER_TRIGGER);
        stopCameraButton = manipulatorController.getRawButtonPressed(Constants.STOP_CAMERA_BUTTON);
        feederAxis = manipulatorController.getRawAxis(Constants.FEEDER_AXIS);
        chomperUpButton = manipulatorController.getRawButtonPressed(Constants.CHOMPER_UP_BUTTON);
        chomperDownButton = manipulatorController.getRawButtonPressed(Constants.CHOMPER_DOWN_BUTTON);

        // Mobility
        climberUpTrigger = mobilityController.getRawAxis(Constants.CLIMBER_UP_TRIGGER);
        climberDownTrigger = mobilityController.getRawAxis(Constants.CLIMBER_DOWN_TRIGGER);
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        driveSideToggle= mobilityController.getRawButtonPressed(Constants.SWITCH_SIDE_BUTTON);
        limelightButton = mobilityController.getRawButton(Constants.LIMELIGHT_BUTTON);
    }

    // Manipulator
    public boolean isArmUpButton() {
        return armUpButton;
    }

    public boolean isArmDownButton() {
        return armDownButton;
    }

    public boolean isIntakeArmDownBumper() {
        return intakeArmDownBumper;
    }

    public boolean isIntakeArmUpBumper() {
        return intakeArmUpBumper;
    }

    public double getIntakeAxis() {
        return intakeAxis;
    }

    public double getShooterTrigger() {
        return shooterTrigger;
    }

    public boolean getStopCameraButton() {
        return stopCameraButton;
    }

    public double getFeederAxis() {
        return feederAxis;
    }

    public boolean getChomperUpButton() {
        return chomperUpButton;
    }

    public boolean getChomperDownButton() {
        return chomperDownButton;
    }
    // Mobilty
    public double getClimberUpTrigger() {
        return climberUpTrigger;
    }

    public double getClimberDownTrigger() {
        return climberDownTrigger;
    }
    
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
}