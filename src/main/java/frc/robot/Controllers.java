package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

// houses all methods to get values from xbox controllers
public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private boolean armUpBumper;
    private boolean armDownBumper;
    private double climberUpTrigger;
    private double climberDownTrigger;
    private double driveSpeedAxis;
    private double driveTurnAxis;
    private boolean driveSideToggle;
    private boolean limelightButton;
    private boolean stopCameraButton;

    // Manipulator
    private Joystick manipulatorController;
    private boolean intakeArmDownBumper;
    private boolean intakeArmUpBumper;
    private double intakeAxis;
    private double shooterTrigger;
    private boolean shooterModeToggle;
    private double feederOuttakeTrigger;
    private double chomperOverrideAxis;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);
        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);
    }

    public void updateControllerValues() {
        // Mobility
        armUpBumper = mobilityController.getRawButtonPressed(Constants.ARM_UP_BUMPER);
        armDownBumper = mobilityController.getRawButtonPressed(Constants.ARM_DOWN_BUMPER);
        climberUpTrigger = mobilityController.getRawAxis(Constants.CLIMBER_UP_TRIGGER);
        climberDownTrigger = mobilityController.getRawAxis(Constants.CLIMBER_DOWN_TRIGGER);
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        driveSideToggle= mobilityController.getRawButtonPressed(Constants.DRIVE_SIDE_TOGGLE);
        limelightButton = mobilityController.getRawButton(Constants.LIMELIGHT_BUTTON);
        stopCameraButton = mobilityController.getRawButtonPressed(Constants.STOP_CAMERA_BUTTON);

        // Manipulator
        intakeArmDownBumper = manipulatorController.getRawButton(Constants.INTAKE_ARM_DOWN_BUMPER);
        intakeArmUpBumper = manipulatorController.getRawButton(Constants.INTAKE_ARM_UP_BUMPER);
        intakeAxis = -manipulatorController.getRawAxis(Constants.INTAKE_AXIS);
        shooterTrigger = manipulatorController.getRawAxis(Constants.SHOOTER_TRIGGER);
        shooterModeToggle = manipulatorController.getRawButtonPressed(Constants.SHOOTER_MODE_TOGGLE);
        feederOuttakeTrigger = manipulatorController.getRawAxis(Constants.FEEDER_OUTTAKE_TRIGGER);
        chomperOverrideAxis = -manipulatorController.getRawAxis(Constants.CHOMPER_OVERRIDE_AXIS);
    }

    // Mobilty
    public boolean isArmUpBumper() {
        return armUpBumper;
    }

    public boolean isArmDownBumper() {
        return armDownBumper;
    }

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

    public boolean isDriveSideToggle() {
        return driveSideToggle;
    }

    public boolean isLimelightButton() {
        return limelightButton;
    }

    public boolean isStopCameraButton() {
        return stopCameraButton;
    }

    // Manipulator
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

    public boolean isShooterModeToggle() {
        return shooterModeToggle;
    }

    public double getFeederOuttakeTrigger() {
        return feederOuttakeTrigger;
    }

    public double getChomperOverrideAxis() {
        return chomperOverrideAxis;
    } 
}