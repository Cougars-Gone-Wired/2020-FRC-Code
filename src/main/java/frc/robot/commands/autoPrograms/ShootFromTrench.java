package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Aim;
import frc.robot.commands.ArmDown;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.ShootPID;
import frc.robot.commands.Unaim;

public class ShootFromTrench extends SequentialCommandGroup {

    public ShootFromTrench() {
        addCommands(
            new ArmDown(),
            new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/3Meters.wpilib.json"),
            new Aim(),
            new ShootPID().withTimeout(5),
            new Unaim()
        );
    }
}