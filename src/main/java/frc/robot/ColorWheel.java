package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class ColorWheel {
 
    private static final double COLORWHEEL_SPEED = .4;
    private WPI_TalonSRX wheelMotor;

    public ColorWheel() {
        wheelMotor = new WPI_TalonSRX(0);
        currentState = States.NOT_MOVING;
    }

    public void initialize() {
        wheelMotor.set(0);
        currentState = States.NOT_MOVING;
    }

    public enum States {
        NOT_MOVING, SPINNING
    }

    private States currentState;
    public void spin(boolean spinButton) {
        switch(currentState) {
            case NOT_MOVING:
                if (spinButton) {
                    feederMotor.set(COLORWHEEL_SPEED);
                    currentState = States.SPINNING;
                }
                break;
                
            case SPINNING:
                if (!spinButton) {
                    wheelMotor.set(0);
                    currentState = States.NOT_MOVING;
                }
                break;
        }
    }


}