package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Feeder {

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
                    setFeeding(feederAxis);
                }
                if (feederAxis <= -Constants.DEADZONE) {
                    setOuttaking(feederAxis);
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
