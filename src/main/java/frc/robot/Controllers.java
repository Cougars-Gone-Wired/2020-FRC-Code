package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Manipulator
    private Joystick manipulatorController;
    private boolean armUpButton;
    private boolean armDownButton;
    private double intakeArmTrigger;
    private double intakeAxis;
    private double shooterTrigger;
    private double chomperOverrideAxis;
    private boolean stopCameraButton;

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
        intakeArmTrigger = manipulatorController.getRawAxis(Constants.INTAKE_ARM_TRIGGER);
        intakeAxis = -manipulatorController.getRawAxis(Constants.INTAKE_AXIS);
        shooterTrigger = manipulatorController.getRawAxis(Constants.SHOOTER_TRIGGER);
        chomperOverrideAxis = -manipulatorController.getRawAxis(Constants.CHOMPER_OVERRIDE_AXIS);
        stopCameraButton = manipulatorController.getRawButtonPressed(Constants.STOP_CAMERA_BUTTON);

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

    public double getIntakeArmTrigger() {
        return intakeArmTrigger;
    }

    public double getIntakeAxis() {
        return intakeAxis;
    }

    public double getShooterTrigger() {
        return shooterTrigger;
    }

    public double getChomperOverrideAxis() {
        return chomperOverrideAxis;
    }

    public boolean getStopCameraButton() {
        return stopCameraButton;
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