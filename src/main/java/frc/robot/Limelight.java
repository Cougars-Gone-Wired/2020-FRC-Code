package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private Drive drive; //Drive is in here so the limelight can drive

    private NetworkTable table;

    //Limelight Values
    private double tv; //Whether the limelight has any valid targets (0 or 1)
    private double tx; //Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
    private double ty; //Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)
    private double ta; //Target Area (0% of image to 100% of image)

    //Values for calculating distance
    private double h1; //Distance from the center of the limelight to the ground
    private double h2; //Distance from the center of the target to the target (106.25in approx)
    private double a1; //Angle between the limelight and the ground
                       //Angle between limelight and target calculated by limelight (ty) so another variable is not required
    private double distance; //Calculated from the above values

    //kp = 0.1 min = 0.3 degree = 2.0
    private double min = 0.29; //Minumum value to move motors
    private double aimKp = 0.04; //Aim multiplier
    private double driveKp = 0.05; //Drive multiplier
    private double aim_adjust = 0.00; //0 By Default so robot doesn't move initially
    private double drive_adjust = 0.00; // ↑ ↑ ↑ 
    private double desired_angle = 1.00; //Needs to be set later, 1 degree right now
    private double desired_distance = 0.00; //Needs to be set later

    private enum LimelightStates {
        DO_NOTHING, AIM, AIM_AND_DRIVE, SEEK_AIM_AND_DRIVE 
    }
    private LimelightStates limelightState;

    public Limelight(Robot robot) {
        //drive = robot.getDrive(); needs to be incorporated into Drive later
        table = NetworkTableInstance.getDefault().getTable("limelight"); 
        limelightState = LimelightStates.DO_NOTHING;
    }

    public void limelight(boolean aimButton) {
        tv = table.getEntry("tv").getDouble(0);
        tx = table.getEntry("tx").getDouble(0);
        ty = table.getEntry("ty").getDouble(0);
        ta = table.getEntry("ta").getDouble(0);

        limelightDrive(aimButton);
    }

    public void limelightDrive(boolean aimButton) {

        switch(limelightState) {
            case DO_NOTHING:
                //why doesn't this do anything
                if (aimButton) {
                    limelightState = LimelightStates.SEEK_AIM_AND_DRIVE; //What the limelight should do when the button is pressed
                }
            break;

            case SEEK_AIM_AND_DRIVE:
            //Stays in seek until button stops being pressed or it finds a valid target
                if (!aimButton) {
                    limelightState = LimelightStates.DO_NOTHING;
                    aim_adjust = 0; 
                    drive_adjust = 0;
                }

                if (tv == 1) { 
                    limelightState = LimelightStates.AIM_AND_DRIVE;
                    aim_adjust = 0;
                } else {
                    aim_adjust = min;
                    drive_adjust = 0;
                }
            break;

            case AIM_AND_DRIVE:
                //A lot of copy and paste here that could be changed later but it's not necessary 
                if (!aimButton) {
                    limelightState = LimelightStates.DO_NOTHING;
                    aim_adjust = 0; 
                    drive_adjust = 0;
                }

                aim_adjust = aimKp * (tx / 29.8);
                if (tx > desired_angle) {
                    aim_adjust += min;
                    drive_adjust = 0;
                } else if (tx < -desired_angle) {
                    aim_adjust -= min;
                    drive_adjust = 0;
                } else {
                    aim_adjust = 0;

                    //Won't drive until centered, will stop driving and center again if not centered
                    //drive_adjust = driveKp*(ty/24.85); //used if limelight looks directly at center when at correct distance
                    drive_adjust = driveKp * (currentDistance() - desired_distance);
                    if (currentDistance() - desired_distance  > 5) {
                        drive_adjust += min; //If too far away, move closer
                    }
                    else if (currentDistance() - desired_distance < -5) {
                        drive_adjust -= min; //If too close, move away
                    } else {
                        drive_adjust = 0;
                    }
                }

                break;

            case AIM:
                if (!aimButton) {
                    limelightState = LimelightStates.DO_NOTHING;
                    aim_adjust = 0; 
                    drive_adjust = 0;
                }

                aim_adjust = aimKp * (tx / 29.8);
                if (tx > desired_angle) {
                    aim_adjust += min;
                } else if (tx < -desired_angle) {
                    aim_adjust -= min;
                } else {
                    aim_adjust = 0;
                }
            break;
        }

        drive.robotDrive(drive_adjust, aim_adjust);
    }

    //Calculates Distance
    public double currentDistance() {
        distance = (h2 - h1) / Math.tan(Math.toRadians(a1 + ty));
        return distance;
    }

    //Should not be used in actual code, just for testing
    //For dashboard values that need to be put on the dashboard once
    public void dashboardInitialize() {
        SmartDashboard.putNumber("h1", 0);
        SmartDashboard.putNumber("h2", 0);
        SmartDashboard.putNumber("a1", 0);
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
    }
}