package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;

public class Gyro {

    private AHRS navX;

    private int gyroInvert = DriveConstants.isGyroReversed ? -1 : 1; // set to -1 if constant true, set to 1 if constant false

    public Gyro() {
        navX = new AHRS(SPI.Port.kMXP);
    }

    public void zeroHeading() {
        navX.zeroYaw();
    }

    // ensures angle from gyro read from -180 to 180
    public double getHeading() {
        return Math.IEEEremainder(navX.getAngle(), 360) * gyroInvert;
    }

    // returns turning velocity
    public double getTurnRate() {
        return navX.getRate() * gyroInvert;
    }

    public void dashboard() {
        SmartDashboard.putNumber("Gyro", getHeading());
    }
}