package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive {

  public static double DRIVE_SPEED = 0.9;
  public static double TURN_SPEED = 0.9;

  private WPI_TalonSRX frontLeftMotor;
  private WPI_TalonSRX midLeftMotor;
  private WPI_TalonSRX backLeftMotor;

  private WPI_TalonSRX frontRightMotor;
  private WPI_TalonSRX midRightMotor;
  private WPI_TalonSRX backRightMotor;

  private DifferentialDrive robotDrive;

  public Drive() {

    midLeftMotor = new WPI_TalonSRX(99);
    frontLeftMotor = new WPI_TalonSRX(98);
    backLeftMotor = new WPI_TalonSRX(97);

    midRightMotor = new WPI_TalonSRX(96);
    frontRightMotor = new WPI_TalonSRX(95);
    backRightMotor = new WPI_TalonSRX(94);

    frontLeftMotor.follow(midLeftMotor);
    backLeftMotor.follow(frontLeftMotor);

    frontRightMotor.follow(midRightMotor);
    backRightMotor.follow(frontRightMotor);

    initMotors();

    robotDrive = new DifferentialDrive(midLeftMotor, midRightMotor);
    robotDrive.setDeadband(0.15);// robotDrive.setDeadband(Constants.DRIVE_DEADZONE);
    robotDrive.setSafetyEnabled(false);

  }

  public void initalize() {
    midLeftMotor.set(0);
    frontLeftMotor.set(0);
    backLeftMotor.set(0);

    midRightMotor.set(0);
    frontRightMotor.set(0);
    backRightMotor.set(0);

    driveState = DriveStates.SHOOTER_SIDE;
  }

  public void initMotors() {
    midLeftMotor.setNeutralMode(NeutralMode.Brake);
    midLeftMotor.configOpenloopRamp(0);

    frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    frontLeftMotor.configOpenloopRamp(0);

    backLeftMotor.setNeutralMode(NeutralMode.Brake);
    backLeftMotor.configOpenloopRamp(0);

    midRightMotor.setNeutralMode(NeutralMode.Brake);
    midRightMotor.configOpenloopRamp(0);

    frontRightMotor.setNeutralMode(NeutralMode.Brake);
    frontRightMotor.configOpenloopRamp(0);

    backRightMotor.setNeutralMode(NeutralMode.Brake);
    backRightMotor.configOpenloopRamp(0);
  }

  public enum DriveStates {
    SHOOTER_SIDE, INTAKE_SIDE
  }

  private DriveStates driveState;

  public void robotDrive(double driveSpeedAxis, double driveTurnAxis) {
    driveSpeedAxis = driveSpeedAxis * DRIVE_SPEED;
    driveTurnAxis = driveTurnAxis * TURN_SPEED;

    switch (driveState) {
    case SHOOTER_SIDE:
      robotDrive.arcadeDrive(driveSpeedAxis, -driveTurnAxis);
      break;

    case INTAKE_SIDE:
      robotDrive.arcadeDrive(-driveSpeedAxis, -driveTurnAxis);
      break;
    }
  }

  public void setSide(boolean switchSide) {
    if (switchSide) {
      driveState = DriveStates.SHOOTER_SIDE;
    } else {
      driveState = DriveStates.INTAKE_SIDE;
    }
  }
}
