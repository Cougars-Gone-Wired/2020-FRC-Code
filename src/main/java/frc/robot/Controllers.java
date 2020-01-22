package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private double driveSpeedAxis;
    private double driveTurnAxis;

    private ToggleButton driveSideToggle;
    private double climberUpTrigger;
    private double climberDownTrigger;

    // Manipulator
    private Joystick manipulatorController;
    private double shooterTrigger;
    private double intakeAxis;
    private double feederAxis;
    private ToggleButton armToggle;
    private ToggleButton climberToggle;
    private ToggleButton intakeArmToggle;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);
        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);

        driveSideToggle = new ToggleButton(mobilityController, Constants.SWITCH_SIDE_BUTTON);
        armToggle = new ToggleButton(manipulatorController, Constants.ARM_POSITION_BUTTON);
        climberToggle = new ToggleButton(manipulatorController, Constants.CLIMBING_POSITION_BUTTON);
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
        shooterTrigger = manipulatorController.getRawAxis(Constants.SHOOTER_TRIGGER);
        intakeAxis = manipulatorController.getRawAxis(Constants.INTAKE_AXIS);
        feederAxis = manipulatorController.getRawAxis(Constants.FEEDER_AXIS);
        armToggle.toggle();
        climberToggle.toggle();
        intakeArmToggle.toggle();
    }

    // Mobilty
    public double getDriveSpeedAxis() {
        return driveSpeedAxis;
    }

    public double getDriveTurnAxis() {
        return driveTurnAxis;
    }

    public boolean isDriveSideToggle() {
        return driveSideToggle.getValue();
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

    public boolean isArmToggle() {
        return armToggle.getValue();
    }

    public boolean isClimberToggle() {
        return climberToggle.getValue();
    }

    public boolean isIntakeArmToggle() {
        return intakeArmToggle.getValue();
    }
}