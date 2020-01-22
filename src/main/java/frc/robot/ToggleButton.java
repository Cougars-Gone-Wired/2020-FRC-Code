package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ToggleButton {
    private boolean state;
    private Joystick controller;
    private int buttonNumber;

    public ToggleButton(Joystick controller, int buttonNumber) {
        state = true;
        this.controller = controller;
        this.buttonNumber = buttonNumber;
    }

    public boolean toggle() {
        if (controller.getRawButtonPressed(buttonNumber)) {
            this.state = !this.state;
        }
        return this.state;
    }

    public void setValue(boolean value) {
        this.state = value;
    }

    public boolean getValue() {
        return this.state;
    }
}