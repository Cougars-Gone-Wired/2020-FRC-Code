package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Shoot extends CommandBase {

    @Override
    public void execute() {
        Robot.shooter.setPIDShooting();
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.setNotMoving();
    }

}