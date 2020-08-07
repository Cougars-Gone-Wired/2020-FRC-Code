package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

// the drive train system
public class Drive extends SubsystemBase{
    public static double DRIVE_SPEED = 0.9;
    public static double TURN_SPEED = 0.7;

    private WPI_TalonFX frontLeftMotor;
    private WPI_TalonFX middleLeftMotor;
    private WPI_TalonFX backLeftMotor;

    private WPI_TalonFX frontRightMotor;
    private WPI_TalonFX middleRightMotor;
    private WPI_TalonFX backRightMotor;

    private SpeedControllerGroup leftMotors;
    private SpeedControllerGroup rightMotors;

    private DifferentialDrive robotDrive;

    private Encoders encoders;
    private Gyro gyro;

    private DifferentialDriveKinematics driveKinematics; // used to convert single velocity to velocity compenents for left and right side
    private DifferentialDriveOdometry driveOdomentry; // used to store and update complete location info (2 dimensional position and rotation)

    public Drive() {
        frontLeftMotor = new WPI_TalonFX(Constants.FRONT_LEFT_MOTOR_ID);
        middleLeftMotor = new WPI_TalonFX(Constants.MIDDLE_LEFT_MOTOR_ID);
        backLeftMotor = new WPI_TalonFX(Constants.BACK_LEFT_MOTOR_ID);

        frontRightMotor = new WPI_TalonFX(Constants.FRONT_RIGHT_MOTOR_ID);
        middleRightMotor = new WPI_TalonFX(Constants.MIDDLE_RIGHT_MOTOR_ID);
        backRightMotor = new WPI_TalonFX(Constants.BACK_RIGHT_MOTOR_ID);

        leftMotors = new SpeedControllerGroup(frontLeftMotor, middleLeftMotor, backLeftMotor);
        rightMotors = new SpeedControllerGroup(frontRightMotor, middleRightMotor, backRightMotor);

        encoders = new Encoders(this);
        gyro = new Gyro();

        initMotors();

        robotDrive = new DifferentialDrive(leftMotors, rightMotors);
        robotDrive.setDeadband(Constants.DEADZONE);
        robotDrive.setSafetyEnabled(false); // to get rid of pesky warnings

        driveKinematics = new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);
        driveOdomentry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(gyro.getHeading()));

        initalize();
    }

    public void initMotors() {
        // set to zero so motors don't take time to ramp up in teleop
        frontLeftMotor.configOpenloopRamp(0);
        middleLeftMotor.configOpenloopRamp(0);
        backLeftMotor.configOpenloopRamp(0);

        frontRightMotor.configOpenloopRamp(0);
        middleRightMotor.configOpenloopRamp(0);
        backRightMotor.configOpenloopRamp(0);

        // ramping parameter nonzero so that wheels don't slip and screw up position data for profiling
        frontLeftMotor.configClosedloopRamp(.1);
        middleLeftMotor.configClosedloopRamp(.1);
        backLeftMotor.configClosedloopRamp(.1);

        frontRightMotor.configClosedloopRamp(.1);
        middleRightMotor.configClosedloopRamp(.1);
        backRightMotor.configClosedloopRamp(.1);
    }

    public void initalize() {
        middleLeftMotor.set(0);
        frontLeftMotor.set(0);
        backLeftMotor.set(0);

        middleRightMotor.set(0);
        frontRightMotor.set(0);
        backRightMotor.set(0);

        resetOdometry(new Pose2d());

        currentDriveState = DriveStates.SHOOTER_SIDE;
    }

    // variable front of the robot, with a push of a button the "forward" direction switches sides
    public enum DriveStates {
        SHOOTER_SIDE, INTAKE_SIDE
    }

    private DriveStates currentDriveState;

    // for teleop driving
    public void robotDrive(double driveSpeedAxis, double driveTurnAxis, boolean toggle) {
        if (!Robot.limelight.isDriveNotMoving()) { // if vision is positioning the robot, axis data isn't given to the drive train
            driveSpeedAxis = 0;
            driveTurnAxis = 0;
        } else {
            driveSpeedAxis *= DRIVE_SPEED;
            driveTurnAxis *= TURN_SPEED;
        }

        // switching which side of the robot is the "front"
        switch (currentDriveState) {
            case SHOOTER_SIDE:
                robotDrive.arcadeDrive(driveSpeedAxis, -driveTurnAxis); // turn axis always inverted for arcade drive
                if (toggle) {
                    setIntakeSide();
                }
                break;

            case INTAKE_SIDE:
                robotDrive.arcadeDrive(-driveSpeedAxis, -driveTurnAxis);
                if (toggle) {
                    setShooterSide();
                }
                break;
        }
    }

    public boolean isShooterSide() {
        return currentDriveState == DriveStates.SHOOTER_SIDE;
    }
    
    public boolean isIntakeSide() {
        return currentDriveState == DriveStates.INTAKE_SIDE;
    }

    public void setShooterSide() {
        currentDriveState = DriveStates.SHOOTER_SIDE;
    }

    public void setIntakeSide() {
        currentDriveState = DriveStates.INTAKE_SIDE;
    }

    // for aiming
    public void limelightDrive(double driveSpeedAxis, double driveTurnAxis) {
        robotDrive.arcadeDrive(driveSpeedAxis, -driveTurnAxis);
    }

    // for bang bang
    public void driveStraight(double speed) {
        robotDrive.curvatureDrive(speed, 0, false);
    }

    // for motion profiling
    // controls each side of the drive train directly with voltages
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftMotors.setVoltage(leftVolts);
        rightMotors.setVoltage(-rightVolts);
    }

    // for continually updating position of robot during auto
    @Override
    public void periodic() {
        driveOdomentry.update(Rotation2d.fromDegrees(gyro.getHeading()), encoders.getLeftEncodersMeters(), encoders.getRightEncodersMeters());
    }

    // resets the odomentry to a specified position
    public void resetOdometry(Pose2d position) {
        resetSensors();
        driveOdomentry.resetPosition(position, Rotation2d.fromDegrees(gyro.getHeading()));
    }

    public void resetSensors() {
        encoders.resetEncoders();
        gyro.zeroHeading();
    }

    public void setMotorsBrake() {
        frontLeftMotor.setNeutralMode(NeutralMode.Brake);
        middleLeftMotor.setNeutralMode(NeutralMode.Brake);
        backLeftMotor.setNeutralMode(NeutralMode.Brake);
        frontRightMotor.setNeutralMode(NeutralMode.Brake);
        middleRightMotor.setNeutralMode(NeutralMode.Brake);
        backRightMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setMotorsCoast() {
        frontLeftMotor.setNeutralMode(NeutralMode.Coast);
        middleLeftMotor.setNeutralMode(NeutralMode.Coast);
        backLeftMotor.setNeutralMode(NeutralMode.Coast);
        frontRightMotor.setNeutralMode(NeutralMode.Coast);
        middleRightMotor.setNeutralMode(NeutralMode.Coast);
        backRightMotor.setNeutralMode(NeutralMode.Coast);
    }

    public WPI_TalonFX getFrontLeftMotor() {
        return frontLeftMotor;
    }
    public WPI_TalonFX getMiddleLeftMotor() {
        return middleLeftMotor;
    }
    public WPI_TalonFX getBackLeftMotor() {
        return backLeftMotor;
    }
    public WPI_TalonFX getFrontRightMotor() {
        return frontRightMotor;
    }
    public WPI_TalonFX getMiddleRightMotor() {
        return middleRightMotor;
    }
    public WPI_TalonFX getBackRightMotor() {
        return backRightMotor;
    }

    // returns estimated x and y position of robot in meters
    public Pose2d getPosition() {
        return driveOdomentry.getPoseMeters();
    }

    // used for motion profiling
    public DifferentialDriveKinematics getDriveKinematics() {
        return driveKinematics;
    }

    public Encoders getEncoders() {
        return encoders;
    }

    // used for testing purposes
    public void dashboard() {
        encoders.dashboard();
        // gyro.dashboard();
        SmartDashboard.putNumber("Odometry X", driveOdomentry.getPoseMeters().getTranslation().getX());
        // SmartDashboard.putNumber("Odometry Y", driveOdomentry.getPoseMeters().getTranslation().getY());

        // SmartDashboard.putNumber("Front Left Voltage", frontLeftMotor.getMotorOutputVoltage());
        // SmartDashboard.putNumber("Middle Left Voltage", middleLeftMotor.getMotorOutputVoltage());
        // SmartDashboard.putNumber("Back Left Voltage", backLeftMotor.getMotorOutputVoltage());

        // SmartDashboard.putNumber("Front Right Voltage", frontRightMotor.getMotorOutputVoltage());
        // SmartDashboard.putNumber("Middle Right Voltage", middleRightMotor.getMotorOutputVoltage());
        // SmartDashboard.putNumber("Back Right Voltage", backRightMotor.getMotorOutputVoltage());
    }
}
