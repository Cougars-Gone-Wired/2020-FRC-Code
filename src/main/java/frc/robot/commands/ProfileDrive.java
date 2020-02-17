package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Drive;
import frc.robot.Constants.DriveConstants;

public class ProfileDrive extends CommandBase{

    private Drive drive;

    public ProfileDrive(Drive robotDrive) {
        drive = robotDrive;
        addRequirements(drive);
    }

    public Command getProfilingCommand(String fileName) {
        drive.resetSensors();
        drive.resetOdometry(new Pose2d());

        String trajectoryJSON = fileName;
        Trajectory trajectory; 
        
        try{
			Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
			trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
		} catch (IOException ex) {
			trajectory = null;
			DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        
        RamseteController controller = new RamseteController(DriveConstants.B, DriveConstants.Zeta);
		SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DriveConstants.ks, DriveConstants.kv, DriveConstants.ka);
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