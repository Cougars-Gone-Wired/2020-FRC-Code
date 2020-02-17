package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import frc.robot.Constants.DriveConstants;

public class Encoders {

    private TalonFXSensorCollection frontLeftSensors;
    private TalonFXSensorCollection middleLeftSensors;
    private TalonFXSensorCollection backLeftSensors;

    private TalonFXSensorCollection frontRightSensors;
    private TalonFXSensorCollection middleRightSensors;
    private TalonFXSensorCollection backRightSensors;

    public Encoders(Drive drive) {
        frontLeftSensors = new TalonFXSensorCollection(drive.getFrontLeftMotor());
        middleLeftSensors = new TalonFXSensorCollection(drive.getMiddleLeftMotor());
        backLeftSensors = new TalonFXSensorCollection(drive.getBackLeftMotor());

        frontRightSensors = new TalonFXSensorCollection(drive.getFrontRightMotor());
        middleRightSensors = new TalonFXSensorCollection(drive.getMiddleRightMotor());
        backRightSensors = new TalonFXSensorCollection(drive.getBackRightMotor());
    }

    public void resetEncoders() {
        frontLeftSensors.setIntegratedSensorPosition(0, 10);
        middleLeftSensors.setIntegratedSensorPosition(0, 10);
        backLeftSensors.setIntegratedSensorPosition(0, 10);

        frontRightSensors.setIntegratedSensorPosition(0, 10);
        middleRightSensors.setIntegratedSensorPosition(0, 10);
        backRightSensors.setIntegratedSensorPosition(0, 10);
    }

    public double getFrontLeftEncoder() {
        return frontLeftSensors.getIntegratedSensorPosition();
    }
    public double getMidLeftEncoder() {
        return middleLeftSensors.getIntegratedSensorPosition();
    }
    public double getBackLeftEncoder() {
        return backLeftSensors.getIntegratedSensorPosition();
    }
    public double getFrontRightEncoder() {
        return frontRightSensors.getIntegratedSensorPosition();
    }
    public double getMidRightEncoder() {
        return middleRightSensors.getIntegratedSensorPosition();
    }
    public double getBackRightEncoder() {
        return backRightSensors.getIntegratedSensorPosition();
    }

    public double getRawLeftEncoders() {
        return avgSensors(getFrontLeftEncoder(), getMidLeftEncoder(), getBackLeftEncoder());
    }
    public double getRawRightEncoders() {
        return avgSensors(getFrontRightEncoder(), getMidRightEncoder(), getBackRightEncoder());
    }
    public double getAvgRawEncoders() {
        return (Math.abs(getRawLeftEncoders()) + Math.abs(getRawRightEncoders())) / 2;
    }

    public double getLeftEncodersMeters() {
        return ticksToMeters(getRawLeftEncoders()) * (DriveConstants.areLeftEncodersReversed ? -1.0 : 1.0);
    }
    public double getRightEncodersMeters() {
        return ticksToMeters(getRawRightEncoders()) * (DriveConstants.areRightEncodersReversed ? -1.0 : 1.0);
    }
    public double getAvgEncoderMetersAvg() {
        return (getLeftEncodersMeters() + getRightEncodersMeters()) / 2;
    }

    public double getLeftSpeed() {
        return avgSensors(frontLeftSensors.getIntegratedSensorVelocity(), middleLeftSensors.getIntegratedSensorVelocity(), backLeftSensors.getIntegratedSensorVelocity()) * DriveConstants.METER_PER_SECOND_CONSTANT;
    }
    public double getRightSpeed() {
        return avgSensors(frontRightSensors.getIntegratedSensorVelocity(), middleRightSensors.getIntegratedSensorVelocity(), backRightSensors.getIntegratedSensorVelocity()) * DriveConstants.METER_PER_SECOND_CONSTANT;
    }
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftSpeed(), getRightSpeed());
    }

    public double avgSensors(double sensor1, double sensor2, double sensor3) {
        return (sensor1 + sensor2 + sensor3)/3;
    }

    public double ticksToMeters(double ticks) {
        return ticks * DriveConstants.DISTANCE_PER_TICK;
    }
}