
package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.recorder.*;

public class Robot extends TimedRobot {

    private Controllers controllers;

    static Arms arms;
    static Intake intake;
    static Shooter shooter;
    static Feeder feeder;
    static Chomper chomper;

    static Climber climber;
    static Drive drive;
    static Limelight limelight;

    static StateRecorder recorder;
    static StateRunner runner;

    @Override
    public void robotInit() {
        controllers = new Controllers();

        arms = new Arms();
        intake = new Intake();
        shooter = new Shooter();
        feeder = new Feeder();
        chomper = new Chomper();

        climber = new Climber();
        drive = new Drive();
        limelight = new Limelight();

        recorder = new StateRecorder();
        runner = new StateRunner(this);
        GsonSmartDash.put();
        limelight.dashboardInitialize();
    }

    @Override
    public void robotPeriodic() {
        limelight.dashboard();
    }

    @Override
    public void autonomousInit() {
        runner.counterInitialize();
        try {
            List<State> states = StateReader.read(StateLister.gsonChooser.getSelected());
            runner.setStates(states);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void autonomousPeriodic() {
        runner.run();
    }

    @Override
    public void teleopInit() {
        arms.initialize();
        intake.initialize();
        shooter.initialize();
        feeder.initialize();
        chomper.initialize();

        climber.initalize();
        drive.initalize();

        recorder.initialize();
        runner.counterInitialize();
    }

    @Override
    public void teleopPeriodic() {
        controllers.updateControllerValues();

        arms.shooterArm(controllers.isArmUpButton(), controllers.isArmDownButton());
        arms.intakeArm(controllers.getIntakeArmToggle());
        intake.intake(controllers.getIntakeAxis());
        shooter.shoot(controllers.getShooterTrigger());
        feeder.feed();
        chomper.controlChomp();

        climber.climb(controllers.getClimberUpTrigger(), controllers.getClimberDownTrigger());
        drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis(), controllers.getDriveSideToggle());
        limelight.getDashboard();
        limelight.limelightDrive(controllers.getLimelightButton());

        drive.dashboard();
        recorder.record(controllers);
    }

    @Override
    public void disabledInit() {
        if (GsonSmartDash.shouldRecord) {
            List<State> states = recorder.getStates();
            try {
                StatesWriter.writeStates(states, GsonSmartDash.gsonFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        StateLister.getStateNames();
        SmartDashboard.putBoolean("Should Record", false);
    }

    @Override
    public void disabledPeriodic() {
        GsonSmartDash.set();

        if (!GsonSmartDash.shouldRecord) {
            SmartDashboard.putString("Gson File Name", "");
        }
    }

    @Override
    public void testPeriodic() {
        limelight.dashboardInitialize();
    }

    public Drive getDrive() {
        return drive;
    }
}
