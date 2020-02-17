package frc.robot.commands.autoPrograms;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Intake;
import frc.robot.commands.IntakeArmDown;
import frc.robot.commands.IntakeArmUp;
import frc.robot.commands.ProfileDrive;

public class IntakeThroughTrench extends SequentialCommandGroup {

    public IntakeThroughTrench() {
        addCommands(
            new IntakeArmDown(),
            new ParallelRaceGroup(
                new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/ThroughTrench.wpilib.json"), 
                new Intake()),
            new IntakeArmUp()
        );
    }
}