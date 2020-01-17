package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
    // We can use intake speed for both funnel motor and intake motor
    private static final double INTAKE_SPEED = 1;
    private WPI_TalonSRX intakeMotor;
    // private WPI_TalonSRX funnelMotor;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(0);
        currentState = States.NOT_MOVING;
    }

    public enum States {
        NOT_MOVING, INTAKING
    }

    private States currentState;

    public void initialize() {
        intakeMotor.set(0);

        currentState = States.NOT_MOVING;
    }

    public void intake(boolean intakeButton) {
        // Can use same states for it
        // Runs off same button as intake
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