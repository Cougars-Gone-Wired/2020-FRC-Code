package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Aim;
import frc.robot.commands.ArmDown;
import frc.robot.commands.DoNothing;
import frc.robot.commands.DriveBack;
import frc.robot.commands.DriveForward;
import frc.robot.commands.ProfileDrive;
import frc.robot.commands.Unaim;
import frc.robot.commands.autoPrograms.OfflineAndShoot;
import frc.robot.commands.autoPrograms.ShootFromLineBackBumper;
import frc.robot.commands.autoPrograms.ShootFromLineFrontBumper;
import frc.robot.commands.autoPrograms.ShootFromTrench;
import frc.robot.commands.autoPrograms.SixBallAuto;

public class AutoSelector {

    private SendableChooser<Programs> autoChooser = new SendableChooser<>();

    private Command autoCommand;

    public enum Programs {
        DO_NOTHING, SHOOT_FROM_LINE_BACK_BUMPER, SHOOT_FROM_LINE_FRONT_BUMPER, OFFLINE_AND_SHOOT, SHOOT_FROM_TRENCH, SIX_BALL_AUTO, DRIVE_RECKONING, DRIVE_PROFILING, AIM, AIM_AND_UNAIM
    }

    private Programs autoChoice;

    public AutoSelector() {
        initialize();
    }

    public void initialize() {
        autoChooser.addOption("Do Nothing", Programs.DO_NOTHING);
        autoChooser.addOption("Shoot From Line", Programs.SHOOT_FROM_LINE_BACK_BUMPER);
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

            case SHOOT_FROM_LINE_BACK_BUMPER:
                autoCommand = new ShootFromLineBackBumper();
                break;

            case SHOOT_FROM_LINE_FRONT_BUMPER:
                autoCommand = new ShootFromLineFrontBumper();

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
                autoCommand = new ProfileDrive(Robot.drive).getProfilingCommand("paths/output/TrenchShootingPose.wpilib.json");
                break;

            case AIM:
                autoCommand = new SequentialCommandGroup(new ArmDown().withTimeout(5), new Aim());
                break;

            case AIM_AND_UNAIM:
                autoCommand = new SequentialCommandGroup(new ArmDown().withTimeout(5), new Aim().withTimeout(5), new Unaim());
                break;
        }

        return autoCommand;
    }
}