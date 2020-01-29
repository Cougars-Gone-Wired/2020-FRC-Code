package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Feeder.FeederStates;

public class Chomper {
    private Solenoid chompSolenoid;
    private Solenoid hardStopSolenoid;

    public Chomper() {
        chompSolenoid = new Solenoid(Constants.CHOMPER_SOLENOID_PORT);
        hardStopSolenoid = new Solenoid(Constants.HARD_STOP_SOLENOID_PORT);
        initialize();
    }

    public void initialize() {
        setIdleConfig();
    }

    public enum ChompStates {
        INTAKE_CONFIG, IDLE_CONFIG, SHOOTER_CONFIG
    }

    private ChompStates currentChompState;

    public void controlChomp() {

        switch(currentChompState) {
            case INTAKE_CONFIG:
                if (Robot.feeder.getCurrentFeederState() == FeederStates.NOT_MOVING) {
                    setIdleConfig();
                }
                break;

            case IDLE_CONFIG:
                if (Robot.feeder.getCurrentFeederState() == FeederStates.INTAKING) {
                    setIntakeConfig();
                } else if (Robot.feeder.getCurrentFeederState() == FeederStates.FEEDING_SHOOTER) {
                    setShooterConfig();
                }
                break;

            case SHOOTER_CONFIG:
                if (Robot.feeder.getCurrentFeederState() == FeederStates.NOT_MOVING) {
                    setIdleConfig();
                }
            break;
        }
    }

    public ChompStates getCurrentChompState() {
        return currentChompState;
    }

    public void setIntakeConfig() {
        chompSolenoid.set(true);
        hardStopSolenoid.set(true);
        currentChompState = ChompStates.INTAKE_CONFIG;
    }

    public void setIdleConfig() {
        chompSolenoid.set(false);
        hardStopSolenoid.set(true);
        currentChompState = ChompStates.IDLE_CONFIG;
    }

    public void setShooterConfig() {
        chompSolenoid.set(false);
        hardStopSolenoid.set(false);
        currentChompState = ChompStates.SHOOTER_CONFIG;
    }
}