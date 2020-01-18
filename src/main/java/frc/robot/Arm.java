package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Arm{
    private static final double ARM_SPEED = 1;
    private WPI_TalonSRX armMotor;
    private States currentState;

    public Arm() {
        armMotor = new WPI_TalonSRX(0);
        currentState = States.NOT_MOVING;
    }
    public void initialize() {
        armMotor.set(0);

        currentState = States.NOT_MOVING;
    }
    public enum States {
        NOT_MOVING, MOVING_UP, MOVING_DOWN
    }

    

    

    public void arm(double armAxis) {
        switch(currentState) {
            case NOT_MOVING:
                if (armAxis > .15) {
                    armMotor.set(ARM_SPEED);
                    currentState = States.MOVING_UP;
                }
                else if (armAxis < -.15) {
                    armMotor.set(ARM_SPEED);
                    currentState = States.MOVING_DOWN
                }
                break;
            case MOVING_UP:
                if (armAxis <= .15) {
                    armMotor.set(ARM_SPEED);
                    currentState = States.NOT_MOVING;
                }
                break;
            case MOVING_DOWN:
                if (armAxis >= -.15) {
                    armMotor.set(ARM_SPEED);
                    currentState = States.NOT_MOVING;
                }
                break;
            
        
        }
    }





}