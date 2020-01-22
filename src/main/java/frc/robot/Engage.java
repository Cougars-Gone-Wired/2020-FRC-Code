package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Engage {
    private Solenoid engageSolenoid;
    private Solenoid hardStopSolenoid;
    private boolean shooterTriggerBool;

    public Engage() {
        engageSolenoid = new Solenoid(Constants.ENGAGE_SOLENOID_ID);
        hardStopSolenoid = new Solenoid(Constants.HARD_STOP_SOLENOID_ID);
        initialize();
    }

    public void initialize() {
        engageSolenoid.set(false);
        hardStopSolenoid.set(false);
        currentEngageState = EngageStates.DISENGAGED;
    }

    public enum EngageStates {
        ENGAGED, DISENGAGED
    }

    private EngageStates currentEngageState;

    public void engageShoot(double shooterTrigger) {

        shooterTriggerBool = (shooterTrigger >= Constants.DEADZONE);

        switch(currentEngageState) {
            case ENGAGED:
                if (shooterTriggerBool) {
                    engageSolenoid.set(true);
                    hardStopSolenoid.set(true);
                    currentEngageState = EngageStates.DISENGAGED;
                }
                break;

            case DISENGAGED:
                if (!shooterTriggerBool) {
                    engageSolenoid.set(false);
                    hardStopSolenoid.set(false);
                    currentEngageState = EngageStates.ENGAGED;
                }
        }
    }
}