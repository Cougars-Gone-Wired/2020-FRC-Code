package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    private static final double SHOOTER_SPEED = 0.45;
    static double P = 0.4; // 0.46
    static double I = 0.001; // 0.001
    static int IZONE = 80; // 180
    static double D = 10; // 15
    static double F = 0.0465; // 0.048
    static double DESIRED_VELOCITY = 0;
    static double VELOCITY_THRESHOLD = 20;

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
        setShooterDashboard();
        setNotMoving();
        initShooterMotor();
    }

    public void initShooterMotor() {
        shooterMotor.setInverted(true);
        shooterMotor.config_kP(0, P, 10);
        shooterMotor.config_kI(0, I, 10);
        shooterMotor.config_IntegralZone(0, IZONE, 10);
        shooterMotor.config_kD(0, D, 10);
        shooterMotor.config_kF(0, F, 10);
    }

    public void setShooterDashboard() {
        SmartDashboard.putBoolean("Set Constants", setConstants);
        SmartDashboard.putNumber("P", P);
        SmartDashboard.putNumber("I", I);
        SmartDashboard.putNumber("I Zone", IZONE);
        SmartDashboard.putNumber("D", D);
        SmartDashboard.putNumber("F", F);
        SmartDashboard.putNumber("Desired Velocity", DESIRED_VELOCITY);
    }

    public void shooterDashboard() {
        setConstants = SmartDashboard.getBoolean("Set Constants", false);
        if (setConstants) {
            P = SmartDashboard.getNumber("P", 0);
            I = SmartDashboard.getNumber("I", 0);
            IZONE = (int)SmartDashboard.getNumber("I Zone", 0);
            D = SmartDashboard.getNumber("D", 0);
            F = SmartDashboard.getNumber("F", 0);
            DESIRED_VELOCITY = SmartDashboard.getNumber("Desired Velocity", 0);
            initShooterMotor();
        }

        SmartDashboard.putNumber("Velocity", velocity);
        SmartDashboard.putNumber("Velocity Plot", velocity);
        SmartDashboard.putNumber("Velocity Error", error);
        SmartDashboard.putNumber("Velocity Error %", (error / velocity) * 100);
        SmartDashboard.putNumber("Temp", shooterMotor.getTemperature());
    }

    public enum ShooterStates {
        NOT_MOVING, SHOOTING
    }

    private ShooterStates currentShooterState;

    public void shoot(double shooterTrigger) {
        shooterDashboard();
        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);
        velocity = sensors.getIntegratedSensorVelocity();

        switch (currentShooterState) {
        case NOT_MOVING:
            if (shooterTriggerBool && !Robot.arms.isShooterClimbingPosition()) {
                setShooting();
            }
            break;
        case SHOOTING:
            if (!shooterTriggerBool || Robot.arms.isShooterClimbingPosition()) {
                setNotMoving();
            } 
            break;
        }
    }

    public void pidShooter(double shooterTrigger) {
        shooterDashboard();
        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);
        velocity = sensors.getIntegratedSensorVelocity();
        error = DESIRED_VELOCITY - velocity;

        switch (currentShooterState) {
            case NOT_MOVING:
                //if (shooterTriggerBool && !Robot.arms.isShooterClimbingPosition()) {
                if (shooterTriggerBool) {
                    setPIDShooting();
                }
                break;
            case SHOOTING:
                setPIDShooting();
                //if (!shooterTriggerBool || Robot.arms.isShooterClimbingPosition()) {
                if (!shooterTriggerBool) {
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

    public void setShooting() {
        shooterMotor.set(SHOOTER_SPEED);
        currentShooterState = ShooterStates.SHOOTING;
    }

    public void setPIDShooting() {
        shooterMotor.set(ControlMode.Velocity, DESIRED_VELOCITY);
        currentShooterState = ShooterStates.SHOOTING;
    }

    public boolean atDesiredVelocity() {
        return (velocity >= DESIRED_VELOCITY - VELOCITY_THRESHOLD) && (velocity <= DESIRED_VELOCITY + VELOCITY_THRESHOLD);
    }
}
