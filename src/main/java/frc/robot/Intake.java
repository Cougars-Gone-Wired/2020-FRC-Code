package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
    private static final double INTAKE_SPEED = 1;
    private WPI_TalonSRX intakeMotor;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(0);
        currentState = States.NOT_MOVING;
    }

    public void initialize() {
        intakeMotor.set(0);
        currentState = States.NOT_MOVING;
    }

    public enum States {
        NOT_MOVING, INTAKING
    }

    private States currentState;

    public void intake(boolean intakeButton) {

        switch(currentState) {
            case NOT_MOVING:
                if (intakeButton) {
                    intakeMotor.set(INTAKE_SPEED);
                    currentState = States.INTAKING;
                }
                break;

            case INTAKING:
                if (!intakeButton) {
                    intakeMotor.set(0);
                    currentState = States.NOT_MOVING;
                }
                break;
        }
    }
}