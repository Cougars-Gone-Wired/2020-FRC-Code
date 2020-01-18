package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ToggleButton {

  private boolean state = false;

  private Joystick controller;
  private int buttonNumber;

  public ToggleButton(Joystick controller, int buttonNumber) {
    this.controller = controller;
    this.buttonNumber = buttonNumber;
    initialize();
  }

  public void initialize() {
    state = false;
  }

  public boolean toggle() {
    if (controller.getRawButtonPressed(buttonNumber)) {
      // Toggles the state here
      state = !state;
    }
    return state;
  }

  // public boolean oldToggle() {
  // if (controller.getRawButton(buttonNumber)) {
  // if (!buttonState) {
  // output = !output;
  // }
  // buttonState = true;
  // } else {
  // buttonState = false;
  // }
  // return output;
  // }
}