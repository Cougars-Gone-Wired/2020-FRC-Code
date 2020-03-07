package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.ArmDown;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.ShootPID;
import frc.robot.commands.ShootVoltage;

public class OfflineAndShoot extends SequentialCommandGroup {

    public OfflineAndShoot() {
        addCommands(
            new ArmDown(),
            new ParallelRaceGroup(new ProfileDrive(Robot.drive).getProfilingCommand(3),
            new ShootVoltage(.4)
            ),
            new ShootPID(10000).withTimeout(5)
        );
    }
}