
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
    drive.setSide(controllers.driveSideToggle.getValue());
    arm.pistonArm(controllers.shooterPosToggle, controllers.startPosToggle, controllers.climingPosToggle);
  }

  @Override
  public void testPeriodic() {
  }
}
