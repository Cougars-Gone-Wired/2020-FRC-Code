package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Chomper {

    private Solenoid chomperSolenoid;

    public Chomper() {
        chomperSolenoid = new Solenoid(Constants.CHOMPER_SOLENOID_PORT);
        initialize();
    }

    public void initialize() {
        setIdle();
    }

    public enum ChomperStates {
        IDLE, INTAKE_READY, SHOOTER_READY, OVERRIDE
    }

    private ChomperStates currentChomperState;

    public void controlChomper(double overrideAxis) {

        switch (currentChomperState) {
            case IDLE:
                if (Robot.feeder.isIntaking() || Robot.feeder.isOuttaking()) {
                    setIntakeReady();
                } else if (Robot.feeder.isFeedingShooter()) {
                    setShooterReady();
                } else if (overrideAxis <= -Constants.DEADZONE) {
                    setOverride();
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

            case OVERRIDE:
                if (overrideAxis > -Constants.DEADZONE) {
                    setIdle();
                }
                break;
        }
    }

    public boolean isIdle() {
        return currentChomperState == ChomperStates.IDLE;
    }

    public boolean isIntakeReady() {
        return currentChomperState == ChomperStates.INTAKE_READY;
    }

    public boolean isShooterReady() {
        return currentChomperState == ChomperStates.SHOOTER_READY;
    }

    public void setIdle() {
        chomperSolenoid.set(true);
        currentChomperState = ChomperStates.IDLE;
    }

    public void setIntakeReady() {
        chomperSolenoid.set(true);
        currentChomperState = ChomperStates.INTAKE_READY;
    }

    public void setShooterReady() {
        chomperSolenoid.set(false);
        currentChomperState = ChomperStates.SHOOTER_READY;
    }

    public void setOverride() {
        chomperSolenoid.set(false);
        currentChomperState = ChomperStates.OVERRIDE;
    }
}