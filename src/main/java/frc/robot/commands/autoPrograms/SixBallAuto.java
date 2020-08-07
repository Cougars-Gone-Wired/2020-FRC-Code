package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.TrajectoryBuilder;
import frc.robot.commands.Aim;
import frc.robot.commands.ArmDown;
import frc.robot.commands.IdleShooterSpin;
import frc.robot.commands.IntakeArmDown;
import frc.robot.commands.IntakeBalls;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.ShootPID;
import frc.robot.commands.Unaim;

// starting pose - directly in front of trench, shooter facing wall, front bumper on line
public class SixBallAuto extends SequentialCommandGroup {

    // puts shooter arm down, puts intake arm down, drives back into trench and intakes first ball in trench, aims, and shoots
    // umains, drives back and intakes rest of balls in trench, aims, and shoots
    public SixBallAuto() {
        addCommands(
            new ArmDown(),
            new IntakeArmDown().withTimeout(1),
            new ParallelRaceGroup(
                new SequentialCommandGroup(
                    new ParallelRaceGroup(
                        new ProfileDrive(Robot.drive).getProfilingCommand(TrajectoryBuilder.Paths.TWO_METERS),
                        new IntakeBalls()),
                    new Aim()),
                new IdleShooterSpin()),
            new ShootPID().withTimeout(5),
            new ParallelRaceGroup(
                new SequentialCommandGroup(
                    new Unaim(),
                    new ParallelRaceGroup(
                        new ProfileDrive(Robot.drive).getProfilingCommand(TrajectoryBuilder.Paths.TWO_METERS),
                        new IntakeBalls()),
                    new Aim()),
                new IdleShooterSpin()),
            new ShootPID().withTimeout(5)
        );
    }
}