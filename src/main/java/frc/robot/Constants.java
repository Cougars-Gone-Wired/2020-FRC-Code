package frc.robot;

// stores all constants (except speed constants) used on the robot
public class Constants {

    // Mobility Controller
    static final int MOBILITY_CONTROLLER_ID = 1;
    static final int ARM_UP_BUMPER = 6;
    static final int ARM_DOWN_BUMPER = 5;
    static final int CLIMBER_UP_TRIGGER = 3;
    static final int CLIMBER_DOWN_TRIGGER = 2;
    static final int DRIVE_SPEED_AXIS = 1;
    static final int DRIVE_TURN_AXIS = 4;
    static final int DRIVE_SIDE_TOGGLE = 1;
    static final int LIMELIGHT_BUTTON = 2;
    static final int STOP_CAMERA_BUTTON = 8;

    // Manipulator Controller
    static final int MANIPULATOR_CONTROLLER_ID = 0;
    static final int INTAKE_ARM_DOWN_BUMPER = 6;
    static final int INTAKE_ARM_UP_BUMPER = 5;
    static final int INTAKE_AXIS = 1;
    static final int SHOOTER_TRIGGER = 3;
    static final int SHOOTER_MODE_TOGGLE = 8;
    static final int FEEDER_OUTTAKE_TRIGGER = 2;
    static final int CHOMPER_OVERRIDE_AXIS = 5;

    static final double DEADZONE = 0.15;

    // Mobility Ids
    static final int CLIMBER_LEFT_MOTOR_ID = 4;
    static final int CLIMBER_RIGHT_MOTOR_ID = 5;
    static final int FRONT_LEFT_MOTOR_ID = 6;
    static final int MIDDLE_LEFT_MOTOR_ID = 7;
    static final int BACK_LEFT_MOTOR_ID = 8;
    static final int FRONT_RIGHT_MOTOR_ID = 9;
    static final int MIDDLE_RIGHT_MOTOR_ID = 10;
    static final int BACK_RIGHT_MOTOR_ID = 11;

    // Manipulator Ids
        //Motors
    static final int INTAKE_MOTOR_ID = 0;
    static final int SHOOTER_MOTOR_ID = 1;
    static final int FEEDER_MOTOR_ID = 2;
    static final int CONTROL_PANEL_MOTOR_ID = 3;
        //Solenoids
    static final int ARM_TOP_SOLENOID_PORT_1 = 2;
    static final int ARM_TOP_SOLENOID_PORT_2 = 3;
    static final int ARM_BOTTOM_SOLENOID_PORT_1 = 0;
    static final int ARM_BOTTOM_SOLENOID_PORT_2 = 1;
    static final int INTAKE_ARM_SOLENOID_PORT_1 = 4;
    static final int INTAKE_ARM_SOLENOID_PORT_2 = 5;
    static final int CHOMPER_SOLENOID_PORT = 6;
        //Sensors
    static final int FEEDER_UPPER_LINEBREAK_PORT = 1;
    static final int FEEDER_LOWER_LINEBREAK_PORT = 2;
    
    // all drive train specific constants (including constants for motion profiling)
    public class DriveConstants {

        public static final double AUTO_DRIVE_SPEED = 0.4; // only used for bang bang contol
        public static final double DRIVE_OFF_LINE_DISTANCE = 1; // 3.71 meters back to shoot

        // Encoder Constants
        public static final int TICKS_PER_REVOLUTION = 2048; // standard for TalonFX
        public static final double WHEEL_DIAMETER = .1524; // in meters
        public static final double GEARING_CONVERSION = 5.3333333;
        public static final double DISTANCE_PER_TICK = (Math.PI * WHEEL_DIAMETER) / (TICKS_PER_REVOLUTION * GEARING_CONVERSION);
        public static final double METER_PER_SECOND_CONSTANT = DISTANCE_PER_TICK / 10; // for converting units per 100ms to meters per second

        public static final boolean areLeftEncodersReversed = false;
        public static final boolean areRightEncodersReversed = true;
        public static final boolean isGyroReversed = false;

        //Ramsete Constants
        // all constants in meters and seconds
        public static final double TRACK_WIDTH = 0.5714; // width bewteen each side of drive train in meters

        // constants from robot characterization tool
        public static final double ks = 0.218;//.23
        public static final double kv = 1.24;//1.2
        public static final double ka = 0.23;//.326

        // tuned constant, tuned like an P value, look for oscillation 
        public static final double DriveP = .6;//6

        // adjust to change speed when following path
        // Important that these constants are the same as in the setting of PathWeaver project
        public static final double maxVelocity = 1;
        public static final double maxAcceleration = 0.6;

        // don't change, given by documentation
        public static final double B = 2; // constant tuned for meters
        public static final double Zeta = 0.7; // ↑ ↑ ↑
    }
}