
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private Drive drive;
  private Controllers controllers;
  private Arm arm;

  @Override
  public void robotInit() {
    controllers = new Controllers();
    drive = new Drive();
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
  public void teleopPeriodic() {
    controllers.updateControllerValues();
    drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis());
    drive.setSide(controllers.isDriveSideButton());
    arm.pistonArm(controllers.isShootingPosButton(), controllers.isStartingPosButton(),
        controllers.isClimbingPosButton());
  }

  @Override
  public void testPeriodic() {
  }
}
