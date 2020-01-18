package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Arm {

  private Solenoid solenoid1;
  private Solenoid solenoid2;

  public Arm() {
    solenoid1 = new Solenoid(0);
    solenoid2 = new Solenoid(1);

  }

  public void initialize() {
    solenoid1.set(false);
    solenoid2.set(true);
    currentPistonState = PistonStates.STARTING_POSITION;
  }

  private enum PistonStates {
    CLIMBING_POSITION, STARTING_POSITION, SHOOTING_POSITION
  }

  private PistonStates currentPistonState;

  // BIG NOTE: climerPositionButton = Y button
  // startConfigButton = B button
  // shooterPositionButton = A button

  public void pistonArm(ToggleButton shooterPosButton, ToggleButton startPosButton, ToggleButton climberPosButton) {
    // CARTER: Set the ToggleButtons in the different cases with
    // <button>.setValue(true/false);
    switch (currentPistonState) {
    case STARTING_POSITION:
      if (climberPosButton.getValue()) {
        solenoid1.set(true);
        solenoid2.set(true);
        currentPistonState = PistonStates.CLIMBING_POSITION;
      } else if (shooterPosButton.getValue()) {
        solenoid1.set(false);
        solenoid2.set(false);
        currentPistonState = PistonStates.SHOOTING_POSITION;
      }
      break;
    case CLIMBING_POSITION:
      if (startPosButton.getValue()) {
        solenoid1.set(false);
        solenoid2.set(true);
        currentPistonState = PistonStates.STARTING_POSITION;
      } else if (shooterPosButton.getValue()) {
        solenoid1.set(false);
        solenoid2.set(false);
        currentPistonState = PistonStates.SHOOTING_POSITION;
      }
      break;
    case SHOOTING_POSITION:
      if (climberPosButton.getValue()) {
        solenoid1.set(true);
        solenoid2.set(true);
        currentPistonState = PistonStates.CLIMBING_POSITION;
      } else if (startPosButton.getValue()) {
        solenoid1.set(false);
        solenoid2.set(true);
        currentPistonState = PistonStates.STARTING_POSITION;
      }
      break;
    }
  }

}