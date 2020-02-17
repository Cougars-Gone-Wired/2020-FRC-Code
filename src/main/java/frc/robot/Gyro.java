package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import frc.robot.Constants.DriveConstants;

public class Gyro {

    private AHRS navX;

    public Gyro() {
        navX = new AHRS(SPI.Port.kMXP);
    }

    public void zeroHeading() {
        navX.zeroYaw();
    }

    // ensures angle from gyro read from -180 to 180
    public double getHeading() {
        return Math.IEEEremainder(navX.getAngle(), 360) * (DriveConstants.isGyroReversed ? -1.0 : 1.0);
    }

    public double getTurnRate() {
        return navX.getRate() * (DriveConstants.isGyroReversed ? -1.0 : 1.0);
    }
}