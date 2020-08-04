package frc.robot.recorder;

import java.io.File;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// class creates a drop down on shuffleboard so a recording to be run can be selected
public class StateLister {

    public static SendableChooser<String> gsonChooser = new SendableChooser<>();

    public static void getStateNames() {

        try {
            File dir = new File("/home/lvuser/gsonFiles/");
            File[] files = dir.listFiles();
    
            for (int i = 0; i < files.length; i++) {
                gsonChooser.addOption(files[i].getName(), files[i].getAbsolutePath());
            }
            SmartDashboard.putData("Gson choices", gsonChooser);    
        } catch(Exception e) {

        }
    }
}
