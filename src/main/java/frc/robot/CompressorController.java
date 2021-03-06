package frc.robot;

import edu.wpi.first.wpilibj.Compressor;

// purpose of class is to stop the compressor while shooting or climbing so that that compressor doesn't suck up battery and affect PID or motor power
public class CompressorController {

    private Compressor c;

    private enum CompressorStates {
        ENABLED, DISABLED
    }

    private CompressorStates currentState;

    public CompressorController() {
        c = new Compressor();
        initialize();
    }

    public void initialize() {
        setEnabled();
    }

    public void controlCompressor() {

        switch (currentState) {
            case ENABLED:
                if (Robot.shooter.isShooting() || !Robot.climber.isNotMoving()) {
                    setDisabled();
                }
                break;

            case DISABLED:

                if (Robot.shooter.isNotMoving() && Robot.climber.isNotMoving()) {
                    setEnabled();
                }
                break;
        }
    }

    public void setDisabled() {
        currentState = CompressorStates.DISABLED;
        c.stop();
    }

    public void setEnabled() {
        currentState = CompressorStates.ENABLED;
        c.start();
    }
}