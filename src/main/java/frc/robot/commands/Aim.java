package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

// command for aiming robot toward port with limelight
public class Aim extends CommandBase {
    boolean isAimed;

    @Override
    public void initialize() {
        Robot.limelight.setUnaimAngle();
    }
    
    @Override
    public void execute() {
        isAimed = Robot.limelight.limelightAutoAim();
    }

    @Override
    public boolean isFinished() {
        return isAimed;
    }
}