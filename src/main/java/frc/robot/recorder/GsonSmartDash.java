package frc.robot.recorder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// class adds widgets to shuffleboard to control when states should be recorded and what new recording files should be called
public class GsonSmartDash {

    public static boolean shouldRecord;
    public static String gsonFileName;

    public static void put() {
        SmartDashboard.putBoolean("Should Record", false);
        SmartDashboard.putString("Gson File Name", "");
    }

    public static void set() {
        shouldRecord = SmartDashboard.getBoolean("Should Record", false);
        gsonFileName = SmartDashboard.getString("Gson File Name", "notGood");
    }
}
