package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.Drive;
import frc.robot.TrajectoryBuilder;
import frc.robot.Constants.DriveConstants;

public class ProfileDrive extends CommandBase{

    private Drive drive;

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DriveConstants.ks, DriveConstants.kv, DriveConstants.ka);

    public ProfileDrive(Drive robotDrive) {
        drive = robotDrive;
        addRequirements(drive);
    }

    public Command getProfilingCommand(String fileName) {
        String trajectoryJSON = fileName;
        Trajectory trajectory; 
        
        try{
			Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
			trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
		} catch (IOException ex) {
			trajectory = null;
			DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        return getProfilingCommand(trajectory);
    }

    public Command getProfilingCommand(double distanceMeters) {
        DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
            feedforward,
            drive.getDriveKinematics(), 
            10);

        TrajectoryConfig config = new TrajectoryConfig(
            Constants.DriveConstants.maxVelocity, 
            Constants.DriveConstants.maxAcceleration)
            .setKinematics(drive.getDriveKinematics())
            .addConstraint(voltageConstraint);

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(
                new Translation2d(distanceMeters, 0)
            ),
            new Pose2d(distanceMeters, 0, new Rotation2d(0)),
            config
        );
        return getProfilingCommand(trajectory);
    }

    public Command getProfilingCommand(TrajectoryBuilder.Paths path) {
        return getProfilingCommand(TrajectoryBuilder.getTrajectory(path));
    }

    public Command getProfilingCommand(Trajectory trajectory) {
        drive.resetSensors();
        drive.resetOdometry(new Pose2d());

        RamseteController controller = new RamseteController(DriveConstants.B, DriveConstants.Zeta);
		PIDController leftController = new PIDController(DriveConstants.DriveP, 0, 0);
        PIDController rightController = new PIDController(DriveConstants.DriveP, 0, 0);
        RamseteCommand ramseteCommand = new RamseteCommand(
			trajectory, 
			drive::getPosition, 
			controller, 
			feedforward, 
			drive.getDriveKinematics(), 
			drive.getEncoders()::getWheelSpeeds, 
			leftController, 
			rightController, 
			drive::tankDriveVolts, 
			drive
        );
        
        return ramseteCommand.andThen(() -> drive.tankDriveVolts(0, 0));
    }
}