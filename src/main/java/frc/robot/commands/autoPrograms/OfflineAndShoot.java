package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmDown;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.Shoot;

public class OfflineAndShoot extends SequentialCommandGroup {

    public OfflineAndShoot() {
        addCommands(
            new DriveStraight(-2),
            new ArmDown(),
            new Shoot().withTimeout(10)
        );
    }
}