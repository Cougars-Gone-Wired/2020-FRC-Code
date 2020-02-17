package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Aim extends CommandBase {
    boolean isAimed;
    
    @Override
    public void execute() {
        isAimed = Robot.limelight.limelightAutoAim();
    }

    @Override
    public boolean isFinished() {
        if(isAimed) {
            return true;
        }
        return false;
    }
}