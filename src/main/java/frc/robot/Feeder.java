package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Feeder {
    private static final double FEEDER_SPEED = 1;
    private WPI_TalonSRX feederMotor;

    public Feeder() {
        feederMotor = new WPI_TalonSRX(0);
        currentState = States.NOT_MOVING;
    }

    public void initialize() {
        feederMotor.set(0);
        currentState = States.NOT_MOVING;
    }

    public enum States {
        NOT_MOVING, FEEDING
    }

    private States currentState;

    public void feed(boolean feedButton) {
        switch(currentState) {
            case NOT_MOVING:
                if (feedButton) {
                    feederMotor.set(FEEDER_SPEED);
                    currentState = States.FEEDING;
                }
                break;
            
            case FEEDING:
                if (!feedButton) {
                    feederMotor.set(0);
                    currentState = States.NOT_MOVING;
                }
                break;
        }
    }
}
