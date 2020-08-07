package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// the shooter system 
public class Shooter {
    public static double SHOOTER_SPEED = 0.5;//.47

    // all of these constants need to be tuned
    public static double F = 0.044;
    public static double P = 0.9;//.6
    public static double I = 0.001;
    public static int IZONE = 1400; // distance from desired velocity at which I kicks in
    public static double D = 11;
    
    public static double VOLTAGE_TO_VELOCITY = 20000; // constant to switch between % speed and raw velocity
    public static double DESIRED_VELOCITY = SHOOTER_SPEED * VOLTAGE_TO_VELOCITY; // goal is to only have to set the shooter speed constant so you don't have to figure out what speed corelates to what velocity

    // voltage mode velocity thresholds depend on the tested average velocity at a given shooter speed, but should be lower so that it is easier to start shooting 
    public static double VOLTAGE_INITIAL_VELOCITY_THRESHOLD = 5;//250
    public static double VOLTAGE_VELOCITY_THRESHOLD = 50;//500

    // PID mode velocity thresholds should ideaklly be closer to the desired velocity so that the shooter only shoots when it is up to speed
    public static double PID_INITIAL_VELOCITY_THRESHOLD = 5;
    public static double PID_VELOCITY_THRESHOLD = 70;

    private WPI_TalonFX shooterMotor;
    private TalonFXSensorCollection sensors;

    private boolean shooterTriggerBool;
    private boolean setConstants;

    private double shooterSpeed;
    private double desiredVelocity;

    private double initialVelocityThreshold;
    private double velocityThreshold;

    private double velocity;
    private double error;

    public Shooter() {
        shooterMotor = new WPI_TalonFX(Constants.SHOOTER_MOTOR_ID);
        sensors = new TalonFXSensorCollection(shooterMotor);
        initialize();
    }

    public void initialize() {
        // setVoltage();
        setPID();
        initShooterMotor();
        setShooterDashboard();
        setNotMoving();
        shooterSpeed = SHOOTER_SPEED;
        desiredVelocity = DESIRED_VELOCITY;
    }

    public void initShooterMotor() {
        shooterMotor.setInverted(true);
        shooterMotor.setNeutralMode(NeutralMode.Coast);

        // how PID is implemented on the motor controller
        shooterMotor.config_kF(0, F, 10);
        shooterMotor.config_kP(0, P, 10);
        shooterMotor.config_kI(0, I, 10);
        shooterMotor.config_IntegralZone(0, IZONE, 10);
        shooterMotor.config_kD(0, D, 10);
    }

    public void setShooterDashboard() {
        SmartDashboard.putBoolean("Set Constants", setConstants);
        SmartDashboard.putNumber("Shooter Voltage", SHOOTER_SPEED);
        SmartDashboard.putNumber("F", F);
        SmartDashboard.putNumber("P", P);
        SmartDashboard.putNumber("I", I);
        SmartDashboard.putNumber("I Zone", IZONE);
        SmartDashboard.putNumber("D", D);
        SmartDashboard.putNumber("Inital Velocity Thresh", PID_INITIAL_VELOCITY_THRESHOLD);
        SmartDashboard.putNumber("Velocity Thresh", PID_VELOCITY_THRESHOLD);
    }

    // used for PID tuning
    public void shooterDashboard() {
        setConstants = SmartDashboard.getBoolean("Set Constants", false);
        if (setConstants) {
            SHOOTER_SPEED = SmartDashboard.getNumber("Shooter Voltage", 0);
            F = SmartDashboard.getNumber("F", 0);
            P = SmartDashboard.getNumber("P", 0);
            I = SmartDashboard.getNumber("I", 0);
            IZONE = (int)SmartDashboard.getNumber("I Zone", 0);
            D = SmartDashboard.getNumber("D", 0);
            PID_INITIAL_VELOCITY_THRESHOLD = SmartDashboard.getNumber("Inital Velocity Thresh", 0);
            PID_VELOCITY_THRESHOLD= SmartDashboard.getNumber("Velocity Thresh", 0);
            DESIRED_VELOCITY = SmartDashboard.getNumber("Shooter Voltage", 0) * 20000;
            initShooterMotor();
        }

        SmartDashboard.putNumber("Desired Velocity", DESIRED_VELOCITY);
        SmartDashboard.putNumber("Velocity", velocity);
        SmartDashboard.putNumber("Velocity Plot", velocity);
        SmartDashboard.putNumber("Velocity Error", error);
        SmartDashboard.putNumber("Velocity Error %", (error / velocity) * 100);
        SmartDashboard.putNumber("Temp", shooterMotor.getTemperature());
    }

    public enum ShooterModes {
        VOLTAGE, PID
    }

    ShooterModes currentShooterMode;

