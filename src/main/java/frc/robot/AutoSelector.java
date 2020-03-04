package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DoNothing;
import frc.robot.commands.DriveStraightBack;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.autoPrograms.FiveBallAuto;
import frc.robot.commands.autoPrograms.IntakeThroughTrench;
import frc.robot.commands.autoPrograms.OfflineAndShoot;
import frc.robot.commands.autoPrograms.ShootAndOffLine;
import frc.robot.commands.autoPrograms.ShootFromTrench;

public class AutoSelector {

    private SendableChooser<Programs> autoChooser = new SendableChooser<>();

    private Command autoCommand;

    public enum Programs {
        DRIVE_OFF_LINE, OFFLINE_AND_SHOOT, SHOOT_FROM_TRENCH, FIVE_BALL, INTAKE_THROUGH_TRENCH, DRIVE_STRAIGHT, DO_NOTHING
    }

    private Programs autoChoice;

    public AutoSelector() {
        initialize();
    }

    public void initialize() {
        autoChooser.addOption("Drive off line", Programs.DRIVE_OFF_LINE);
        autoChooser.addOption("Shoot and off line", Programs.OFFLINE_AND_SHOOT);
        autoChooser.addOption("Shoot from trench", Programs.SHOOT_FROM_TRENCH);
        autoChooser.addOption("Five Ball", Programs.FIVE_BALL);
        autoChooser.addOption("Intake Through Trench", Programs.INTAKE_THROUGH_TRENCH);
        autoChooser.addOption("Drive Straight", Programs.DRIVE_STRAIGHT);
        autoChooser.addOption("Do Nothing", Programs.DO_NOTHING);
        SmartDashboard.putData("Auto", autoChooser);
    }

    public Command getAutoCommand() {
        autoChoice = autoChooser.getSelected();

        switch (autoChoice) {
            case OFFLINE_AND_SHOOT:
                autoCommand = new OfflineAndShoot();
                break;

            case SHOOT_FROM_TRENCH:
                autoCommand = new ShootFromTrench();
                break;

            case FIVE_BALL:
                autoCommand = new FiveBallAuto();
                break;

            case INTAKE_THROUGH_TRENCH:
                autoCommand = new IntakeThroughTrench();
                break;

            case DRIVE_OFF_LINE:
                autoCommand = new DriveStraightBack(1);
                break;

            case DRIVE_STRAIGHT:
                autoCommand = new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/1Meter.wpilib.json");
                break;

            case DO_NOTHING:
                autoCommand = new DoNothing();
                break;
        }

        return autoCommand;
    }
}