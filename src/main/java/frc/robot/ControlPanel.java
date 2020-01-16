package frc.robot;

// import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
public class ControlPanel {  

    //BIG NOTE                                                  |   OTHER REFERENCE DATA
    // Blue = Red                                               |   Wheels ratio: ??:??
    // Red = Blue                                               |   hi
    // Green = Yellow                                           |
    // Yellow = Green                                           |
    // Yes, we live in the Phantom Tollbooth. Get used to it.   |

    private static final double COLORWHEEL_SPEED = .4;
    private WPI_TalonSRX wheelMotor;
    private States currentState;
    private String gameData;
    private Boolean gameDataError;
    private String colorString;

    private Color kBlueTarget;
    private Color kGreenTarget;
    private Color kRedTarget;
    private Color kYellowTarget;
    private ColorMatch m_colorMatcher;
    private ColorSensorV3 m_colorSensor;
    private Color colorRead;
    private Color detectedColor;
    ColorMatchResult match;

    public ControlPanel() { // Constructor 
        wheelMotor = new WPI_TalonSRX(0);
        currentState = States.NOT_MOVING;
        // gameData = DriverStation.getInstance().getGameSpecificMessage();

        final I2C.Port i2cPort = I2C.Port.kOnboard;
        ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
        ColorMatch m_colorMatcher = new ColorMatch();

        Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429); // #246d6d
        Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240); // #328f3d
        Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114); // #8f3b1d
        Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); // #5c861d
    }

    public void initialize() {
        wheelMotor.set(0);
        currentState = States.NOT_MOVING;
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);

        colorRead = m_colorSensor.getColor();
    }

    public enum States {
        NOT_MOVING, SPINNING
    }

    public void color() {
        colorRead = m_colorSensor.getColor();
        
        // ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
        
        // if (match.color == kBlueTarget) {
        //   colorString = "Blue";
        // } else if (match.color == kRedTarget) {
        //   colorString = "Red";
        // } else if (match.color == kGreenTarget) {
        //   colorString = "Green";
        // } else if (match.color == kYellowTarget) {
        //   colorString = "Yellow";
        // } else {
        //   colorString = "Unknown";
        // }
        switch (gameData.charAt(0))
        {
            case 'B' :
              //Blue case code
                if ( colorString != "Red" ) {

                }
              break;
            case 'G' :
              //Green case code
              if ( colorString != "Yellow" ) {

              }
              break;
            case 'R' :
              //Red case code
              if ( colorString != "Blue" ) {

              }
              break;
            case 'Y' :
              //Yellow case code
              if ( colorString != "Green" ) {

              }
              break;
            default :
              //This is corrupt data
              gameDataError = true;
              SmartDashboard.putBoolean("Game Data Status", gameDataError)
              break;
        }
    }
    
    public void spinManual(boolean spinButton) {
        switch(currentState) {
            case NOT_MOVING:
                if (spinButton) {
                    wheelMotor.set(COLORWHEEL_SPEED);
                    currentState = States.SPINNING;
                }
                break;
                
            case SPINNING:
                if (!spinButton) {
                    wheelMotor.set(0);
                    currentState = States.NOT_MOVING;
                }
                break;
        }
    }

    public void writeDash() {
      SmartDashboard.putString("Game Data", gameData);
      match = m_colorMatcher.matchClosestColor(detectedColor);
      detectedColor = m_colorSensor.getColor();

      SmartDashboard.putNumber("Proximity", m_colorSensor.getProximity());
      SmartDashboard.putNumber("Red", m_colorSensor.getRed());
      SmartDashboard.putNumber("Green", m_colorSensor.getGreen());
      SmartDashboard.putNumber("Blue", m_colorSensor.getBlue());
      SmartDashboard.putNumber("Confidence", match.confidence);
      SmartDashboard.putString("Detected Color", colorString);
    }

}

// Code for Robot.java
// gameData = DriverStation.getInstance().getGameSpecificMessage();
// if(gameData.length() > 0) {
//     <wheel>.color();
// }

// Template code for color get
// import edu.wpi.first.wpilibj.DriverStation;

// String gameData;
// gameData = DriverStation.getInstance().getGameSpecificMessage();
// if(gameData.length() > 0)
// {
//   switch (gameData.charAt(0))
//   {
//     case 'B' :
//       //Blue case code
//       break;
//     case 'G' :
//       //Green case code
//       break;
//     case 'R' :
//       //Red case code
//       break;
//     case 'Y' :
//       //Yellow case code
//       break;
//     default :
//       //This is corrupt data
//       break;
//   }
// } else {
//   //Code for no data received yet
// }