package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

// command for setting the intake arm down
public class IntakeArmDown extends CommandBase {

    @Override
    public void initialize() {
        Robot.arms.setDownPosition();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}