package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Shooter;

// command for shooting with a constant speed
public class ShootVoltage extends CommandBase {

    private double shooterSpeed;

    public ShootVoltage() {
        shooterSpeed = Shooter.SHOOTER_SPEED;
    }

    public ShootVoltage(double shooterSpeed, boolean changeThresholds) {
        if(changeThresholds) {
            Robot.shooter.setVelocityThresholds(5, 50);
        }
        this.shooterSpeed = shooterSpeed;
    }

    @Override
    public void execute() {
        Robot.shooter.setVoltageShooting(shooterSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.setNotMoving();
    }
}