package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Shooter;

// command for spinning the shooter a bit before shooting so that it doesn't take as long for the shooter to get up to speed
public class IdleShooterSpin extends CommandBase {

    private double desiredVelocity;

    public IdleShooterSpin() {
        desiredVelocity = Shooter.DESIRED_VELOCITY;
    }

    public IdleShooterSpin(double desiredVelocityPercent) {
        desiredVelocity = desiredVelocityPercent * Shooter.VOLTAGE_TO_VELOCITY;
    }

    @Override
    public void execute() {
        Robot.shooter.setVelocityThresholds(-1, -1);
        Robot.shooter.setPIDShooting(desiredVelocity);
    }
}