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

    public enum ChomperStates {
        IDLE, INTAKE_READY, SHOOTER_READY
    }

    private ChomperStates currentChomperState;

    public void controlChomper() {

        switch(currentChomperState) {
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
        return currentChomperState == ChomperStates.IDLE;
    }

    public boolean isIntakeReady() {
        return currentChomperState == ChomperStates.INTAKE_READY;
    }

    public boolean isShooterReady() {
        return currentChomperState == ChomperStates.SHOOTER_READY;
    }

    public void setIdle() {
        chompSolenoid.set(false);
        currentChomperState = ChomperStates.IDLE;
    }

    public void setIntakeReady() {
        chompSolenoid.set(true);
        currentChomperState = ChomperStates.INTAKE_READY;
    }

    public void setShooterReady() {
        chompSolenoid.set(false);
        currentChomperState = ChomperStates.SHOOTER_READY;
    }
}