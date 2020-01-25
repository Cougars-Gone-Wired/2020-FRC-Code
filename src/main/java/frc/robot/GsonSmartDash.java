package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GsonSmartDash {

    static boolean shouldRecord;
    static String gsonFileName;

    public static void put() {
        SmartDashboard.putBoolean("Should Record", false);
        SmartDashboard.putString("Gson File Name", "");
    }

    public static void set() {
        shouldRecord = SmartDashboard.getBoolean("Should Record", false);
        gsonFileName = SmartDashboard.getString("Gson File Name", "notGood");
    }
}
