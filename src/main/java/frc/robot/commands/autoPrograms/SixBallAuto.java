package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Aim;
import frc.robot.commands.ArmDown;
import frc.robot.commands.IntakeArmDown;
import frc.robot.commands.IntakeBalls;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.ShootPID;
import frc.robot.commands.Unaim;

public class SixBallAuto extends SequentialCommandGroup {

    public SixBallAuto() {
        addCommands(
            new ArmDown(),
            new IntakeArmDown().withTimeout(1),
            new ParallelRaceGroup(
                new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/3Meters.wpilib.json"),
                new IntakeBalls()),
            new Aim(),
            new ShootPID().withTimeout(5),
            new Unaim(),
            new ParallelRaceGroup(
                new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/2Meters.wpilib.json"),
                new IntakeBalls()),
            new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/2MetersBack.wpilib.json"),
            new Aim(),
            new ShootPID().withTimeout(5)
        );
    }
}