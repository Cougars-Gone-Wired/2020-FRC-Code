package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Climber {
    private static double CLIMBER_UP_SPEED = 1.0;
    private static double CLIMBER_DOWN_SPEED = 0.7;

    private WPI_TalonSRX climbMotor;

    private boolean climberUpTriggerBool;
    private boolean climberDownTriggerBool;

    public Climber() {
        climbMotor = new WPI_TalonSRX(Constants.CLIMBER_MOTOR_ID);
        initalize();
    }

    public void initalize() {
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
                        && Robot.isClimbTime()) {
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
        climbMotor.set(0);
        currentClimberState = ClimberStates.NOT_MOVING;
    }

    public void setMovingUp() {
        climbMotor.set(CLIMBER_UP_SPEED);
        currentClimberState = ClimberStates.MOVING_UP;
    }

    public void setMovingDown() {
        climbMotor.set(-CLIMBER_DOWN_SPEED);
        currentClimberState = ClimberStates.MOVING_DOWN;
    }

    public void setMotorsBrake() {
        climbMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setMotorsCoast() {
        climbMotor.setNeutralMode(NeutralMode.Coast);
    }
}