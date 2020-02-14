package frc.robot;

public class Constants {

    // Manipulator Controller
    static final int MANIPULATOR_CONTROLLER_ID = 0;
    static final int ARM_UP_BUTTON = 5;
    static final int ARM_DOWN_BUTTON = 6;
    static final int INTAKE_ARM_TRIGGER = 2;
    static final int INTAKE_AXIS = 1;
    static final int SHOOTER_TRIGGER = 3;
    static final int STOP_CAMERA_BUTTON = 8;

    // Mobility Controller
    static final int MOBILITY_CONTROLLER_ID = 1;
    static final int CLIMBER_UP_TRIGGER = 3;
    static final int CLIMBER_DOWN_TRIGGER = 2;
    static final int DRIVE_SPEED_AXIS = 1;
    static final int DRIVE_TURN_AXIS = 4;
    static final int SWITCH_SIDE_BUTTON = 1;
    static final int LIMELIGHT_BUTTON = 2;

    static final double DEADZONE = 0.15;

    // Manipulator Ids
        //Solenoids
    static final int ARM_TOP_SOLENOID_PORT_1 = 0;
    static final int ARM_TOP_SOLENOID_PORT_2 = 1;
    static final int ARM_BOTTOM_SOLENOID_PORT_1 = 2;
    static final int ARM_BOTTOM_SOLENOID_PORT_2 = 3;
    static final int INTAKE_ARM_SOLENOID_PORT_1 = 4;
    static final int INTAKE_ARM_SOLENOID_PORT_2 = 5;
    static final int CHOMPER_SOLENOID_PORT = 6;
        //Motors
    static final int INTAKE_MOTOR_ID = 0;
    static final int SHOOTER_MOTOR_ID = 1;
    static final int FEEDER_MOTOR_ID = 2;
    static final int CONTROL_PANEL_MOTOR_ID = 3;
        //Sensors
    static final int FEEDER_LINEBREAK_PORT = 1;

    // Mobility Ids
    static final int CLIMBER_MOTOR_ID = 4;
    static final int FRONT_LEFT_MOTOR_ID = 5;
    static final int MIDDLE_LEFT_MOTOR_ID = 6;
    static final int BACK_LEFT_MOTOR_ID = 7;
    static final int FRONT_RIGHT_MOTOR_ID = 8;
    static final int MIDDLE_RIGHT_MOTOR_ID = 9;
    static final int BACK_RIGHT_MOTOR_ID = 10;
}