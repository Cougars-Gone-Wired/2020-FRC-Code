
package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.recorder.*;

public class Robot extends TimedRobot {

    private Controllers controllers;

    static Drive drive;
    static Climber climber;
    static Limelight limelight;

    static Shooter shooter;
    static Intake intake;
    static IntakeArm intakeArm;
    static Feeder feeder;
    static Arm arm;
    static Engage engage;

    static StateRecorder recorder;
    static StateRunner runner;

    @Override
    public void robotInit() {
        controllers = new Controllers();

        drive = new Drive();
        climber = new Climber();
        limelight = new Limelight();

        shooter = new Shooter();
        intake = new Intake();
        intakeArm = new IntakeArm();
        feeder = new Feeder();
        arm = new Arm();
        engage = new Engage();

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
        drive.initalize();
        climber.initalize();

        shooter.initialize();
        intake.initialize();
        intakeArm.initialize();
        feeder.initialize();
        arm.initialize();
        engage.initialize();

        recorder.initialize();
        runner.counterInitialize();
    }

    @Override
    public void teleopPeriodic() {
        controllers.updateControllerValues();

        drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis(), controllers.getDriveSideToggle());
        climber.climb(controllers.getClimberUpTrigger(), controllers.getClimberDownTrigger());
        limelight.getDashboard();
        limelight.limelightDrive(controllers.getLimelightButton());
        shooter.shoot(controllers.getShooterTrigger());
        intake.intake(controllers.getIntakeAxis());
        intakeArm.intakeArm(controllers.getIntakeArmToggle());
        feeder.feed(controllers.getFeederAxis());
        arm.pistonArm(controllers.isArmUpButton(), controllers.isArmDownButton());
        engage.engageShoot(controllers.getIntakeAxis());

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
