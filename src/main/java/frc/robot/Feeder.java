package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Feeder {
    private static final double FEEDER_SPEED = 1;

    private WPI_TalonSRX feederMotor;

    public Feeder() {
        feederMotor = new WPI_TalonSRX(Constants.FEEDER_MOTOR_ID);
        initialize();
    }

    public void initialize() {
        feederMotor.set(0);
        currentFeederState = FeederStates.NOT_MOVING;
    }

    public enum FeederStates {
        NOT_MOVING, FEEDING, OUTTAKING
    }

    private FeederStates currentFeederState;

    public void feed(double feederAxis) {
        switch(currentState) {
            case NOT_MOVING:
                if (feederAxis >= Constants.DEADZONE) {
                    feederMotor.set(FEEDER_SPEED);
                    currentFeederState = FeederStates.FEEDING;
                }
                if (feederAxis <= -Constants.DEADZONE) {
                    feederMotor.set(-FEEDER_SPEED);
                    currentFeederState = FeederStates.OUTTAKING;
                }
                break;

            case FEEDING:
                if (feederAxis < Constants.DEADZONE) {
                    feederMotor.set(0);
                    currentFeederState = FeederStates.NOT_MOVING;
                }
                break;

            case OUTTAKING:
                if (feederAxis > -Constants.DEADZONE) {
                    feederMotor.set(0);
                    currentFeederState = FeederStates.NOT_MOVING;
                }
                break;
        }
    }
}
