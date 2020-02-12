package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Chomper {
    private Solenoid chompSolenoid;

    public Chomper() {
        chompSolenoid = new Solenoid(Constants.CHOMPER_SOLENOID_PORT);
        initialize();
    }

    public void initialize() {
        setIdle();
    }

    public enum ChompStates {
        IDLE, INTAKE_READY, SHOOTER_READY
    }

    private ChompStates currentChompState;

    public void controlChomp() {

        switch(currentChompState) {
            case IDLE:
                if (Robot.feeder.isIntaking()) {
                    setIntakeReady();
                } else if (Robot.feeder.isFeedingShooter()) {
                    setShooterReady();
                }
                break;

            case INTAKE_READY:
                if (Robot.feeder.isNotMoving()) {
                    setIdle();
                }
                break;

            case SHOOTER_READY:
                if (Robot.feeder.isNotMoving()) {
                    setIdle();
                }
            break;
        }
    }

    public boolean isIdle() {
        return currentChompState == ChompStates.IDLE;
    }

    public boolean isIntakeReady() {
        return currentChompState == ChompStates.INTAKE_READY;
    }

    public boolean isShooterReady() {
        return currentChompState == ChompStates.SHOOTER_READY;
    }

    public void setIdle() {
        chompSolenoid.set(false);
        currentChompState = ChompStates.IDLE;
    }

    public void setIntakeReady() {
        chompSolenoid.set(true);
        currentChompState = ChompStates.INTAKE_READY;
    }

    public void setShooterReady() {
        chompSolenoid.set(false);
        currentChompState = ChompStates.SHOOTER_READY;
    }
}