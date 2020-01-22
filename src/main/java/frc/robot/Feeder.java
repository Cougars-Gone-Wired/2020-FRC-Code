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
        NOT_MOVING, FEEDING, OUTTAKING
    }

    private States currentState;

    public void feed(double feederAxis) {
        switch(currentState) {
            case NOT_MOVING:
                if (feederAxis >= Constants.DEADZONE) {
                    feederMotor.set(FEEDER_SPEED);
                    currentState = States.FEEDING;
                }
                if (feederAxis <= -Constants.DEADZONE) {
                    feederMotor.set(-FEEDER_SPEED);
                    currentState = States.OUTTAKING;
                }
                break;

            case FEEDING:
                if (feederAxis < Constants.DEADZONE) {
                    feederMotor.set(0);
                    currentState = States.NOT_MOVING;
                }
                break;

            case OUTTAKING:
                if (feederAxis > -Constants.DEADZONE) {
                    feederMotor.set(0);
                    currentState = States.NOT_MOVING;
                }
                break;
        }
    }
}
