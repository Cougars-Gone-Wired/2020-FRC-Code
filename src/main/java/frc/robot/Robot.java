
package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.recorder.*;

public class Robot extends TimedRobot {

    private AutoPrograms autoPrograms;

    public static int ticks = 0;

    public static Controllers controllers;

    public static Arms arms;
    public static Intake intake;
    public static Shooter shooter;
    public static Feeder feeder;
    public static Chomper chomper;

    public static Climber climber;
    public static Drive drive;
    public static Limelight limelight;

    public static StateRecorder recorder;
    public static StateRunner runner;

    public static Camera camera;

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
        autoPrograms = new AutoPrograms();


        recorder = new StateRecorder();
        runner = new StateRunner();
        GsonSmartDash.put();
        limelight.dashboardInitialize();
        // autoPrograms.initalizeChooser();

        camera = new Camera();
        new Thread(camera).start();
    }

    @Override
    public void robotPeriodic() {
        ticks++;
        autoPrograms.autoDashboard();
        drive.dashboard();
        limelight.dashboard();

        camera.stop(controllers.getStopCameraButton());
    }

    @Override
    public void autonomousInit() {
        setMotorsBrake();

        autoPrograms.initAuto();
    }

    @Override
    public void autonomousPeriodic() {
        autoPrograms.runAuto();
    }

    @Override
    public void teleopInit() {
        setMotorsBrake();

        arms.initialize();
        intake.initialize();
        shooter.initialize();
        feeder.initialize();
        chomper.initialize();

        climber.initalize();
        drive.initalize();
        limelight.initialize();

        recorder.initialize();
        runner.counterInitialize();
    }

    @Override
    public void teleopPeriodic() {
        controllers.updateControllerValues();

        arms.controlArm(controllers.isArmUpButton(), controllers.isArmDownButton());
        arms.controlIntakeArm(controllers.getIntakeAxis());
        intake.intake(controllers.getIntakeArmTrigger());
        shooter.shoot(controllers.getShooterTrigger());
        // shooter.pidShooter(controllers.getShooterTrigger());
        feeder.feed(controllers.getFeederAxis());
        chomper.controlChomper(controllers.getChomperUpButton(), controllers.getChomperDownButton());

        climber.controlClimb(controllers.getClimberUpTrigger(), controllers.getClimberDownTrigger());
        drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis(), controllers.getDriveSideToggle());
        limelight.limelightDrive(controllers.getLimelightButton());

        recorder.record();
    }

    @Override
    public void disabledInit() {
        setMotorsCoast();
        drive.resetSensors();

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

    public void setMotorsBrake() {
        climber.setMotorsBrake();
        drive.setMotorsBrake();
        feeder.setMotorsBrake();
        intake.setMotorsBrake();
    }
    public void setMotorsCoast() {
        climber.setMotorsCoast();
        drive.setMotorsCoast();
        feeder.setMotorsCoast();
        intake.setMotorsCoast();
    }
}
