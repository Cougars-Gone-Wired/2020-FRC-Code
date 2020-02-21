package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;

public class Climber {

    private static double startTime;

    private static double CLIMBER_UP_SPEED = 1.0;
    private static double CLIMBER_DOWN_SPEED = 0.7;

    private WPI_TalonSRX climbMotor1;
    private WPI_TalonSRX climbMotor2;

    private SpeedControllerGroup climbMotors;

    private boolean climberUpTriggerBool;
    private boolean climberDownTriggerBool;

    public Climber() {
        climbMotor1 = new WPI_TalonSRX(Constants.CLIMBER_MOTOR_ID_1);
        climbMotor2 = new WPI_TalonSRX(Constants.CLIMBER_MOTOR_ID_2);

        climbMotors = new SpeedControllerGroup(climbMotor1, climbMotor2);

        initalize();
    }

    public void initalize() {
        startTime = Timer.getFPGATimestamp();
        setNotMoving();
    }

    private enum ClimberStates {
        NOT_MOVING, MOVING_UP, MOVING_DOWN
    }

    private ClimberStates currentClimberState;

    public void controlClimb(double climberUpTrigger, double climberDownTrigger) {
        climberUpTriggerBool = (climberUpTrigger >= Constants.DEADZONE);
        climberDownTriggerBool = (climberDownTrigger >= Constants.DEADZONE);
        
        switch (currentClimberState) {
            case NOT_MOVING:
                if (climberUpTriggerBool && !climberDownTriggerBool 
                        && Robot.arms.isArmClimbingPosition()
                        && isClimbTime()) {
                    setMovingUp();
                } else if (climberDownTriggerBool && !climberUpTriggerBool) {
                    setMovingDown();   
                }
                break;

            case MOVING_UP:
                if (!climberUpTriggerBool || climberDownTriggerBool) {
                    setNotMoving();
                }
                break;
        
            case MOVING_DOWN:
                if (!climberDownTriggerBool || climberUpTriggerBool) {
                    setNotMoving();
                }
                break;
        }
    }

    public boolean isNotMoving() {
        return currentClimberState == ClimberStates.NOT_MOVING;
    }

    public boolean isMovingUp() {
        return currentClimberState == ClimberStates.MOVING_UP;
    }
    
    public boolean isMovingDown() {
        return currentClimberState == ClimberStates.MOVING_DOWN;
    }

    public void setNotMoving() {
        climbMotors.set(0);
        currentClimberState = ClimberStates.NOT_MOVING;
    }

    public void setMovingUp() {
        climbMotors.set(CLIMBER_UP_SPEED);
        currentClimberState = ClimberStates.MOVING_UP;
    }

    public void setMovingDown() {
        climbMotors.set(-CLIMBER_DOWN_SPEED);
        currentClimberState = ClimberStates.MOVING_DOWN;
    }

    public void setMotorsBrake() {
        climbMotor1.setNeutralMode(NeutralMode.Brake);
        climbMotor2.setNeutralMode(NeutralMode.Brake);
    }

    public void setMotorsCoast() {
        climbMotor1.setNeutralMode(NeutralMode.Coast);
        climbMotor2.setNeutralMode(NeutralMode.Coast);
    }

    public static boolean isClimbTime() {
        return Timer.getFPGATimestamp() - startTime > 120;
    }
}