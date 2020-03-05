package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Shooter;

public class ShootPID extends CommandBase {

    private double desiredVelocity;

    public ShootPID() {
        desiredVelocity = Shooter.DESIRED_VELOCITY;
    }

    public ShootPID(double desiredVelocity) {
        this.desiredVelocity = desiredVelocity;
    }

    @Override
    public void execute() {
        Robot.shooter.setPIDShooting(desiredVelocity);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.setNotMoving();
    }

}