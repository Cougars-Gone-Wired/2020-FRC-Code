package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
// import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
    public static double DRIVE_SPEED = 0.9;
    public static double TURN_SPEED = 0.9;

    private WPI_TalonFX frontLeftMotor;
    private WPI_TalonFX midLeftMotor;
    private WPI_TalonFX backLeftMotor;

    private WPI_TalonFX frontRightMotor;
    private WPI_TalonFX midRightMotor;
    private WPI_TalonFX backRightMotor;

    private TalonFXSensorCollection frontLeftSensors;
    private TalonFXSensorCollection midLeftSensors;
    private TalonFXSensorCollection backLeftSensors;

    private TalonFXSensorCollection frontRightSensors;
    private TalonFXSensorCollection midRightSensors;
    private TalonFXSensorCollection backRightSensors;

    private SpeedControllerGroup leftMotors;
    private SpeedControllerGroup rightMotors;

    private DifferentialDrive robotDrive;

    public Drive() {
        frontLeftMotor = new WPI_TalonFX(Constants.FRONT_LEFT_MOTOR_ID);
        midLeftMotor = new WPI_TalonFX(Constants.MIDDLE_LEFT_MOTOR_ID);
        backLeftMotor = new WPI_TalonFX(Constants.BACK_LEFT_MOTOR_ID);

        frontRightMotor = new WPI_TalonFX(Constants.FRONT_RIGHT_MOTOR_ID);
        midRightMotor = new WPI_TalonFX(Constants.MIDDLE_RIGHT_MOTOR_ID);
        backRightMotor = new WPI_TalonFX(Constants.BACK_RIGHT_MOTOR_ID);

        frontLeftSensors = new TalonFXSensorCollection(frontLeftMotor);
        midLeftSensors = new TalonFXSensorCollection(midLeftMotor);
        backLeftSensors = new TalonFXSensorCollection(backLeftMotor);

        frontRightSensors = new TalonFXSensorCollection(frontRightMotor);
        midRightSensors = new TalonFXSensorCollection(midRightMotor);
        backRightSensors = new TalonFXSensorCollection(backRightMotor);

        leftMotors = new SpeedControllerGroup(frontLeftMotor, midLeftMotor, backLeftMotor);
        rightMotors = new SpeedControllerGroup(frontRightMotor, midRightMotor, backRightMotor);

        // frontLeftMotor.follow(middleLeftMotor);
        // backLeftMotor.follow(middleLeftMotor);

        // frontRightMotor.follow(middleRightMotor);
        // backRightMotor.follow(middleRightMotor);

        initMotors();
        initalize();

        robotDrive = new DifferentialDrive(leftMotors, rightMotors);
        robotDrive.setDeadband(Constants.DEADZONE);
        robotDrive.setSafetyEnabled(false);
    }

    public void initMotors() {
        frontLeftMotor.setNeutralMode(NeutralMode.Brake);
        frontLeftMotor.configOpenloopRamp(0);
        midLeftMotor.setNeutralMode(NeutralMode.Brake);
        midLeftMotor.configOpenloopRamp(0);
        backLeftMotor.setNeutralMode(NeutralMode.Brake);
        backLeftMotor.configOpenloopRamp(0);

        frontRightMotor.setNeutralMode(NeutralMode.Brake);
        frontRightMotor.configOpenloopRamp(0);
        midRightMotor.setNeutralMode(NeutralMode.Brake);
        midRightMotor.configOpenloopRamp(0);
        backRightMotor.setNeutralMode(NeutralMode.Brake);
        backRightMotor.configOpenloopRamp(0);
    }

    public void initalize() {
        midLeftMotor.set(0);
        frontLeftMotor.set(0);
        backLeftMotor.set(0);

        midRightMotor.set(0);
        frontRightMotor.set(0);
        backRightMotor.set(0);

        resetSensors();

        currentDriveState = DriveStates.SHOOTER_SIDE;
    }

    public enum DriveStates {
        SHOOTER_SIDE, INTAKE_SIDE
    }

    private DriveStates currentDriveState;

    public void robotDrive(double driveSpeedAxis, double driveTurnAxis, boolean toggle) {
        driveSpeedAxis = driveSpeedAxis * DRIVE_SPEED;
        driveTurnAxis = driveTurnAxis * TURN_SPEED;

        switch (currentDriveState) {
            case SHOOTER_SIDE:
                robotDrive.arcadeDrive(driveSpeedAxis, -driveTurnAxis);
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

    public DifferentialDrive getDifferentialDrive() {
        return robotDrive;
    }

    public void driveStraight(double speed) {
        robotDrive.curvatureDrive(speed, 0, false);
    }

    //NOTE: I kinda wanna move this to its own class, I think it doesn't belong in the drive class. - Judd
    public double getFrontLeftSensors() {
        return frontLeftSensors.getIntegratedSensorPosition();
    }
    public double getMidLeftSensors() {
        return midLeftSensors.getIntegratedSensorPosition();
    }
    public double getBackLeftSensors() {
        return backLeftSensors.getIntegratedSensorPosition();
    }
    public double getFrontRightSensors() {
        return frontRightSensors.getIntegratedSensorPosition();
    }
    public double getMidRightSensors() {
        return midRightSensors.getIntegratedSensorPosition();
    }
    public double getBackRightSensors() {
        return backRightSensors.getIntegratedSensorPosition();
    }

    public double getLeftSensors() {
        return avgSensorGroup(getFrontLeftSensors(), getMidLeftSensors(), getBackLeftSensors());
    }
    public double getRightSensors() {
        return avgSensorGroup(getFrontRightSensors(), getMidRightSensors(), getBackRightSensors());
    }

    public double getSensorAvg() {
        return (Math.abs(getLeftSensors()) + Math.abs(getRightSensors()))/2;
    }
    public void resetSensors() {
        frontLeftSensors.setIntegratedSensorPosition(0, 10);
        midLeftSensors.setIntegratedSensorPosition(0, 10);
        backLeftSensors.setIntegratedSensorPosition(0, 10);

        frontRightSensors.setIntegratedSensorPosition(0, 10);
        midRightSensors.setIntegratedSensorPosition(0, 10);
        backRightSensors.setIntegratedSensorPosition(0, 10);
    }


    public void dashboard() {
        double fls = getFrontLeftSensors();
        double mls = getMidLeftSensors();
        double bls = getBackLeftSensors();
        double frs = getFrontRightSensors();
        double mrs = getMidRightSensors();
        double brs = getBackRightSensors();

        SmartDashboard.putNumber("Front Left", ticksToInches(fls));
        SmartDashboard.putNumber("Middle Left", ticksToInches(mls));
        SmartDashboard.putNumber("Back Left", ticksToInches(bls));
        SmartDashboard.putNumber("Front Right", ticksToInches(frs));
        SmartDashboard.putNumber("Middle Right", ticksToInches(mrs));
        SmartDashboard.putNumber("Back Right", ticksToInches(brs));

        SmartDashboard.putNumber("Left Average", ticksToInches(getLeftSensors()));
        SmartDashboard.putNumber("Right Average", ticksToInches(getRightSensors()));
        SmartDashboard.putNumber("All the sensors", ticksToInches(getSensorAvg()));
    }

    public double avgSensorGroup(double sensor1, double sensor2, double sensor3) {
        return (sensor1 + sensor2 + sensor3)/3;
    }

    public double ticksToInches(double ticks) {
        return (9 * Math.PI * ticks) / 16384;
    }
}
