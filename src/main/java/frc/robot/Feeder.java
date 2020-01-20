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
        NOT_MOVING, FEEDING
    }

    private FeederStates currentFeederState;

    public void feed(boolean feederButton) {
        switch(currentFeederState) {
            case NOT_MOVING:
                if (feederButton) {
                    feederMotor.set(FEEDER_SPEED);
                    currentFeederState = FeederStates.FEEDING;
                }
                break;
            
            case FEEDING:
                if (!feederButton) {
                    feederMotor.set(0);
                    currentFeederState = FeederStates.NOT_MOVING;
                }
                break;
        }
    }
}
