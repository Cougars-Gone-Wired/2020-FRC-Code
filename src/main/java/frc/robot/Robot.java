
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private Drive drive;
  private Controllers controllers;

  @Override
  public void robotInit() {
    controllers = new Controllers();
    drive = new Drive();

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
    drive.setSide(controllers.toggle());
  }

  @Override
  public void testPeriodic() {
  }
}
