package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmDown;
import frc.robot.commands.DriveBack;
import frc.robot.commands.ShootPID;

public class ShootFromLineFrontBumper extends SequentialCommandGroup {

    public ShootFromLineFrontBumper() {
        addCommands(
            new ArmDown(),
            new ShootPID(.5).withTimeout(5),
            new DriveBack(.1)
        );
    } 
}