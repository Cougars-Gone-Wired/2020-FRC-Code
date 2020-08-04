package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

// command for resetting robot to original unaimed position after limelight had aimed the robot
public class Unaim extends CommandBase {
    boolean isUnaimed;
    
    @Override
    public void execute() {
        isUnaimed = Robot.limelight.limelightAutoUnaim();
    }

    @Override
    public boolean isFinished() {
        return isUnaimed;
    }
}