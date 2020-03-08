package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveForward;
import frc.robot.commands.ShootVoltage;

public class ShootFromLineBackBumper extends SequentialCommandGroup {

    public ShootFromLineBackBumper() {
        addCommands(
            new ShootVoltage(.35, true).withTimeout(5),
            new DriveForward(.1)
        );
    }
}