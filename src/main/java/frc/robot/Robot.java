
package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.recorder.*;

public class Robot extends TimedRobot {

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
    public static CompressorController compressorController;

    private AutoSelector autoSelector;
    private Command autonomousCommand;

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

        compressorController = new CompressorController();

        autoSelector = new AutoSelector();

        recorder = new StateRecorder();
        runner = new StateRunner();
        // GsonSmartDash.put();
        limelight.dashboardInitialize();

        // camera = new Camera();
        // new Thread(camera).start();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        limelight.dashboard();

        // camera.stop(controllers.getStopCameraButton());
    }

    @Override
    public void autonomousInit() {
        setMotorsBrake();

        arms.initialize();
        intake.initialize();
        shooter.initialize();
        feeder.initialize();
        chomper.initialize();

        climber.initalize();
        drive.initalize();
        limelight.initialize();

        autoSelector.chooseAuto();
        autonomousCommand = autoSelector.getAutoCommand();
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
        feeder.controlFeeder();
        chomper.controlChomper(0);
        drive.dashboard();
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

        compressorController.initialize();

        // recorder.initialize();
        // runner.counterInitialize();
    }

    @Override
    public void teleopPeriodic() {
        controllers.updateControllerValues();

        arms.controlArm(controllers.isArmUpButton(), controllers.isArmDownButton());
        arms.controlIntakeArm(controllers.getIntakeArmTrigger());
        intake.controlIntake(controllers.getIntakeAxis());
        shooter.controlShooter(controllers.getShooterTrigger());
        // shooter.pidShooter(controllers.getShooterTrigger());
        feeder.controlFeeder();
        chomper.controlChomper(controllers.getChomperOverrideAxis());

        climber.controlClimber(controllers.getClimberUpTrigger(), controllers.getClimberDownTrigger());
        drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis(),
                controllers.getDriveSideToggle());
        drive.dashboard();
        limelight.limelightDrive(controllers.getLimelightButton());

        compressorController.controlCompressor();
        // recorder.record();
    }

    @Override
    public void disabledInit() {
        setMotorsCoast();
        drive.resetSensors();

        // if (GsonSmartDash.shouldRecord) {
        // List<State> states = recorder.getStates();
        // try {
        // StatesWriter.writeStates(states, GsonSmartDash.gsonFileName);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // StateLister.getStateNames();
        // SmartDashboard.putBoolean("Should Record", false);
    }

    @Override
    public void disabledPeriodic() {
        // GsonSmartDash.set();

        // if (!GsonSmartDash.shouldRecord) {
        // SmartDashboard.putString("Gson File Name", "");
        // }
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
