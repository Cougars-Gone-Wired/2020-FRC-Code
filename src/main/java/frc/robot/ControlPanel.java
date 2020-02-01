package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

public class ControlPanel {
  private static final double MANUAL_PANEL_MOTOR_SPEED = .4;
  private static final double AUTOMATIC_PANEL_MOTOR_SPEED = 1.0;
  private static final int COLOR_LOG_LENGTH = 6;

  private WPI_TalonSRX panelMotor;

  private ColorSensorV3 m_colorSensor;
  private ColorMatch m_colorMatcher;
  private ArrayList<String> colorLog;

  public ControlPanel() {
    panelMotor = new WPI_TalonSRX(Constants.CONTROL_PANEL_MOTOR_ID);
    m_colorSensor = new ColorSensorV3(i2cPort);
    m_colorMatcher = new ColorMatch();
    colorLog = new ArrayList<String>(COLOR_LOG_LENGTH);

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }

  public void initialize() {
    panelMotor.setInverted(false);
    panelMotor.setNeutralMode(NeutralMode.Brake);
    panelMotor.configOpenloopRamp(0.0);
    setNotMoving();
  }

  private enum PanelStates {
    NOT_MOVING, MANUALLY_SPINNING, ROTATING, POSITIONING
  }

  private PanelStates panelState;

  private String targetColor;
  private int timesTargetSeen;

  public void spin(boolean manualSpinButton, boolean rotateToggle, boolean positionToggle) {
    switch (panelState) {
      case NOT_MOVING:
        if (manualSpinButton) {
          setManuallySpinning();
        } else if (rotateToggle) {
          setRotating();
        } else if (positionToggle) {
          setPostitioning();
        }
        break;

      case MANUALLY_SPINNING:
        if (!manualSpinButton) {
          setNotMoving();
        }
        break;

      case ROTATING:
        trackColor();
        if (changedColor && trueColor == targetColor) {
          timesTargetSeen++;
        }
        if (timesTargetSeen >= 7 || !rotateToggle) {
          setNotMoving();
        }
        break;

      case POSITIONING:
        trackColor();
        if (changedColor && trueColor == targetColor || !positionToggle) {
          panelMotor.setInverted(false);
          setNotMoving();
        }
        break;
    }
  }

  public void colorTrackInit() {
    colorLog.clear();
    matchColor();
    for (int i = 0; i <= COLOR_LOG_LENGTH; i++) {
      colorLog.add(currentColor);
    }
    trueColor = currentColor;
  }

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  
  private ColorMatchResult match;
  
  private final Color kRedTarget = ColorMatch.makeColor(0.643, 0.310, 0.048);
  private final Color kGreenTarget = ColorMatch.makeColor(0.271, 0.557, 0.173);
  private final Color kBlueTarget = ColorMatch.makeColor(0.241, 0.482, 0.278);
  private final Color kYellowTarget = ColorMatch.makeColor(0.439, 0.486, 0.075);

  private Color rawColor;
  private String currentColor;
  private String averagedColor;
  private String trueColor;
  private boolean changedColor;

  public void trackColor() {
    matchColor();

    colorLog.remove(0);
    colorLog.add(currentColor);

    if (colorLog.stream().distinct().count() <= 1) {
      averagedColor = currentColor;
    } else {
      averagedColor = "?";
    }

    switch (trueColor) {
      case "R":
        if (averagedColor == "G") {
          trueColor = "G";
          changedColor = true;
        } else if (averagedColor == "Y") {
          trueColor = "Y";
          changedColor = true;
        } else {
          changedColor = false;
        }
        break;
      case "G":
        if (averagedColor == "B") {
          trueColor = "B";
          changedColor = true;
        } else if (averagedColor == "R") {
          trueColor = "R";
          changedColor = true;
        } else {
          changedColor = false;
        }
        break;
      case "B":
        if (averagedColor == "Y") {
          trueColor = "Y";
          changedColor = true;
        } else if (averagedColor == "G") {
          trueColor = "G";
          changedColor = true;
        } else {
          changedColor = false;
        }
        break;
      case "Y":
        if (averagedColor == "R") {
          trueColor = "R";
          changedColor = true;
        } else if (averagedColor == "B") {
          trueColor = "B";
          changedColor = true;
        } else {
          changedColor = false;
        }
        break;
      default:
        if (averagedColor != "?") {
          trueColor = averagedColor;
          changedColor = true;
        } else {
          changedColor = false;
        }
        break;
    }
  }

  public void matchColor() {
    rawColor = m_colorSensor.getColor();
    match = m_colorMatcher.matchClosestColor(rawColor);
    
    if (match.color == kRedTarget) {
      currentColor = "R";
    } else if (match.color == kGreenTarget) {
      currentColor = "G";
    } else if (match.color == kBlueTarget) {
      currentColor = "B";
    } else if (match.color == kYellowTarget) {
      currentColor = "Y";
    } else {
      currentColor = "?";
    }
  }

  public void setNotMoving() {
    panelMotor.set(0.0);
    panelState = PanelStates.NOT_MOVING;
  }

  public void setManuallySpinning() {
    panelMotor.set(MANUAL_PANEL_MOTOR_SPEED);
    panelState = PanelStates.MANUALLY_SPINNING;
  }

  public void setRotating() {
    colorTrackInit();
    timesTargetSeen = 0;
    switch (trueColor) {
      case "R":
        targetColor = "G";
        break;
      case "G":
        targetColor = "B";
        break;
      case "B":
        targetColor = "Y";
        break;
      case "Y":
        targetColor = "R";
        break;
    }
    panelMotor.set(AUTOMATIC_PANEL_MOTOR_SPEED);
    panelState = PanelStates.ROTATING;
  }

  private String gameData;

  public void setPostitioning() {
    colorTrackInit();
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    switch (gameData.charAt(0)) {
      case 'R':
        targetColor = "B";
        if (trueColor == "Y" || trueColor == "B") {
          panelMotor.setInverted(true);
        }
        break;
        
      case 'G':
        targetColor = "Y";
        if (trueColor == "R" || trueColor == "Y") {
          panelMotor.setInverted(true);
        }
        break;

      case 'B':
        targetColor = "R";
        if (trueColor == "G" || trueColor == "R") {
          panelMotor.setInverted(true);
        }
        break;

      case 'Y':
        targetColor = "G";
        if (trueColor == "B" || trueColor == "G") {
          panelMotor.setInverted(true);
        }
        break;

      default:
        return;
    }
    panelMotor.set(AUTOMATIC_PANEL_MOTOR_SPEED);
    panelState = PanelStates.POSITIONING;
  }

  public void writeDash() {
    SmartDashboard.putString("Game Data", gameData);

    SmartDashboard.putNumber("Red", rawColor.red);
    SmartDashboard.putNumber("Green", rawColor.green);
    SmartDashboard.putNumber("Blue", rawColor.blue);

    SmartDashboard.putString("Current Color", currentColor);
    SmartDashboard.putString("Averaged Color", averagedColor);
    SmartDashboard.putString("True Color", trueColor);

    SmartDashboard.putString("State", panelState.toString());
  }
}