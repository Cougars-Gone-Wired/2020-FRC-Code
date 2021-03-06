package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;

// class used for all drive encoder related data
public class Encoders {
    /* 
    *  very important to implement encoders as done in this class instead of using sensor collections if using motion profiling,
    *  sensor collections update at a slower rate than required by the profiling code, so encoder positions should be found directly 
    *  off of motor controllers
    */ 
    
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

    // methods that get raw values from each encoder
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

    // methods that average the raw valies from the encoders
    public double getRawLeftEncoders() {
        return avgSensors(getFrontLeftEncoder(), getMidLeftEncoder(), getBackLeftEncoder());
    }
    public double getRawRightEncoders() {
        return avgSensors(getFrontRightEncoder(), getMidRightEncoder(), getBackRightEncoder());
    }
    public double getAvgRawEncoders() {
        return (Math.abs(getRawLeftEncoders()) + Math.abs(getRawRightEncoders())) / 2.0;
    }

    // methods that convert avergaed encoder values to meters
    public double getLeftEncodersMeters() {
        return ticksToMeters(getRawLeftEncoders()) * (DriveConstants.areLeftEncodersReversed ? -1.0 : 1.0);
    }
    public double getRightEncodersMeters() {
        return ticksToMeters(getRawRightEncoders()) * (DriveConstants.areRightEncodersReversed ? -1.0 : 1.0);
    }
    public double getAvgEncoderMetersAvg() {
        return (getLeftEncodersMeters() + getRightEncodersMeters()) / 2;
    }

    // methods that return the average motor velocity in meters per second for each side
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

    // takes the average of three values
    public double avgSensors(double sensor1, double sensor2, double sensor3) {
        return (sensor1 + sensor2 + sensor3) / 3.0;
    }

    // converts raw encoder ticks to meters
    public double ticksToMeters(double ticks) {
        return ticks * DriveConstants.DISTANCE_PER_TICK;
    }

    // used for testing purposes to ensure encoders and conversions are working properly
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