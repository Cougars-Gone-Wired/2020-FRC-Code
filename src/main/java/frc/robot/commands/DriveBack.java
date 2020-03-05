package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Constants.DriveConstants;

public class DriveBack extends CommandBase {

    private double distance;

    public DriveBack(double distance) {
        this.distance = distance;
    }

    @Override
    public void execute() {
        Robot.drive.driveStraight(DriveConstants.AUTO_DRIVE_SPEED);
    }

    @Override
    public boolean isFinished() {
        if (Robot.drive.getEncoders().getAvgEncoderMetersAvg() >= distance) {
            Robot.drive.driveStraight(0);
            return true;
        }
        return false;
    }
}