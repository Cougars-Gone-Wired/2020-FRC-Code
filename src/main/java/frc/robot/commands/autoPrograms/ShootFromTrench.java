package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.TrajectoryBuilder;
import frc.robot.commands.Aim;
import frc.robot.commands.ArmDown;
import frc.robot.commands.IdleShooterSpin;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.ShootPID;
import frc.robot.commands.Unaim;

// starting pose - directly in front of trench, shooter facing wall, front bumper on line
public class ShootFromTrench extends SequentialCommandGroup {

    public ShootFromTrench() {
        addCommands(
            new ArmDown(),
            new ParallelRaceGroup(
                new SequentialCommandGroup(
                    new ProfileDrive(Robot.drive).getProfilingCommand(TrajectoryBuilder.Paths.TRENCH_SHOOTING_POSE),
                    new Aim()),
                new IdleShooterSpin()),
            new ShootPID().withTimeout(5),
            new Unaim()
        );
    }
}