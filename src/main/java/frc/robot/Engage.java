package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Engage {
    private Solenoid engageSolenoid;
    private Solenoid hardStopSolenoid;
    private boolean engageBool;

    public Engage() {
        engageSolenoid = new Solenoid(Constants.ENGAGE_SOLENOID_PORT);
        hardStopSolenoid = new Solenoid(Constants.HARD_STOP_SOLENOID_PORT);
        initialize();
    }

    public void initialize() {
        setEngaged();
    }

    public enum EngageStates {
        ENGAGED, DISENGAGED
    }

    private EngageStates currentEngageState;

    public void engageShoot(double enageAxis) {
        engageBool = (enageAxis >= Constants.DEADZONE);

        switch(currentEngageState) {
            case DISENGAGED:
                if (!engageBool) {
                    setEngaged();
                }
                break;

            case ENGAGED:
                if (engageBool) {
                    setDisengaged();
                }
                break;
        }
    }

    public EngageStates getCurrentEngageState() {
        return currentEngageState;
    }

    public void setEngaged() {
        engageSolenoid.set(true);
        hardStopSolenoid.set(false);
        currentEngageState = EngageStates.ENGAGED;
    }

    public void setDisengaged() {
        engageSolenoid.set(false);
        hardStopSolenoid.set(true);
        currentEngageState = EngageStates.DISENGAGED;
    }
}