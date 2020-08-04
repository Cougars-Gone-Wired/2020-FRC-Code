package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

// command for do nothing auto - important to have just in case
public class DoNothing extends CommandBase {

    @Override
    public boolean isFinished() {
        return true;
    }
}