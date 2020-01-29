package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPrograms {

    private DifferentialDrive robotDrive;
    private SendableChooser<Programs> autoChooser = new SendableChooser<>(); // sendable chooser to hold dead reckoning
                                                                             // auto

    public enum Programs {
        DO_NOTHING, DEAD_RECKONING, RECORDER, LIMELIGHT_AI
    }

    public AutoPrograms(Robot robot) {

        robotDrive = Robot.drive.getDifferentialDrive();
    }

    public void initalizeChooser() {
        autoChooser.addOption("Do Nothing", Programs.DO_NOTHING);
        autoChooser.addOption("Dead Reckoning", Programs.DEAD_RECKONING);
        autoChooser.addOption("Recorder", Programs.RECORDER);
        autoChooser.addOption("Limelight program", Programs.LIMELIGHT_AI);
        SmartDashboard.putData("Auto choices", autoChooser);
    }

    public void pickAuto() {
        Programs selectedAuto = autoChooser.getSelected();

        switch (selectedAuto) {
        case DO_NOTHING:
            break;
        case DEAD_RECKONING:
            
            robotDrive.curvatureDrive(1, 0, false);
            break;
        case RECORDER:

        case LIMELIGHT_AI:

        }

    }
}