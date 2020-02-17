package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.autoPrograms.IntakeThroughTrench;
import frc.robot.commands.autoPrograms.ShootAndOffLine;
import frc.robot.commands.autoPrograms.ShootFromTrench;

public class AutoSelector {

    private SendableChooser<String> autoChooser = new SendableChooser<>();    

    private final String SHOOT_AND_OFF_LINE = "Shoot and off line";
    private final String SHOOT_FROM_TRENCH = "Shoot from trench";
    private final String INTAKE_THROUGH_TRENCH = "Intake Through Trench";

    private String autoPicker;

    public AutoSelector() {
        initialize();
    }

    public void initialize() {
        autoChooser.addOption("Shoot and off line", SHOOT_AND_OFF_LINE);
        autoChooser.addOption("Shoot from trench", SHOOT_FROM_TRENCH);
        autoChooser.addOption("Intake Through Trench", INTAKE_THROUGH_TRENCH);
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

            default:
                return null;
        }
    }
}