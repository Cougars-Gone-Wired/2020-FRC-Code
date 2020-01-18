package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

    // Mobility
    private Joystick mobilityController;
    private double driveSpeedAxis;
    private double driveTurnAxis;
    private Toggle switchSideToggle;
    private boolean switchSide;

    //Manipulator
    private Joystick manipulatorController;
    private boolean shooterButton;
    private boolean intakeButton;
    private boolean feederButton;

    public Controllers() {
        mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_ID);
        switchSideToggle = new Toggle(mobilityController, Constants.SWITCH_SIDE_BUTTON);

        manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_ID);
    }

    public void updateControllerValues() {
        // Mobility
        driveSpeedAxis = mobilityController.getRawAxis(Constants.DRIVE_SPEED_AXIS);
        driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
        switchSide = switchSideToggle.toggle();

        //Manipulator
        shooterButton = manipulatorController.getRawButton(Constants.SHOOTER_BUTTON);
        intakeButton = manipulatorController.getRawButton(Constants.INTAKE_BUTTON);
        feederButton = manipulatorController.getRawButton(Constants.FEEDER_BUTTON);
    }

    // Mobilty
    public double getDriveSpeedAxis() {
        return driveSpeedAxis;
    }
    public double getDriveTurnAxis() {
        return driveTurnAxis;
    }
    public boolean isSwitchSide() {
        return switchSide;
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