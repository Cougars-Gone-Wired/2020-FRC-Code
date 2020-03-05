package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.ArmDown;
import frc.robot.commands.DriveBack;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.ShootPID;

public class OfflineAndShoot extends SequentialCommandGroup {

    public OfflineAndShoot() {
        addCommands(
            new ArmDown(),
            new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/1Meter.wpilib.json"),
            // new DriveBack(1),
            new ShootPID().withTimeout(5)
        );
    }
}