package frc.robot.commands;

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
        return Robot.drive.getEncoders().getAvgEncoderMetersAvg() >= distance;
    }
}