
package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

    private Controllers controllers;
    private Drive drive;

    private StateRecorder recorder;
    private StateRunner runner;
    private Climber climber;
    private Shooter shooter;
    private Intake intake;
    private Feeder feeder;
    private Arm arm;
    private Engage engage;

    @Override
    public void robotInit() {
        controllers = new Controllers();
        drive = new Drive();

        climber = new Climber();
        shooter = new Shooter();
        intake = new Intake();
        feeder = new Feeder();
        arm = new Arm();
        engage = new Engage();

        recorder = new StateRecorder();
        runner = new StateRunner(this);
        GsonSmartDash.put();
    }

    @Override
    public void robotPeriodic() {
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
        feeder.initialize();
        arm.initialize();
        engage.initialize();
        
        recorder.initialize();
        runner.counterInitialize();
    }

    @Override
    public void teleopPeriodic() {
        controllers.updateControllerValues();

        drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis());
        drive.setSide(controllers.isDriveSideToggle());
        climber.climb(controllers.getClimberUpTrigger(), controllers.getClimberDownTrigger());

        shooter.shoot(controllers.getShooterTrigger());
        intake.intake(controllers.getIntakeAxis());
        intake.intakeArm(controllers.isIntakeArmToggle());
        feeder.feed(controllers.getFeederAxis());
        arm.pistonArm(controllers.isArmToggle(), controllers.isClimberToggle());
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
    }

    public Drive getDrive() {
        return drive;
    }
}
