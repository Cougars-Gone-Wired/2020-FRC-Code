package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.autoPrograms.IntakeThroughTrench;
import frc.robot.commands.autoPrograms.ShootAndOffLine;
import frc.robot.commands.autoPrograms.ShootFromTrench;

public class AutoSelector {

    public enum Programs {
        DRIVE_OFF_LINE, SHOOT_AND_OFF_LINE, SHOOT_FROM_TRENCH, INTAKE_THROUGH_TRENCH
    }

    private SendableChooser<Programs> autoChooser = new SendableChooser<>(); // sendable chooser to hold dead reckoning

    private Programs autoPicker;

    public AutoSelector() {
        initialize();
    }

    public void initialize() {
        autoChooser.addOption("Drive off line", Programs.DRIVE_OFF_LINE);
        autoChooser.addOption("Shoot and off line", Programs.SHOOT_AND_OFF_LINE);
        autoChooser.addOption("Shoot from trench", Programs.SHOOT_FROM_TRENCH);
        autoChooser.addOption("Intake Through Trench", Programs.INTAKE_THROUGH_TRENCH);
    }

    public void chooseAuto() {
        autoPicker = autoChooser.getSelected();
    }

    public Command getAutoCommand() {

        switch (autoPicker) {
            case SHOOT_AND_OFF_LINE:
                return new ShootAndOffLine();

            case SHOOT_FROM_TRENCH:
                return new ShootFromTrench();

            case INTAKE_THROUGH_TRENCH:
                return new IntakeThroughTrench();

            case DRIVE_OFF_LINE:
                return new DriveStraight(DriveConstants.DRIVE_OFF_LINE_DISTANCE);

            default:
                return null;
        }
    }
}