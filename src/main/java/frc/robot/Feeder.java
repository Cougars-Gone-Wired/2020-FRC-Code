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
        switch(currentFeederState) {
            case NOT_MOVING:
                if (feederAxis >= Constants.DEADZONE) {
                    feeding();
                }
                if (feederAxis <= -Constants.DEADZONE) {
                    outtaking();
                }
                break;

            case FEEDING:
                if (feederAxis < Constants.DEADZONE) {
                    notMoving();
                }
                break;

            case OUTTAKING:
                if (feederAxis > -Constants.DEADZONE) {
                    notMoving();
                }
                break;
        }
    }

    public void notMoving() {
        feederMotor.set(0);
        currentFeederState = FeederStates.NOT_MOVING;
    }

    public void feeding() {
        feederMotor.set(FEEDER_SPEED);
        currentFeederState = FeederStates.FEEDING;
    }

    public void outtaking() {
        feederMotor.set(-FEEDER_SPEED);
        currentFeederState = FeederStates.OUTTAKING;
    }
}
