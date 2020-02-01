package frc.robot;

import java.util.List;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.recorder.*;

public class AutoPrograms {
    public static double AUTO_SPEED = 0.3;

    AHRS gyro;

    private SendableChooser<Programs> autoChooser = new SendableChooser<>(); // sendable chooser to hold dead reckoning
    private SendableChooser<Positions> position = new SendableChooser<>(); // sendable chooser to hold dead reckoning
                                                                           // auto
    private Programs selectedAuto;
    private Positions selectedPosition;

    public enum Programs {
        DO_NOTHING, DEAD_RECKONING, RECORDER, LIMELIGHT
    }

    public enum Positions {
        POWER_PORT, MIDDLE_FIELD, LOADING_BAY
    }

    public AutoPrograms() {
        gyro = new AHRS(Port.kMXP);
    }

    public void initalizeChoosers() {
        autoChooser.addOption("Do Nothing", Programs.DO_NOTHING);
        autoChooser.addOption("Dead Reckoning", Programs.DEAD_RECKONING);
        autoChooser.addOption("Recorder", Programs.RECORDER);
        autoChooser.addOption("Limelight program", Programs.LIMELIGHT);
        SmartDashboard.putData("Auto choices", autoChooser);

        position.addOption("Power Port", Positions.POWER_PORT);
        position.addOption("Middle", Positions.MIDDLE_FIELD);
        position.addOption("Loading Bay", Positions.LOADING_BAY);
        SmartDashboard.putData("Position on field", position);
    }

    public void initAuto() {
        // selectedAuto = Programs.SOMETHING;
        selectedAuto = autoChooser.getSelected();
        selectedPosition = Positions.POWER_PORT;
        // selectedPosition = position.getSelected();

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
        case LIMELIGHT:
            Robot.limelight.initialize();
            break;
        default:
            break;
        }
    }

    public void runAuto() {
        switch (selectedAuto) {
        case DO_NOTHING:
            Robot.drive.robotDrive(0, 0, false);
            break;

        case DEAD_RECKONING:
            moveOffLine();
            break;

        case RECORDER:
            Robot.runner.run();
            break;

        case LIMELIGHT:
            runLimelightAuto();

            break;

        default:
            break;
        }
    }

    public void runLimelightAuto() {
        switch (selectedPosition) {
        case POWER_PORT:
            Robot.limelight.limelightAuto(true);
            // shoot

            break;
        case MIDDLE_FIELD:

            break;
        case LOADING_BAY:
            break;
        }
    }

    public void move(double distance, double speed) {

    }

    public void turn(double angle) {

    }

    public void moveOffLine() {
        SmartDashboard.putNumber("sensor avgs", Robot.drive.ticksToInches(Robot.drive.getSensorAvg()));
        if (Robot.drive.ticksToInches(Robot.drive.getSensorAvg()) < 21) {
            Robot.drive.driveStraight(AUTO_SPEED);
        } else {
            Robot.drive.driveStraight(0);
        }
    }
}