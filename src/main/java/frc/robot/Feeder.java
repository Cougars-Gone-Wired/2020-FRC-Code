package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Feeder {
    private static final double FEEDER_SPEED = 0.6;

    private WPI_TalonSRX feederMotor;

    public Feeder() {
        feederMotor = new WPI_TalonSRX(Constants.FEEDER_MOTOR_ID);
        initialize();
    }

    public void initialize() {
        setNotMoving();
    }

    public enum FeederStates {
        NOT_MOVING, FEEDING, OUTTAKING
    }

    private FeederStates currentFeederState;

    public void feed(double feederAxis) {
        switch(currentFeederState) {
            case NOT_MOVING:
                if (feederAxis >= Constants.DEADZONE) {
                    setFeeding(FEEDER_SPEED);
                }
                else if (feederAxis <= -Constants.DEADZONE) {
                    setOuttaking(-FEEDER_SPEED);
                }
                break;

            case FEEDING:
                if (feederAxis < Constants.DEADZONE) {
                    setNotMoving();
                }
                break;

            case OUTTAKING:
                if (feederAxis > -Constants.DEADZONE) {
                    setNotMoving();
                }
                break;
        }
    }

    public FeederStates getCurrentFeederState() {
        return currentFeederState;
    }

    public void setNotMoving() {
        feederMotor.set(0);
        currentFeederState = FeederStates.NOT_MOVING;
    }

    public void setFeeding(double feederSpeed) {
        feederMotor.set(feederSpeed);
        currentFeederState = FeederStates.FEEDING;
    }

    public void setOuttaking(double outtakeSpeed) {
        feederMotor.set(outtakeSpeed);
        currentFeederState = FeederStates.OUTTAKING;
    }
}
