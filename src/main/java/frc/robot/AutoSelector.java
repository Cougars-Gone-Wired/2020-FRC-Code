package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Aim;
import frc.robot.commands.DoNothing;
import frc.robot.commands.DriveBack;
import frc.robot.commands.DriveForward;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.Unaim;
import frc.robot.commands.autoPrograms.OfflineAndShoot;
import frc.robot.commands.autoPrograms.ShootFromLine;
import frc.robot.commands.autoPrograms.ShootFromTrench;
import frc.robot.commands.autoPrograms.SixBallAuto;

public class AutoSelector {

    private SendableChooser<Programs> autoChooser = new SendableChooser<>();

    private Command autoCommand;

    public enum Programs {
        DO_NOTHING, SHOOT_FROM_LINE, OFFLINE_AND_SHOOT, SHOOT_FROM_TRENCH, SIX_BALL_AUTO, DRIVE_RECKONING, DRIVE_PROFILING, AIM, AIM_AND_UNAIM
    }

    private Programs autoChoice;

    public AutoSelector() {
        initialize();
    }

    public void initialize() {
        autoChooser.addOption("Do Nothing", Programs.DO_NOTHING);
        autoChooser.addOption("Shoot From Line", Programs.SHOOT_FROM_LINE);
        autoChooser.addOption("Offline and Shoot", Programs.OFFLINE_AND_SHOOT);
        autoChooser.addOption("Shoot From Trench", Programs.SHOOT_FROM_TRENCH);
        autoChooser.addOption("Six Ball Auto", Programs.SIX_BALL_AUTO);
        autoChooser.addOption("Drive Reckoning", Programs.DRIVE_RECKONING);
        autoChooser.addOption("Drive Profiling", Programs.DRIVE_PROFILING);
        autoChooser.addOption("Aim", Programs.AIM);
        autoChooser.addOption("Aim and Unaim", Programs.AIM_AND_UNAIM);
        SmartDashboard.putData("Auto", autoChooser);
    }

    public Command getAutoCommand() {
        autoChoice = autoChooser.getSelected();

        switch (autoChoice) {
            case DO_NOTHING:
                autoCommand = new DoNothing();
                break;

            case SHOOT_FROM_LINE:
                autoCommand = new ShootFromLine();
                break;

            case OFFLINE_AND_SHOOT:
                autoCommand = new OfflineAndShoot();
                break;

            case SHOOT_FROM_TRENCH:
                autoCommand = new ShootFromTrench();
                break;

            case SIX_BALL_AUTO:
                autoCommand = new SixBallAuto();

            case DRIVE_RECKONING:
                autoCommand = new DriveBack(1);
                // autoCommand = new DriveForward(1);
                break;

            case DRIVE_PROFILING:
                autoCommand = new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/1Meter.wpilib.json");
                break;

            case AIM:
                autoCommand = new Aim();
                break;

            case AIM_AND_UNAIM:
                autoCommand = new SequentialCommandGroup(new Aim(), new Unaim());
                break;
        }

        return autoCommand;
    }
}