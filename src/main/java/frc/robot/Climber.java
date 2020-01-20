package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Climber {
    private static double LIFT_SPEED = 0.3;
    private WPI_TalonSRX climbMotor;
    private boolean upTriggerBool;
    private boolean downTriggerBool;

    public Climber() {
        climbMotor = new WPI_TalonSRX(42);

    }

    public void initalize() {
        climbMotor.set(0);
        climbState = ClimbStates.NOT_MOVING;
    }

    private enum ClimbStates {
        NOT_MOVING, MOVING_UP, MOVING_DOWN
    }
    private ClimbStates climbState;

    public void climb(double upTrigger, double downTrigger) {
        upTriggerBool = (upTrigger >= Constants.DEADZONE);
        downTriggerBool = (downTrigger >= Constants.DEADZONE);
        
        switch (climbState) {
            case NOT_MOVING:
                if (upTriggerBool && !downTriggerBool) {
                    climbMotor.set(LIFT_SPEED);
                    climbState = ClimbStates.MOVING_UP;
                }
                if (downTriggerBool && !upTriggerBool) {
                    climbMotor.set(-LIFT_SPEED);
                    climbState = ClimbStates.MOVING_DOWN;
                }
                break;

            case MOVING_UP:
                if (!upTriggerBool || downTriggerBool) {
                    climbMotor.set(0);
                    climbState = ClimbStates.NOT_MOVING;
                }
                break;
        
            case MOVING_DOWN:
                if (!downTriggerBool || upTriggerBool) {
                    climbMotor.set(0);
                    climbState = ClimbStates.NOT_MOVING;
                }
                break;

        }
    }
}