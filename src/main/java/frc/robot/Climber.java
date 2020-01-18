package frc.robot;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
public class Climber {
    private static double LIFT_SPEED = 0.3;
    private WPI_TalonSRX climbMotor;
    private thresh = 0.5;

    public Climber() {
        climbMotor = new WPI_TalonSRX(42);


        // initMotors();
    }

    public void initMotors() {
        // midLeftMotor.setNeutralMode(NeutralMode.Brake);
        // midLeftMotor.configOpenloopRamp(0);
    
    }

    private enum ClimbStates {
        STATIONARY, LEVITATING, DESCENDING
    }
    private ClimbStates climbState;

    public void initalize() {
        climbMotor.set(0);
        climbState = ClimbStates.STATIONARY;
    }

    public void climb(double upTrigger, double downTrigger) {
        if (upTrigger >= thresh) {
            upTriggerBool = true;
        }else{
            upTriggerBool = false;
        }

        if (downTrigger >= thresh) {
            downTriggerBool = true;
        }else{
            downTriggerBool = false;
        }

        switch (climbState) {
            case LEVITATING:
                if (!upTriggerBool) {
                    climbMotor.set(0);
                    climbState = ClimbStates.STATIONARY;
                }
                break;
        
            case DESCENDING:
                if (!downTriggerBool) {
                    climbMotor.set(0);
                    climbState = ClimbStates.STATIONARY;
                }
                break;

            default:
                if (upTriggerBool) {
                    climbMotor.set(LIFT_SPEED);
                    climbState = ClimbStates.LEVITATING;
                }
                if (downTriggerBool) {
                    climbMotor.set(-LIFT_SPEED);
                    climbState = ClimbStates.DESCENDING;
                }
                break;
        }
    }
}