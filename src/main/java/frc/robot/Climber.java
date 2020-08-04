package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;

public class Climber {
    private static double CLIMBER_UP_SPEED = 1.0;
    private static double CLIMBER_DOWN_SPEED = 0.7;

    private static double startTime;

    private WPI_TalonSRX climberLeftMotor;
    private WPI_TalonSRX climberRightMotor;
    private SpeedControllerGroup climberMotors;

    private boolean climberUpTriggerBool;
    private boolean climberDownTriggerBool;

    public Climber() {
        climberLeftMotor = new WPI_TalonSRX(Constants.CLIMBER_LEFT_MOTOR_ID);
        climberRightMotor = new WPI_TalonSRX(Constants.CLIMBER_RIGHT_MOTOR_ID);
        climberMotors = new SpeedControllerGroup(climberLeftMotor, climberRightMotor);
        initalize();
    }

    public void initalize() {
        startTime = Timer.getFPGATimestamp();
        setNotMoving();
    }

    private enum ClimberStates {
        NOT_MOVING, MOVING_UP, TOP, MOVING_DOWN
    }

    private ClimberStates currentClimberState;

    public void controlClimber(double climberUpTrigger, double climberDownTrigger) {
        climberUpTriggerBool = (climberUpTrigger >= Constants.DEADZONE);
        climberDownTriggerBool = (climberDownTrigger >= Constants.DEADZONE);
        
        switch (currentClimberState) {
            case NOT_MOVING:
                if (climberUpTriggerBool && !climberDownTriggerBool 
                        && Robot.arms.isArmClimbingPosition()) {
                    setMovingUp();
                }
                break;

            case MOVING_UP:
                if (!climberUpTriggerBool || climberDownTriggerBool) {
                    setTop();
                }
                break;

            // top state necessary so that climber can only go down once it has already gone up 
            // so climber doesn' break robot when first activated
            case TOP:
                if (climberUpTriggerBool && !climberDownTriggerBool 
                        && Robot.arms.isArmClimbingPosition()) {
                    setMovingUp();
                } else if (climberDownTriggerBool && !climberUpTriggerBool
                        && Robot.arms.isArmClimbingPosition()) {
                    setMovingDown();   
                }
                break;
        
            case MOVING_DOWN:
                if (!climberDownTriggerBool || climberUpTriggerBool) {
                    setTop();
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
        climberMotors.set(0);
        currentClimberState = ClimberStates.NOT_MOVING;
    }

    public void setMovingUp() {
        climberMotors.set(CLIMBER_UP_SPEED);
        currentClimberState = ClimberStates.MOVING_UP;
    }

    public void setTop() {
        climberMotors.set(0);
        currentClimberState = ClimberStates.TOP;
    }

    public void setMovingDown() {
        climberMotors.set(-CLIMBER_DOWN_SPEED);
        currentClimberState = ClimberStates.MOVING_DOWN;
    }

    public void setMotorsBrake() {
        climberLeftMotor.setNeutralMode(NeutralMode.Brake);
        climberRightMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setMotorsCoast() {
        climberLeftMotor.setNeutralMode(NeutralMode.Coast);
        climberRightMotor.setNeutralMode(NeutralMode.Coast);
    }

    // so that the climber won't be able to be activated until the last 30 seconds of match
    public static boolean isClimbTime() {
        return Timer.getFPGATimestamp() - startTime > 120;
    }
}