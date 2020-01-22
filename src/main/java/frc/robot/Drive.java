package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

// import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
    public static double DRIVE_SPEED = 0.9;
    public static double TURN_SPEED = 0.9;

    private WPI_TalonFX frontLeftMotor;
    private WPI_TalonFX middleLeftMotor;
    private WPI_TalonFX backLeftMotor;

    private WPI_TalonFX frontRightMotor;
    private WPI_TalonFX middleRightMotor;
    private WPI_TalonFX backRightMotor;

    private TalonFXSensorCollection frontLeftSensors;
    private TalonFXSensorCollection middleLeftSensors;
    private TalonFXSensorCollection backLeftSensors;

    private TalonFXSensorCollection frontRightSensors;
    private TalonFXSensorCollection middleRightSensors;
    private TalonFXSensorCollection backRightSensors;

    private DifferentialDrive robotDrive;

    public Drive() {
        frontLeftMotor = new WPI_TalonFX(Constants.FRONT_LEFT_MOTOR_ID);
        middleLeftMotor = new WPI_TalonFX(Constants.MIDDLE_LEFT_MOTOR_ID);
        backLeftMotor = new WPI_TalonFX(Constants.BACK_LEFT_MOTOR_ID);

        frontRightMotor = new WPI_TalonFX(Constants.FRONT_RIGHT_MOTOR_ID);
        middleRightMotor = new WPI_TalonFX(Constants.MIDDLE_RIGHT_MOTOR_ID);
        backRightMotor = new WPI_TalonFX(Constants.BACK_RIGHT_MOTOR_ID);

        frontLeftSensors = new TalonFXSensorCollection(frontLeftMotor);
        middleLeftSensors = new TalonFXSensorCollection(middleLeftMotor);
        backLeftSensors = new TalonFXSensorCollection(backLeftMotor);

        frontRightSensors = new TalonFXSensorCollection(frontRightMotor);
        middleRightSensors = new TalonFXSensorCollection(middleRightMotor);
        backRightSensors = new TalonFXSensorCollection(backRightMotor);

        frontLeftMotor.follow(middleLeftMotor);
        backLeftMotor.follow(frontLeftMotor);

        frontRightMotor.follow(middleRightMotor);
        backRightMotor.follow(frontRightMotor);

        initMotors();
        initalize();

        robotDrive = new DifferentialDrive(middleLeftMotor, middleRightMotor);
        robotDrive.setDeadband(Constants.DEADZONE);
        robotDrive.setSafetyEnabled(false);
    }

    public void initMotors() {
        frontLeftMotor.setNeutralMode(NeutralMode.Brake);
        frontLeftMotor.configOpenloopRamp(0);
        middleLeftMotor.setNeutralMode(NeutralMode.Brake);
        middleLeftMotor.configOpenloopRamp(0);
        backLeftMotor.setNeutralMode(NeutralMode.Brake);
        backLeftMotor.configOpenloopRamp(0);

        frontRightMotor.setNeutralMode(NeutralMode.Brake);
        frontRightMotor.configOpenloopRamp(0);
        middleRightMotor.setNeutralMode(NeutralMode.Brake);
        middleRightMotor.configOpenloopRamp(0);
        backRightMotor.setNeutralMode(NeutralMode.Brake);
        backRightMotor.configOpenloopRamp(0);
    }

    public void initalize() {
        middleLeftMotor.set(0);
        frontLeftMotor.set(0);
        backLeftMotor.set(0);

        middleRightMotor.set(0);
        frontRightMotor.set(0);
        backRightMotor.set(0);

        frontLeftSensors.setIntegratedSensorPosition(0, 10);
        middleLeftSensors.setIntegratedSensorPosition(0, 10);
        backLeftSensors.setIntegratedSensorPosition(0, 10);

        frontRightSensors.setIntegratedSensorPosition(0, 10);
        middleRightSensors.setIntegratedSensorPosition(0, 10);
        backRightSensors.setIntegratedSensorPosition(0, 10);

        driveState = DriveStates.SHOOTER_SIDE;
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

    public void setSide(boolean driveSide) {
        if (driveSide) {
            driveState = DriveStates.SHOOTER_SIDE;
        } else {
            driveState = DriveStates.INTAKE_SIDE;
        }
    }

    public void dashboard() {
        double fls = frontLeftSensors.getIntegratedSensorPosition();
        double mls = middleLeftSensors.getIntegratedSensorPosition();
        double bls = backLeftSensors.getIntegratedSensorPosition();
        double frs = frontRightSensors.getIntegratedSensorPosition();
        double mrs = middleRightSensors.getIntegratedSensorPosition();
        double brs = backRightSensors.getIntegratedSensorPosition();

        SmartDashboard.putNumber("Front Left", fls);
        SmartDashboard.putNumber("Middle Left", mls);
        SmartDashboard.putNumber("Back Left", bls);
        SmartDashboard.putNumber("Front Right", frs);
        SmartDashboard.putNumber("Middle Right", mrs);
        SmartDashboard.putNumber("Back Right", brs);

        SmartDashboard.putNumber("Left Average", (fls + mls + bls) / 3);
        SmartDashboard.putNumber("Right Average", (frs + mrs + brs) / 3);
    }
}
