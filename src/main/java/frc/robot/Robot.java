
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private Controllers controllers;
  private Drive drive;
  private Climber climber;
  private Intake intake;
  private Feeder feeder;
  private Arm arm;

  @Override
  public void robotInit() {
    controllers = new Controllers();
    drive = new Drive();
    climber = new Climber();
    intake = new Intake();
    feeder = new Feeder();
    arm = new Arm();
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
    intake.initialize();
    feeder.initialize();
    arm.initialize();
  }

  @Override
  public void teleopPeriodic() {
    controllers.updateControllerValues();
    // Mobility
    drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis());
    drive.setSide(controllers.driveSideToggle.getValue());
    climber.climb(controllers.getClimberUpTrigger(), controllers.getClimberDownTrigger());
    // Manipulator
    intake.intake(controllers.getIntakeTrigger());
    intake.intakeArm(controllers.intakeArmToggle.getValue());
    feeder.feed(controllers.isFeederButton());
    arm.pistonArm(controllers.isShootingPosButton(), controllers.isStartingPosButton(),
        controllers.isClimbingPosButton());
  }

  @Override
  public void testPeriodic() {
  }
}
