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
        LEFT, CENTER, RIGHT
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

        position.addOption("Power Port", Positions.LEFT);
        position.addOption("Middle", Positions.CENTER);
        position.addOption("Loading Bay", Positions.RIGHT);
        SmartDashboard.putData("Position on field", position);
    }

    public void autoDashboard() {
        SmartDashboard.putNumber("Gyro angle", gyro.getAngle());
        SmartDashboard.putNumber("Ticks", Robot.ticks);
    }

    public void initAuto() {
        // selectedAuto = Programs.SOMETHING;
        selectedAuto = autoChooser.getSelected();
        selectedPosition = Positions.LEFT;
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
            shootMoveOffLine();

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
        case LEFT:
            //Robot.limelight.limelightAuto(true);
            // shoot

            break;
        case CENTER:

            break;
        case RIGHT:
            break;
        }
    }

    public void move(double distance, double speed) {
        if (Robot.drive.getEncoders().getAvgEncoderMetersAvg() < distance) {
            Robot.drive.driveStraight(speed);
        } else {
            Robot.drive.driveStraight(0);
        }
    }

    public void turn(double angle) {

    }

    public void shoot(int seconds) {
        if (seconds >= Robot.ticks / 5) {
            Robot.shooter.setShooting();
        } else {
            Robot.shooter.setNotMoving();
        }
    }

    public void shootMoveOffLine() {
        shoot(5);
        move(21, AUTO_SPEED);
    }
}