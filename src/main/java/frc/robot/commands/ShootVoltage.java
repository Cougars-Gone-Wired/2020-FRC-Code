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

    // if changeThreasholds is false the last thresholds used will be used (shooter is initialized with thresholds used for PID)
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
    public void end(boolean interrupted) { // can be stopped by other command ending in a ParallelRaceGroup
        Robot.shooter.setNotMoving();
    }
}