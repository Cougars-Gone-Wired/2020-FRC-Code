package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

// Purpose of class is to import JSON files from PathWeaver and make them easily accessable by other classes
public class TrajectoryBuilder {

    public enum Paths {
        TWO_METERS("2Meters"),
        THREE_METERS("3Meters"),
        OFFLINE_SHOOTING_POSE("OfflineShootingPose"),
        TRENCH_SHOOTING_POSE("TrenchShootingPose");

        public final String trajName;

        private Paths(String trajName) {
            this.trajName = trajName;
        }
    }

    static ArrayList<String> paths;
    static HashMap<Paths, Trajectory> trajectories;

    public static void buildTrajectories() {
        trajectories = new HashMap<>();
        for(Paths path : Paths.values()) {
            String fullPath = "paths/output/" + path.trajName + ".wpilib.json";
            try{
                Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(fullPath);
                trajectories.put(path, TrajectoryUtil.fromPathweaverJson(trajectoryPath));
            } catch (IOException ex) {
                DriverStation.reportError("Unable to open trajectory: " + fullPath, ex.getStackTrace());
            }
        }
    }  

    public static Trajectory getTrajectory(Paths path) {
        return trajectories.get(path);
    }
}