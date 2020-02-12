package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Manipulator
    private Joystick manipulatorController;
    private boolean armUpButton;
    private boolean armDownButton;
    private boolean stopCameraButton;
    private double intakeArmAxis;
    private double intakeTrigger;
    private double shooterTrigger;

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
        stopCameraButton = manipulatorController.getRawButtonPressed(Constants.STOP_CAMERA_BUTTON);
        intakeArmAxis = manipulatorController.getRawAxis(Constants.INTAKE_ARM_AXIS);
        intakeTrigger = manipulatorController.getRawAxis(Constants.INTAKE_TRIGGER);
        shooterTrigger = manipulatorController.getRawAxis(Constants.SHOOTER_TRIGGER);

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

    public double getIntakeArmAxis() {
        return intakeArmAxis;
    }
    
    public double getIntakeTrigger() {
        return intakeTrigger;
    }

    public double getShooterTrigger() {
        return shooterTrigger;
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

    public boolean getStopCameraButton() {
        return stopCameraButton;
    }
}