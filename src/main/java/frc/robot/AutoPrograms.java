package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.recorder.*;

public class AutoPrograms {

    private SendableChooser<Programs> autoChooser = new SendableChooser<>(); // sendable chooser to hold dead reckoning
                                                                             // auto
    private Programs selectedAuto;

    public enum Programs {
        DO_NOTHING, DEAD_RECKONING, RECORDER, LIMELIGHT_AI
    }

    public AutoPrograms() {
    }

    public void initalizeChooser() {
        autoChooser.addOption("Do Nothing", Programs.DO_NOTHING);
        autoChooser.addOption("Dead Reckoning", Programs.DEAD_RECKONING);
        autoChooser.addOption("Recorder", Programs.RECORDER);
        autoChooser.addOption("Limelight program", Programs.LIMELIGHT_AI);
        SmartDashboard.putData("Auto choices", autoChooser);
    }

    // public Programs getAutoProgram() {
    //     return selectedAuto;
    // }

    public void initAuto() {
        selectedAuto = autoChooser.getSelected();

        switch (selectedAuto) {
            case DEAD_RECKONING:
                
                break;
            case RECORDER:
                Robot.runner.counterInitialize();
                try {
                    List<State> states = StateReader.read(StateLister.gsonChooser.getSelected());
                    Robot.runner.setStates(states);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case LIMELIGHT_AI:
                
                break;
            default:
                break;
        }
    }

    public void runAuto() {
        switch (selectedAuto) {
            case DEAD_RECKONING:
                moveOffLine();
                break;
            case RECORDER:
                Robot.runner.run();
                break;
            case LIMELIGHT_AI:
                break;
        
            default:
                break;
        }
    }

    public void reset() {

    }

    public void moveOffLine() {
        if (Robot.drive.ticksToInches(Robot.drive.getSensorAvg()) < 21) {
            Robot.drive.driveStraight();
        }
    }
}