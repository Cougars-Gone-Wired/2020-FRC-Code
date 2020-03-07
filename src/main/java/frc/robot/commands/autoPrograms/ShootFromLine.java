package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveForward;
import frc.robot.commands.ShootVoltage;

public class ShootFromLine extends SequentialCommandGroup {

    public ShootFromLine() {
        addCommands(
            new ShootVoltage(.35).withTimeout(5),
            new DriveForward(.25)
        );
    }
}