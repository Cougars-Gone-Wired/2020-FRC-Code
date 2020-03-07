package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    public static double SHOOTER_SPEED = 0.5;
    public static double F = 0.044;
    public static double P = 0.9;
    public static double I = 0.001;
    public static int IZONE = 1400;
    public static double D = 11;
    public static double VOLTAGE_TO_VELOCITY = 20000;
    public static double DESIRED_VELOCITY = SHOOTER_SPEED * VOLTAGE_TO_VELOCITY;
    public static double VOLTAGE_INITIAL_VELOCITY_THRESHOLD = 5;
    public static double PID_INITIAL_VELOCITY_THRESHOLD = 5;
    public static double VOLTAGE_VELOCITY_THRESHOLD = 50;
    public static double PID_VELOCITY_THRESHOLD = 70;

    private WPI_TalonFX shooterMotor;
    private TalonFXSensorCollection sensors;

    private boolean shooterTriggerBool;
    private boolean setConstants;

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
        setPID();
        initShooterMotor();
        setShooterDashboard();
        setNotMoving();
    }

    public void initShooterMotor() {
        shooterMotor.setInverted(true);
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

    public void controlVoltageShooter(double shooterTrigger) {
        shooterDashboard();
        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);
        updateVelocity();

        switch (currentShooterState) {
        case NOT_MOVING:
            if (shooterTriggerBool 
                    && !Robot.arms.isArmClimbingPosition()
                    && !Robot.intake.isIntaking()) {
                setVoltageShooting();
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
                setPIDShooting();
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

    public void setVoltageShooting(double shooterSpeed) {
        SHOOTER_SPEED = shooterSpeed;
        shooterMotor.set(shooterSpeed);
        currentShooterState = ShooterStates.SHOOTING;
    }

    public void setVoltageShooting() {
        setVoltageShooting(SHOOTER_SPEED);
    }

    public void setPIDShooting(double desiredVelocity) {
        DESIRED_VELOCITY = desiredVelocity;
        updateVelocity();
        shooterDashboard();
        shooterMotor.set(ControlMode.Velocity, desiredVelocity);
        currentShooterState = ShooterStates.SHOOTING;
    }

    public void setPIDShooting() {
        setPIDShooting(DESIRED_VELOCITY);
    }

    public void setVelocityThresholds(double initialVelocityThreshold, double velocityThreshold) {
        this.initialVelocityThreshold = initialVelocityThreshold;
        this.velocityThreshold = velocityThreshold;
    }

    public void updateVelocity() {
        velocity = -sensors.getIntegratedSensorVelocity();
        error = DESIRED_VELOCITY - velocity;
    }

    public boolean atInitialDesiredVelocity() {
        return Math.abs(error) <= initialVelocityThreshold;
    }

    public boolean atDesiredVelocity() {
        return Math.abs(error) <= velocityThreshold;
    }
}
