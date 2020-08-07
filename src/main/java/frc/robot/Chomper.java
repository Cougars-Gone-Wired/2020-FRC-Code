package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

// the system that "chomps" down on the balls so that they can be shot
public class Chomper {

    private Solenoid chomperSolenoid; // false - down, true - up

    private boolean chomperAxisBool;

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
        chomperAxisBool = overrideAxis <= -Constants.DEADZONE;

        switch (currentChomperState) {
            case IDLE:
                if (Robot.shooter.isShooting()) {
                    setShooterReady();
                } else if (chomperAxisBool) {
                    setOverride();
                }
                break;

            case SHOOTER_READY:
                if (Robot.shooter.isNotMoving()) {
                    setIdle();
                }
                break;

            case OVERRIDE:
                if (!chomperAxisBool) {
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