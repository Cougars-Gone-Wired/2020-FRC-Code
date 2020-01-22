
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

    private Controllers controllers;
    private Drive drive;
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
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
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
    }

    @Override
    public void testPeriodic() {
    }

    public Drive getDrive() {
        return drive;
    }
}
