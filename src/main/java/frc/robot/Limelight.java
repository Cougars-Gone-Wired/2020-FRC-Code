package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

// class for everything vision processing
public class Limelight {

    private static double DEGREE_RANGE = 29.8;

    private NetworkTable table;

    //Limelight Values
    private double tv; //Whether the limelight has any valid targets (0 or 1)
    private double tx; //Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
    private double ty; //Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)
    private double ta; //Target Area (0% of image to 100% of image)

    //Values for calculating distance
    private double h1 = 9.375; //Distance from the center of the limelight to the ground
    private double h2 = 90.75; //Distance from the center of the target to the target (106.25in approx)
    private double a1 = 19.1; //Angle between the limelight and the ground
                       //Angle between limelight and target calculated by limelight (ty) so another variable is not required
    private double distance; //Calculated from the above values

    //kp = 0.1 min = 0.3 degree = 2.0
    private double min = 0.39; //Minumum value to move motors
    private double aimKp = 0.021; //Aim multiplier
    private double driveKp = 0.50; //Drive multiplier
    private double angle_threshold = 0.50; //Final value needs to be set later
    private double angle_offset = 0.00;
    private double distance_error = 5.00; //Distance error
    private double desired_distance = 260.00; //Needs to be set later

    private double unaimAngle;
    private double aim_adjust = 0.00; //0 By Default so robot doesn't move initially
    private double drive_adjust = 0.00; // ↑ ↑ ↑ 

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight"); 
    }

    public void initialize() {
        limelightDriveState = LimelightDriveStates.DO_NOTHING;
        // turnlightsOff();
    }

    private enum LimelightDriveStates {
        DO_NOTHING, AIM, AIM_AND_DRIVE, SEEK_AIM_AND_DRIVE, 
    }
    private LimelightDriveStates limelightDriveState;

    public void limelightDrive(boolean aimButton) {
        tv = table.getEntry("tv").getDouble(0);
        tx = table.getEntry("tx").getDouble(0);
        ty = table.getEntry("ty").getDouble(0);
        ta = table.getEntry("ta").getDouble(0);
        getDashboard();

        switch (limelightDriveState) {
            case DO_NOTHING:
            turnlightsOff();
                if (aimButton
                // && !(Math.abs(tx - angle_offset) < angle_threshold)
                ) {
                    turnlightsOn();
                    if(!(Math.abs(tx - angle_offset) < angle_threshold)){
                     //What the limelight should do when the button is pressed
                     setDriveAim();
                    }
                }
            break;

            case SEEK_AIM_AND_DRIVE:
            //Stays in seek until button stops being pressed or it finds a valid target
                if (!aimButton) {
                    setDriveDoNothing();
                }

                drive_adjust = 0;
                if (tv == 1 && ta > 10) { 
                    setDriveAimAndDrive();
                    aim_adjust = 0;
                } else {
                    aim_adjust = min;
                }
                Robot.drive.limelightDrive(drive_adjust, aim_adjust);
            break;

            case AIM_AND_DRIVE:
                //A lot of copy and paste here that could be changed later but it's not necessary 
                if (!aimButton || (Math.abs(tx - angle_offset) < angle_threshold)) {
                    setDriveDoNothing();
                }

                aim_adjust = aimKp * ((tx - angle_offset) / DEGREE_RANGE);
                if (tx - angle_offset > angle_threshold) {
                    aim_adjust += min;
                } else if (tx - angle_offset < -angle_threshold) {
                    aim_adjust -= min;
                } else {
                    aim_adjust = 0;
                }

                //drive_adjust = driveKp*(ty/24.85); //used if limelight looks directly at center when at correct distance
                drive_adjust = -driveKp * ((currentDistance() - desired_distance) / desired_distance);
                if (currentDistance() - desired_distance  > distance_error) {
                    drive_adjust -= min;
                } else if (currentDistance() - desired_distance < -distance_error) {
                    drive_adjust += min;
                } else {
                    drive_adjust = 0;
                }
                Robot.drive.limelightDrive(drive_adjust, aim_adjust);
                break;

            case AIM:
                if (!aimButton || (Math.abs(tx - angle_offset) < angle_threshold)) {
                    setDriveDoNothing();
                } else {
                    aim_adjust = aimKp * ((tx - angle_offset) / DEGREE_RANGE);
                    if (tx - angle_offset > angle_threshold) {
                        aim_adjust += min;
                    } else if (tx - angle_offset < -angle_threshold) {
                        aim_adjust -= min;
                    } else {
                        aim_adjust = 0;
                        setDriveDoNothing();
                    }
                    Robot.drive.limelightDrive(drive_adjust, aim_adjust);
                }
            break;
        }
    }

    public boolean isDriveNotMoving() {
        return limelightDriveState == LimelightDriveStates.DO_NOTHING;
    }

    public boolean isDriveAim() {
        return limelightDriveState == LimelightDriveStates.AIM;
    }

    public void setDriveDoNothing() {
        aim_adjust = 0; 
        drive_adjust = 0;
        Robot.drive.limelightDrive(drive_adjust, aim_adjust);
        limelightDriveState = LimelightDriveStates.DO_NOTHING;
    }

    public void setDriveAim() {
        limelightDriveState = LimelightDriveStates.AIM;
    }

    public void setDriveAimAndDrive() {
        limelightDriveState = LimelightDriveStates.AIM_AND_DRIVE;
    }

    public void setDriveSeekAimAndDrive() {
        limelightDriveState = LimelightDriveStates.SEEK_AIM_AND_DRIVE;
    }

    public boolean limelightAutoAim() {
        tv = table.getEntry("tv").getDouble(0);
        tx = table.getEntry("tx").getDouble(0);
        ty = table.getEntry("ty").getDouble(0);
        ta = table.getEntry("ta").getDouble(0);
            
        aim_adjust = aimKp * (tx / DEGREE_RANGE);
        if (tx - angle_offset > angle_threshold) {
            aim_adjust += min;
        } else if (tx - angle_offset < -angle_threshold) {
            aim_adjust -= min;
        } else {
            aim_adjust = 0;
            Robot.drive.limelightDrive(0, 0);
            return true;
        }

        Robot.drive.limelightDrive(drive_adjust, aim_adjust);

        return false;
    }

    public boolean limelightAutoUnaim() {
        tv = table.getEntry("tv").getDouble(0);
        tx = table.getEntry("tx").getDouble(0);
        ty = table.getEntry("ty").getDouble(0);
        ta = table.getEntry("ta").getDouble(0);

        SmartDashboard.putNumber("UnaimAngle", unaimAngle);

        aim_adjust = aimKp * ((tx - unaimAngle) / DEGREE_RANGE);
        if (tx - unaimAngle > angle_threshold) {
            aim_adjust += min;
        } else if (tx - unaimAngle < -angle_threshold) {
            aim_adjust -= min;
        } else {
            aim_adjust = 0;
            Robot.drive.limelightDrive(0, 0);
            return true;
        }

        Robot.drive.limelightDrive(drive_adjust, aim_adjust);
        
        return false;
    }

    public void setUnaimAngle() {
        tx = table.getEntry("tx").getDouble(0);
        unaimAngle = tx - angle_offset;
    }

    public void turnlightsOn() {
        table.getEntry("ledMode").setNumber(3);
    }

    public void turnlightsOff() {
        table.getEntry("ledMode").setNumber(1);
    }

    //Calculates Distance
    public double currentDistance() {
        distance = (h2 - h1) / Math.tan(Math.toRadians(a1 + ty));
        return distance;
    }

    //Should not be used in actual code, just for testing
    //For dashboard values that need to be put on the dashboard once
    public void dashboardInitialize() {
        SmartDashboard.putNumber("h1", h1);
        SmartDashboard.putNumber("h2", h2);
        SmartDashboard.putNumber("a1", a1);
        SmartDashboard.putNumber("min", min);
        SmartDashboard.putNumber("aimKp", aimKp);
        SmartDashboard.putNumber("driveKp", driveKp);
        SmartDashboard.putNumber("angle_threshold", angle_threshold);
        SmartDashboard.putNumber("angle_offset", angle_offset);
        SmartDashboard.putNumber("distance_error", distance_error);
        SmartDashboard.putNumber("desired_distance", desired_distance);
    }

    //Should not be used in actual code, just for testing
    //For dashboard values that need to be updated periodically
    public void dashboard() {
        SmartDashboard.putNumber("LimelightX", tx);
        SmartDashboard.putNumber("LimelightY", ty);
        SmartDashboard.putNumber("LimelightArea", ta);
        SmartDashboard.putNumber("Distance", currentDistance());
    }

    //Should not be used in actual code, just for testing
    //For getting values from the dashboard
    public void getDashboard() {
        h1 = SmartDashboard.getNumber("h1", 0);
        h2 = SmartDashboard.getNumber("h2", 0);
        a1 = SmartDashboard.getNumber("a1", 0);
        min = SmartDashboard.getNumber("min", 0);
        aimKp = SmartDashboard.getNumber("aimKp", 0);
        driveKp = SmartDashboard.getNumber("driveKp", 0);
        aim_adjust = SmartDashboard.getNumber("aim_adjust", 0);
        drive_adjust = SmartDashboard.getNumber("drive_adjust", 0);
        angle_threshold = SmartDashboard.getNumber("angle_threshold", 0);
        angle_offset = SmartDashboard.getNumber("angle_offset", 0);
        distance_error = SmartDashboard.getNumber("distance_error", 0);
        desired_distance = SmartDashboard.getNumber("desired_distance", 0);
    }
}