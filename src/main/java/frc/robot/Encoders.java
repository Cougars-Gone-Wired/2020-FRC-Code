package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;

public class Encoders {

    private Drive drive;

    public Encoders(Drive drive) {
        this.drive = drive;
    }

    public void resetEncoders() {
        drive.getFrontLeftMotor().setSelectedSensorPosition(0);
        drive.getMiddleLeftMotor().setSelectedSensorPosition(0);
        drive.getBackLeftMotor().setSelectedSensorPosition(0);

        drive.getFrontRightMotor().setSelectedSensorPosition(0);
        drive.getMiddleRightMotor().setSelectedSensorPosition(0);
        drive.getBackRightMotor().setSelectedSensorPosition(0);

    }

    public double getFrontLeftEncoder() {
        return drive.getFrontLeftMotor().getSelectedSensorPosition();
    }
    public double getMidLeftEncoder() {
        return drive.getMiddleLeftMotor().getSelectedSensorPosition();
    }
    public double getBackLeftEncoder() {
        return drive.getBackLeftMotor().getSelectedSensorPosition();
    }
    public double getFrontRightEncoder() {
        return drive.getFrontRightMotor().getSelectedSensorPosition();
    }
    public double getMidRightEncoder() {
        return drive.getMiddleRightMotor().getSelectedSensorPosition();
    }
    public double getBackRightEncoder() {
        return drive.getBackRightMotor().getSelectedSensorPosition();
    }

    public double getRawLeftEncoders() {
        return avgSensors(getFrontLeftEncoder(), getMidLeftEncoder(), getBackLeftEncoder());
    }
    public double getRawRightEncoders() {
        return avgSensors(getFrontRightEncoder(), getMidRightEncoder(), getBackRightEncoder());
    }
    public double getAvgRawEncoders() {
        return (Math.abs(getRawLeftEncoders()) + Math.abs(getRawRightEncoders())) / 2.0;
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
        return avgSensors(
            drive.getFrontLeftMotor().getSelectedSensorVelocity(), 
            drive.getMiddleLeftMotor().getSelectedSensorVelocity(), 
            drive.getBackLeftMotor().getSelectedSensorVelocity()) 
            * DriveConstants.METER_PER_SECOND_CONSTANT
            * (DriveConstants.areLeftEncodersReversed ? -1.0 : 1.0);
    }
    public double getRightSpeed() {
        return avgSensors(
            drive.getFrontRightMotor().getSelectedSensorVelocity(), 
            drive.getMiddleRightMotor().getSelectedSensorVelocity(), 
            drive.getBackRightMotor().getSelectedSensorVelocity()) 
            * DriveConstants.METER_PER_SECOND_CONSTANT
            * (DriveConstants.areRightEncodersReversed ? -1.0 : 1.0);
    }
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftSpeed(), getRightSpeed());
    }

    public double avgSensors(double sensor1, double sensor2, double sensor3) {
        return (sensor1 + sensor2 + sensor3) / 3.0;
    }

    public double ticksToMeters(double ticks) {
        return ticks * DriveConstants.DISTANCE_PER_TICK;
    }

    public void dashboard() {
        // SmartDashboard.putNumber("Front Left Encoder", getFrontLeftEncoder());
        // SmartDashboard.putNumber("Middle Left Encoder", getMidLeftEncoder());
        // SmartDashboard.putNumber("Back Left Encoder", getBackLeftEncoder());

        // SmartDashboard.putNumber("Front Right Encoder", getFrontRightEncoder());
        // SmartDashboard.putNumber("Middle Right Encoder", getMidRightEncoder());
        // SmartDashboard.putNumber("Back Right Encoder", getBackRightEncoder());

        // SmartDashboard.putNumber("Left Speed", getLeftSpeed());
        // SmartDashboard.putNumber("Right Speed", getRightSpeed());

        // SmartDashboard.putNumber("Left Encoder", getRawLeftEncoders());
        // SmartDashboard.putNumber("Right Encoder", getRawRightEncoders());
        SmartDashboard.putNumber("Left Meters", getLeftEncodersMeters());
        SmartDashboard.putNumber("Right Meters", getRightEncodersMeters());
        SmartDashboard.putNumber("Encoders", getAvgEncoderMetersAvg());

        // SmartDashboard.putNumber("Front Left Speed", drive.getFrontLeftMotor().getSelectedSensorVelocity() * DriveConstants.METER_PER_SECOND_CONSTANT);
        // SmartDashboard.putNumber("Middle Left Speed", drive.getMiddleLeftMotor().getSelectedSensorVelocity() * DriveConstants.METER_PER_SECOND_CONSTANT);
        // SmartDashboard.putNumber("Back Left Speed", drive.getBackLeftMotor().getSelectedSensorVelocity() * DriveConstants.METER_PER_SECOND_CONSTANT);

        // SmartDashboard.putNumber("Front Right Speed", drive.getFrontRightMotor().getSelectedSensorVelocity() * DriveConstants.METER_PER_SECOND_CONSTANT);
        // SmartDashboard.putNumber("Middle Right Speed", drive.getMiddleRightMotor().getSelectedSensorVelocity() * DriveConstants.METER_PER_SECOND_CONSTANT);
        // SmartDashboard.putNumber("Back Right Speed", drive.getBackRightMotor().getSelectedSensorVelocity() * DriveConstants.METER_PER_SECOND_CONSTANT);
    }
}