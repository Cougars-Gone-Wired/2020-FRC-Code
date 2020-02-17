package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class IntakeArmUp extends CommandBase {

    @Override
    public void initialize() {
        Robot.arms.setUpPosition();;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}