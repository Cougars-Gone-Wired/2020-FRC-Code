package frc.robot;

import edu.wpi.first.wpilibj.Compressor;

public class CompressorController {

    private Compressor c;

    private enum CompressorStates {
        ENABLED, DISABLED
    }

    private CompressorStates currentState;

    public CompressorController() {
        Compressor c = new Compressor();
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