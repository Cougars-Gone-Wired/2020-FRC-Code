package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Aim;
import frc.robot.commands.ArmDown;
import frc.robot.commands.DriveStraightBack;
import frc.robot.commands.Intake;
import frc.robot.commands.IntakeArmDown;
import frc.robot.commands.IntakeArmUp;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.Shoot;

public class FiveBallAuto extends SequentialCommandGroup{

    public FiveBallAuto() {
        addCommands(
            new ArmDown(),
            new IntakeArmDown().withTimeout(1),
            new ParallelRaceGroup(
                //new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/5Balls.wpilib.json"), 
                new DriveStraightBack(4),
                new Intake()),
            new IntakeArmUp(),
            new Aim(),
            new Shoot().withTimeout(7)
        );
    }
    
}