
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

    // public static StateRecorder recorder;
    // public static StateRunner runner;

    public static Camera camera;
    public static CompressorController compressorController;

    private AutoSelector autoSelector;
    private Command autonomousCommand;

    @Override
    public void robotInit() {
        controllers = new Controllers();

        arms = new Arms();
        climber = new Climber();
        drive = new Drive();
        limelight = new Limelight();

        intake = new Intake();
        shooter = new Shooter();
        feeder = new Feeder();
        chomper = new Chomper();

        compressorController = new CompressorController();

        autoSelector = new AutoSelector();

        limelight.dashboardInitialize();

        // recorder = new StateRecorder();
        // runner = new StateRunner();

        // GsonSmartDash.put();

        // camera = new Camera();
        // new Thread(camera).start();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        // limelight.dashboard();

        // camera.stop(controllers.getStopCameraButton());
    }

    @Override
    public void autonomousInit() {
        setMotorsBrake();
        
        arms.initialize();
        climber.initalize();
        drive.initalize();
        limelight.initialize();

        intake.initialize();
        shooter.initialize();
        feeder.initialize();
        chomper.initialize();

        compressorController.setDisabled();

        autonomousCommand = autoSelector.getAutoCommand();
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
        drive.dashboard();
        feeder.controlFeeder(0);
        chomper.controlChomper(0);
    }

    @Override
    public void teleopInit() {
        setMotorsBrake();

        arms.initialize();
        climber.initalize();
        drive.initalize();
        limelight.initialize();

        intake.initialize();
        shooter.initialize();
        feeder.initialize();
        chomper.initialize();

        compressorController.initialize();

        // recorder.initialize();
        // runner.counterInitialize();
    }

    @Override
    public void teleopPeriodic() {
        controllers.updateControllerValues();

        arms.controlArm(controllers.isArmUpBumper(), controllers.isArmDownBumper());
        climber.controlClimber(controllers.getClimberUpTrigger(), controllers.getClimberDownTrigger());
        drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis(), controllers.isDriveSideToggle());
        // drive.dashboard();
        limelight.limelightDrive(controllers.isLimelightButton());

        arms.controlIntakeArm(controllers.isIntakeArmDownBumper(), controllers.isIntakeArmUpBumper());
        intake.controlIntake(controllers.getIntakeAxis());
        shooter.controlShooter(controllers.getShooterTrigger(), controllers.isShooterModeToggle());
        feeder.controlFeeder(controllers.getFeederOuttakeTrigger());
        chomper.controlChomper(controllers.getChomperOverrideAxis());

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

    public void setMotorsBrake() {
        climber.setMotorsBrake();
        drive.setMotorsBrake();
        intake.setMotorsBrake();
        feeder.setMotorsBrake();
    }

    public void setMotorsCoast() {
        climber.setMotorsCoast();
        drive.setMotorsCoast();
        intake.setMotorsCoast();
        feeder.setMotorsCoast();
    }
}
