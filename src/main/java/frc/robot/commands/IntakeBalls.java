package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class IntakeBalls extends CommandBase {

    @Override
    public void execute() {
        Robot.intake.setIntaking();
    }

    @Override
    public void end(boolean interrupted) {
        Robot.intake.setNotMoving();
    }
}