package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Arm {
    private static final double ARM_SPEED = 1;
    private WPI_TalonSRX armMotor;
    private Solenoid solenoid1;
    private Solenoid solenoid2;

    public Arm() {
        armMotor = new WPI_TalonSRX(0);
        solenoid1 = new Solenoid(0);
        solenoid2 = new Solenoid(1);

    }

    public void initialize() {
        armMotor.set(0);
        currentState = States.NOT_MOVING;
        solenoid1.set(false);
        solenoid2.set(true);
        currentPistonState = PistonStates.STARTING_CONFIG;
    }

    public enum States {
        NOT_MOVING, MOVING_UP, MOVING_DOWN
    }

    private States currentState;

    private enum PistonStates {
        CLIMBING_POSITION, STARTING_CONFIG, SHOOTING_POSITION
    }

    private PistonStates currentPistonState;

    public void arm(double armAxis) {
        switch (currentState) {
        case NOT_MOVING:
            if (armAxis > .15) {
                armMotor.set(ARM_SPEED);
                currentState = States.MOVING_UP;
            } else if (armAxis < -.15) {
                armMotor.set(ARM_SPEED);
                currentState = States.MOVING_DOWN;
            }
            break;
        case MOVING_UP:
            if (armAxis <= .15) {
                armMotor.set(ARM_SPEED);
                currentState = States.NOT_MOVING;
            }
            break;
        case MOVING_DOWN:
            if (armAxis >= -.15) {
                armMotor.set(ARM_SPEED);
                currentState = States.NOT_MOVING;
            }
            break;

        }
    }

    public void pistonArm(boolean climberPositionButton, boolean startConfigButton, boolean shooterPositionButton) {
        switch (currentPistonState) {
        case STARTING_CONFIG:
            if (climberPositionButton) {
                solenoid1.set(true);
                solenoid2.set(true);
                currentPistonState = PistonStates.CLIMBING_POSITION;
            } else if (shooterPositionButton) {
                solenoid1.set(false);
                solenoid2.set(false);
                currentPistonState = PistonStates.SHOOTING_POSITION;
            }
            break;
        case CLIMBING_POSITION:
            if (startConfigButton) {
                solenoid1.set(false);
                solenoid2.set(true);
                currentPistonState = PistonStates.STARTING_CONFIG;
            } else if (shooterPositionButton) {
                solenoid1.set(false);
                solenoid2.set(false);
                currentPistonState = PistonStates.SHOOTING_POSITION;
            }
            break;
        case SHOOTING_POSITION:
            if (climberPositionButton) {
                solenoid1.set(true);
                solenoid2.set(true);
                currentPistonState = PistonStates.CLIMBING_POSITION;
            } else if (startConfigButton) {
                solenoid1.set(false);
                solenoid2.set(true);
                currentPistonState = PistonStates.STARTING_CONFIG;
            }
            break;
        }
    }

}