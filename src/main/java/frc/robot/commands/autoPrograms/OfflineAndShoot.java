package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.TrajectoryBuilder;
import frc.robot.commands.ArmDown;
import frc.robot.commands.IdleShooterSpin;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.ShootPID;

// starting pose - directly in front of port with front bumper on line
public class OfflineAndShoot extends SequentialCommandGroup {

    public OfflineAndShoot() {
        addCommands(
            new ArmDown(),
            new ParallelRaceGroup(
                new ProfileDrive(Robot.drive).getProfilingCommand(TrajectoryBuilder.Paths.OFFLINE_SHOOTING_POSE),
                new IdleShooterSpin(.5)),
            new ShootPID(.5).withTimeout(5)
        );
    }
}