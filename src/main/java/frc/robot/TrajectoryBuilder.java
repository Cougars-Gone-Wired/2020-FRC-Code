package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

// Purpose of class is to import JSON files from PathWeaver and make them easily accessable by other classes
public class TrajectoryBuilder {

    public enum Paths {
        // each enum value is passing in a "name" to become their trajName
        TWO_METERS("2Meters"),
        THREE_METERS("3Meters"),
        OFFLINE_SHOOTING_POSE("OfflineShootingPose"),
        TRENCH_SHOOTING_POSE("TrenchShootingPose");

        public final String trajName;

        private Paths(String trajName) { // enum constructor
            this.trajName = trajName;
        }
    }

    static HashMap<Paths, Trajectory> trajectories;

    public static void buildTrajectories() {
        trajectories = new HashMap<>();
        for(Paths path : Paths.values()) {
            // imports path from PathWeaver JSON file and converts it into a usable trajectory
            // path.trajName gets the trajName of the current enum value so that that name can be used to find the JSON file for the trajectory associated with that enum value
            String fullPath = "paths/output/" + path.trajName + ".wpilib.json";
            try{
                Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(fullPath);
                trajectories.put(path, TrajectoryUtil.fromPathweaverJson(trajectoryPath)); // adds trajectory to HashMap
            } catch (IOException ex) {
                DriverStation.reportError("Unable to open trajectory: " + fullPath, ex.getStackTrace());
            }
        }
    }  

    public static Trajectory getTrajectory(Paths path) {
        return trajectories.get(path); // since the enum values are used as keys in the hashmap, this will return the trajectory associated with the given enum value
    }
}