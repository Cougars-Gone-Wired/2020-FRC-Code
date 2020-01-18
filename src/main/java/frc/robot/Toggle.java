package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Toggle {

  private boolean state = false;

  private Joystick controller;
  private int buttonNumber;

  public Toggle(Joystick controller, int buttonNumber) {
    this.controller = controller;
    this.buttonNumber = buttonNumber;
    initialize();
  }

  public void initialize() {
    state = false;
  }

  public boolean toggle() {
    if (controller.getRawButton(buttonNumber)) {
      // Toggles the state here
      state = !state;
    }
    return state;
  }
}