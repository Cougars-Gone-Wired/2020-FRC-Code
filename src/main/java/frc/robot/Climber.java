package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Climber {
    private static double LIFT_SPEED = 0.3;

    private WPI_TalonSRX climbMotor;

    private boolean climberUpTriggerBool;
    private boolean climberDownTriggerBool;

    public Climber() {
        climbMotor = new WPI_TalonSRX(Constants.CLIMBER_MOTOR_ID);
        initalize();
    }

    public void initalize() {
        climbMotor.set(0);
        currentClimbState = ClimbStates.NOT_MOVING;
    }

    private enum ClimbStates {
        NOT_MOVING, MOVING_UP, MOVING_DOWN
    }

    private ClimbStates currentClimbState;

    public void climb(double climberUpTrigger, double climberDownTrigger) {
        climberUpTriggerBool = (climberUpTrigger >= Constants.DEADZONE);
        climberDownTriggerBool = (climberDownTrigger >= Constants.DEADZONE);
        
        switch (currentClimbState) {
            case NOT_MOVING:
                if (climberUpTriggerBool && !climberDownTriggerBool) {
                    climbMotor.set(LIFT_SPEED);
                    currentClimbState = ClimbStates.MOVING_UP;
                }
                if (climberDownTriggerBool && !climberUpTriggerBool) {
                    climbMotor.set(-LIFT_SPEED);
                    currentClimbState = ClimbStates.MOVING_DOWN;
                }
                break;

            case MOVING_UP:
                if (!climberUpTriggerBool || climberDownTriggerBool) {
                    climbMotor.set(0);
                    currentClimbState = ClimbStates.NOT_MOVING;
                }
                break;
        
            case MOVING_DOWN:
                if (!climberDownTriggerBool || climberUpTriggerBool) {
                    climbMotor.set(0);
                    currentClimbState = ClimbStates.NOT_MOVING;
                }
                break;
        }
    }
}