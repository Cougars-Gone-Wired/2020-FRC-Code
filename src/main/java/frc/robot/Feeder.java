package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Feeder {
    private static final double FEED_INTAKE_SPEED = 0.6;

    private WPI_TalonSRX feederMotor;

    private DigitalInput feederUpperLineBreak;
    private DigitalInput feederLowerLineBreak;

    public Feeder() {
        feederMotor = new WPI_TalonSRX(Constants.FEEDER_MOTOR_ID);
        feederUpperLineBreak = new DigitalInput(Constants.FEEDER_UPPER_LINEBREAK_PORT);
        feederLowerLineBreak = new DigitalInput(Constants.FEEDER_LOWER_LINEBREAK_PORT);
        initialize();
    }

    public void initialize() {
        setNotMoving();
    }

    public enum FeederStates {
        NOT_MOVING, INTAKING, OUTTAKING
    }

    private FeederStates currentFeederState;

    public void feed(double feederAxis) {
        switch(currentFeederState) {
            case NOT_MOVING:
                if (feederAxis >= Constants.DEADZONE && (feederUpperLineBreak.get() || feederLowerLineBreak.get())) {
                // if (feederAxis >= Constants.DEADZONE && !feederLineBreak.get()) {
                // if (feederAxis >= Constants.DEADZONE) {
                    setIntaking();

                } else if (feederAxis <= -Constants.DEADZONE) {
                    setOuttaking();
                }
                break;

            case INTAKING:
                // if(!feederUpperLineBreak.get()) {
                //     if(!feederLowerLineBreak.get()) {
                //         setNotMoving();
                //     }
                // }

                if(feederAxis < Constants.DEADZONE || !(feederUpperLineBreak.get() || feederLowerLineBreak.get())) {
                // if(feederAxis < Constants.DEADZONE || feederLineBreak.get()) {
                // if(feederAxis < Constants.DEADZONE) {
                    setNotMoving();
                }
                break;

            case OUTTAKING:
                if(feederAxis > -Constants.DEADZONE) {
                    setNotMoving();
                }
                break;
        }   
    }

    public boolean isNotMoving() {
        return currentFeederState == FeederStates.NOT_MOVING;
    }

    public boolean isIntaking() {
        return currentFeederState == FeederStates.INTAKING;
    }

    public boolean isOuttaking() {
        return currentFeederState == FeederStates.OUTTAKING;
    }

    public void setNotMoving() {
        feederMotor.set(0);
        currentFeederState = FeederStates.NOT_MOVING;
    }

    public void setIntaking() {
        feederMotor.set(FEED_INTAKE_SPEED);
        currentFeederState = FeederStates.INTAKING;
    }

    public void setOuttaking() {
        feederMotor.set(-FEED_INTAKE_SPEED);
        currentFeederState = FeederStates.OUTTAKING;
    }

    public void setMotorsBrake() {
        feederMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setMotorsCoast() {
        feederMotor.setNeutralMode(NeutralMode.Coast);
    }
}
