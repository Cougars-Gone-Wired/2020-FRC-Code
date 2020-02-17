package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Unaim extends CommandBase {
    boolean isUnaimed;
    
    @Override
    public void execute() {
        isUnaimed = Robot.limelight.limelightAutoAim();
    }

    @Override
    public boolean isFinished() {
        if(isUnaimed) {
            return true;
        }
        return false;
    }
}