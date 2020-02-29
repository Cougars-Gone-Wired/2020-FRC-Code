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
        IDLE, SHOOTER_READY, OVERRIDE
    }

    private ChomperStates currentChomperState;

    public void controlChomper(double overrideAxis) {

        switch (currentChomperState) {
            case IDLE:
                if (Robot.shooter.isShooting()) {
                    setShooterReady();
                } else if (overrideAxis <= -Constants.DEADZONE) {
                    setOverride();
                }
                break;

            case SHOOTER_READY:
                if (Robot.shooter.isNotMoving()) {
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

    public boolean isShooterReady() {
        return currentChomperState == ChomperStates.SHOOTER_READY;
    }

    public void setIdle() {
        chomperSolenoid.set(true);
        currentChomperState = ChomperStates.IDLE;
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