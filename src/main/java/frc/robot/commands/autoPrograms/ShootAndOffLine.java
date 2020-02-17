package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.Shoot;

public class ShootAndOffLine extends SequentialCommandGroup {

    public ShootAndOffLine() {
        addCommands(
            new Shoot().withTimeout(5),
            new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/1MeterBack.wpilib.json")
        );
    }

}