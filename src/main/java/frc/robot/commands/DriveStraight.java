package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Drive;
import frc.robot.Robot;

public class DriveStraight extends CommandBase {

    private double distance;

    public DriveStraight(double distance) {
        this.distance = distance;
    }

    @Override
    public void execute() {
        Robot.drive.driveStraight(Drive.DRIVE_SPEED);
    }

    @Override
    public boolean isFinished() {
        if (Robot.drive.getEncoders().getAvgEncoderMetersAvg() <= distance) {
            Robot.drive.driveStraight(0);
            return true;
        }
        return false;
    }
}