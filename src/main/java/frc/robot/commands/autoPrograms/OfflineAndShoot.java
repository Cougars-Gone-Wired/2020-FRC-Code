package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmDown;
import frc.robot.commands.DriveStraightBack;
import frc.robot.commands.Shoot;

public class OfflineAndShoot extends SequentialCommandGroup {

    public OfflineAndShoot() {
        addCommands(
            new DriveStraightBack(1),
            new ArmDown(),
            new Shoot().withTimeout(5)
        );
    }
}