package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    public static double SHOOTER_SPEED = 0.65; //.32 arm up bumper back of bumper on line
    public static double F = 0.044;
    public static double P = 0.9;
    public static double I = 0.001;
    public static int IZONE = 1400;
    public static double D = 11;
    public static double DESIRED_VELOCITY = 10000;
    public static double INITIAL_VELOCITY_THRESHOLD = 5;
    public static double VELOCITY_THRESHOLD = 70;

    private WPI_TalonFX shooterMotor;
    private TalonFXSensorCollection sensors;

    private boolean shooterTriggerBool;
    private boolean setConstants;

    private double velocity;
    private double error;

    public Shooter() {
        shooterMotor = new WPI_TalonFX(Constants.SHOOTER_MOTOR_ID);
        sensors = new TalonFXSensorCollection(shooterMotor);
        initialize();
    }

    public void initialize() {
        currentShooterMode = ShooterModes.PID;

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
        SmartDashboard.putNumber("Inital Velocity Thresh", INITIAL_VELOCITY_THRESHOLD);
        SmartDashboard.putNumber("Velocity Thresh", VELOCITY_THRESHOLD);
        SmartDashboard.putNumber("Desired Velocity", DESIRED_VELOCITY);
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
            INITIAL_VELOCITY_THRESHOLD = SmartDashboard.getNumber("Inital Velocity Thresh", 0);
            VELOCITY_THRESHOLD= SmartDashboard.getNumber("Velocity Thresh", 0);
            DESIRED_VELOCITY = SmartDashboard.getNumber("Desired Velocity", 0);
            initShooterMotor();
        }

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
                    currentShooterMode = ShooterModes.PID;
                }
                break;

            case PID:
                controlPIDShooter(shooterTrigger);
                if(shooterToggle) {
                    currentShooterMode = ShooterModes.VOLTAGE;
                }
                break;
        }
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
        shooterMotor.set(shooterSpeed);
        currentShooterState = ShooterStates.SHOOTING;
    }

    public void setVoltageShooting() {
        setVoltageShooting(SHOOTER_SPEED);
    }

    public void setPIDShooting(double desiredVelocity) {
        updateVelocity();
        shooterDashboard();
        shooterMotor.set(ControlMode.Velocity, desiredVelocity);
        currentShooterState = ShooterStates.SHOOTING;
    }

    public void setPIDShooting() {
        setPIDShooting(DESIRED_VELOCITY);
    }

    public void updateVelocity() {
        velocity = -sensors.getIntegratedSensorVelocity();
        error = DESIRED_VELOCITY - velocity;
    }

    public boolean atInitialDesiredVelocity() {
        return Math.abs(error) <= INITIAL_VELOCITY_THRESHOLD;
    }

    public boolean atDesiredVelocity() {
        return Math.abs(error) <= VELOCITY_THRESHOLD;
    }
}