    // used to switch between controlling the shooter with PID and shooting at a set speed
    public void controlShooter(double shooterTrigger, boolean shooterToggle) {
        
        switch(currentShooterMode) {
            case VOLTAGE:
                controlVoltageShooter(shooterTrigger);
                if(shooterToggle) {
                    setPID();
                }
                break;

            case PID:
                controlPIDShooter(shooterTrigger);
                if(shooterToggle) {
                    setVoltage();
                }
                break;
        }
    }

    public void setVoltage() {
        setVelocityThresholds(VOLTAGE_INITIAL_VELOCITY_THRESHOLD, VOLTAGE_VELOCITY_THRESHOLD);
        currentShooterMode = ShooterModes.VOLTAGE;
    }

    public void setPID() {
        setVelocityThresholds(PID_INITIAL_VELOCITY_THRESHOLD, PID_VELOCITY_THRESHOLD);
        currentShooterMode = ShooterModes.PID;
    }

    public enum ShooterStates {
        NOT_MOVING, SHOOTING
    }

    private ShooterStates currentShooterState;

    // call the control the shooter with a set speed
    public void controlVoltageShooter(double shooterTrigger) {
        shooterDashboard();
        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);
        updateVelocity();

        switch (currentShooterState) {
        case NOT_MOVING:
            if (shooterTriggerBool 
                    && !Robot.arms.isArmClimbingPosition()
                    && !Robot.intake.isIntaking()) {
                setVoltageShooting(); // sets shooter at constant voltage, so only needs to be called once
            }
            break;

        case SHOOTING:
            if (!shooterTriggerBool 
                    || Robot.arms.isArmClimbingPosition()
                    || Robot.intake.isIntaking()) {
                setNotMoving();
            } 
            break;
        }
    }

    // call to control the shooter with PID implementation
    public void controlPIDShooter(double shooterTrigger) {
        shooterDashboard();
        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);
        updateVelocity();

        switch (currentShooterState) {
            case NOT_MOVING:
                if (shooterTriggerBool 
                        && !Robot.arms.isArmClimbingPosition()
                        && !Robot.intake.isIntaking()) {
                    setPIDShooting();
                }
                break;

            case SHOOTING:
                setPIDShooting(); // called every cycle to adjust shooter voltage output
                if (!shooterTriggerBool 
                        || Robot.arms.isArmClimbingPosition()
                        || Robot.intake.isIntaking()) {
                    setNotMoving();
                } 
                break;
            }
    }

    public boolean isNotMoving() {
        return currentShooterState == ShooterStates.NOT_MOVING;
    }

    public boolean isShooting() {
        return currentShooterState == ShooterStates.SHOOTING;
    }

    public void setNotMoving() {
        shooterMotor.set(0);
        currentShooterState = ShooterStates.NOT_MOVING;
    }

    // call for voltage mode if setting speed to something other than speed constant
    public void setVoltageShooting(double shooterSpeed) {
        this.shooterSpeed = shooterSpeed;
        shooterMotor.set(shooterSpeed);
        currentShooterState = ShooterStates.SHOOTING;
    }

    // call for voltage mode if setting speed to the speed constant
    public void setVoltageShooting() {
        shooterSpeed = SHOOTER_SPEED;
        setVoltageShooting(shooterSpeed);
    }

    // call for PID mode if setting the desired velocity to something other than the desired velocity constant
    public void setPIDShooting(double desiredVelocity) {
        this.desiredVelocity = desiredVelocity;
        updateVelocity();
        shooterDashboard();
        shooterMotor.set(ControlMode.Velocity, desiredVelocity); // this is all of the PID implementation, simply set the constants on the motor controller, call this, and that's it
        currentShooterState = ShooterStates.SHOOTING;
    }

    // call for PID mode if setting the desired velocity to the desired velocity constant
    public void setPIDShooting() {
        desiredVelocity = DESIRED_VELOCITY;
        setPIDShooting(desiredVelocity);
    }

    // used to reset the velocity thresholds when switching between shooter modes
    public void setVelocityThresholds(double initialVelocityThreshold, double velocityThreshold) {
        this.initialVelocityThreshold = initialVelocityThreshold;
        this.velocityThreshold = velocityThreshold;
    }

    // call every cycle to update the velocity readings from the shooter motor
    public void updateVelocity() {
        velocity = -sensors.getIntegratedSensorVelocity();
        error = DESIRED_VELOCITY - velocity; // what PID is trying to minimize
    }

    // used to indicate when the feeder should start feeding the shooter
    public boolean atInitialDesiredVelocity() {
        return Math.abs(error) <= initialVelocityThreshold;
    }

    // used to indicate when the feeder should stop feeding the shooter (if false)
    public boolean atDesiredVelocity() {
        return Math.abs(error) <= velocityThreshold;
    }
}
