package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive {

  private WPI_TalonSRX frontLeftMotor; //Based off Hatch Side
  private WPI_TalonSRX midLeftMotor;
  private WPI_TalonSRX backLeftMotor;

  private WPI_TalonSRX frontRightMotor;
  private WPI_TalonSRX midRightMotor;
  private WPI_TalonSRX backRightMotor;

  private DifferentialDrive robotDrive;

  public Drive() {

    midLeftMotor = new WPI_TalonSRX(Constants.MID_LEFT_MOTOR_ID);
    frontLeftMotor = new WPI_TalonSRX(Constants.FRONT_LEFT_MOTOR_ID)
    backLeftMotor = new WPI_TalonSRX(Constants.BACK_LEFT_MOTOR_ID);

    frontLeftMotor.follow(midLeftMotor);
    backLeftMotor.follow(frontLeftMotor);

    midRightMotor = new WPI_TalonSRX(Constants.MID_RIGHT_MOTOR_ID);
    frontRightMotor = new WPI_TalonSRX(Constants.FRONT_RIGHT_MOTOR_ID);
    backRightMotor =  new WPI_TalonSRX(Constants.BACK_RIGHT_MOTOR_ID);

    frontRightMotor.follow(midRightMotor);
    backRightMotor.follow(frontRightMotor);
    initMotors();

    robotDrive = new DifferentialDrive(midLeftMotor, midRightMotor);
    robotDrive.setSafetyEnabled(false);

  }

  public void initalize() {

    midLeftMotor.set(0);
    frontLeftMotor.set(0);
    backLeftMotor.set(0);

    midRightMotor.set(0);
    frontRightMotor.set(0);
    backRightMotor.set(0);

  }

  public void initMotors() {
    midLeftMotor.setNeutralMode(NeutralMode.Brake);
    midLeftMotor.configOpenloopRamp(Constants.RAMP_TIME);

    frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    frontLeftMotor.configOpenloopRamp(Constants.RAMP_TIME);

    backLeftMotor.setNeutralMode(NeutralMode.Brake);
    backLeftMotor.configOpenloopRamp(Constants.RAMP_TIME);


    midRightMotor.setNeutralMode(NeutralMode.Brake);
    midRightMotor.configOpenloopRamp(Constants.RAMP_TIME);
    
    frontRightMotor.setNeutralMode(NeutralMode.Brake);
    frontRightMotor.configOpenloopRamp(Constants.RAMP_TIME);

    backRightMotor.setNeutralMode(NeutralMode.Brake);
    backRightMotor.configOpenloopRamp(Constants.RAMP_TIME);
  }

  public void robotDrive(double driveSpeedAxis, double driveTurnAxis) {
    robotDrive.arcadeDrive(driveSpeedAxis, driveTurnAxis);
  }
}