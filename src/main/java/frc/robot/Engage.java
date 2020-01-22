package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Engage {
    private Solenoid engageSolenoid;
    private Solenoid hardStopSolenoid;
    private boolean engageBool;

    public Engage() {
        engageSolenoid = new Solenoid(Constants.ENGAGE_SOLENOID_ID);
        hardStopSolenoid = new Solenoid(Constants.HARD_STOP_SOLENOID_ID);
        initialize();
    }

    public void initialize() {
        engageSolenoid.set(false);
        hardStopSolenoid.set(false);
        currentEngageState = EngageStates.ENGAGED;
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
                    engageSolenoid.set(true);
                    hardStopSolenoid.set(false);
                    currentEngageState = EngageStates.ENGAGED;
                }
                break;

            case ENGAGED:
                if (engageBool) {
                    engageSolenoid.set(false);
                    hardStopSolenoid.set(true);
                    currentEngageState = EngageStates.DISENGAGED;
                }
                break;
        }
    }
}