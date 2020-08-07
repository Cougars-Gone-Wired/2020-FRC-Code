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

// command for using motion profiling to follow a path
public class ProfileDrive extends CommandBase{

    private Drive drive;

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DriveConstants.ks, DriveConstants.kv, DriveConstants.ka);

    public ProfileDrive(Drive robotDrive) {
        drive = robotDrive;
        addRequirements(drive);
    }

    // profiling command used to follow PathWeaver created trajectory files
    public Command getProfilingCommand(String fileName) {
        // imports path from PathWeaver JSON file and converts it into a usable trajectory
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

    // profiling command used to follow custom code-made trajectories
    public Command getProfilingCommand(double distanceMeters) {
        // creates a voltage constraint so robot doesn't accelerate too fast
        DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
            feedforward,
            drive.getDriveKinematics(), 
            10);

        // config settings and constraints for custom trajectory
        TrajectoryConfig config = new TrajectoryConfig(
            Constants.DriveConstants.maxVelocity, 
            Constants.DriveConstants.maxAcceleration)
            .setKinematics(drive.getDriveKinematics())
            .addConstraint(voltageConstraint);

        // generates custom trajectory
        // units in meters
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            // start at the origin facing the positive x direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // pass through an interior waypoint (1/2 desired distance for example)
            List.of(
                new Translation2d(distanceMeters / 2, 0)
            ),
            // end distanceMeters straight ahead of where robot started, facing forward
            new Pose2d(distanceMeters, 0, new Rotation2d(0)),
            config
        );
        return getProfilingCommand(trajectory);
    }

    // profiling command used to follow pre-built PathWeaver trajectories from the TrajectoryBuilder class
    public Command getProfilingCommand(TrajectoryBuilder.Paths path) {
        return getProfilingCommand(TrajectoryBuilder.getTrajectory(path));
    }

    // base profiling command used to follow any generated trajectory
    public Command getProfilingCommand(Trajectory trajectory) {
        // very important, don't forget to reset everything before following path
        drive.resetSensors();
        drive.resetOdometry(new Pose2d());

        RamseteController controller = new RamseteController(DriveConstants.B, DriveConstants.Zeta);
		PIDController leftController = new PIDController(DriveConstants.DriveP, 0, 0);
        PIDController rightController = new PIDController(DriveConstants.DriveP, 0, 0);
        RamseteCommand ramseteCommand = new RamseteCommand(
			trajectory, // the generated trajectory to follow
			drive::getPosition, // supplies the current robot position from the Drive class
			controller, // the RamseteController used to calculate speed setpoints based on position data and trajectory state
			feedforward, // performs feedforward calculations from kS, kV, and kA constants
			drive.getDriveKinematics(), // used to convert single speed to speed compenents for left and right side
			drive.getEncoders()::getWheelSpeeds, // supplies the current average wheels speeds for each side of robot
			leftController, // PID controller for correcting wheel speed error on left side (using tuned profiling P value)
			rightController, // PID controller for correcting wheel speed error on right side (using tuned profiling P value)
			drive::tankDriveVolts, // consumes and feeds calculated voltage outputs to drive motors
			drive // the drive system, included so that nothing else can run on system while it is following a path
        );
        
        // run path following command, then stop at the end
        return ramseteCommand.andThen(() -> drive.tankDriveVolts(0, 0));
    }
}