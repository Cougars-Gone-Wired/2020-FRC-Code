package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

// command for setting big arm in shooting position
public class ArmDown extends CommandBase {

    @Override
    public void initialize() {
        Robot.arms.setShootingPostion();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}