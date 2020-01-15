package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;


public class ColorWheel {
 
    private static final double COLORWHEEL_SPEED = .4;
    private WPI_TalonSRX wheelMotor;
    private States currentState;
    private String gameData;

    public ColorWheel() {
        wheelMotor = new WPI_TalonSRX(0);
        currentState = States.NOT_MOVING;
        // gameData = DriverStation.getInstance().getGameSpecificMessage();
    }

    public void initialize() {
        wheelMotor.set(0);
        currentState = States.NOT_MOVING;
        gameData = DriverStation.getInstance().getGameSpecificMessage();

    }

    public enum States {
        NOT_MOVING, SPINNING
    }

    public void color() {
        switch (gameData.charAt(0))
          {
            case 'B' :
              //Blue case code
                while(){ //readcolor != "B"
                    //Spin Motor
                }
              break;
            case 'G' :
              //Green case code
              break;
            case 'R' :
              //Red case code
              break;
            case 'Y' :
              //Yellow case code
              break;
            default :
              //This is corrupt data
              break;
          }
    }
    
    public void spinManual(boolean spinButton) {
        switch(currentState) {
            case NOT_MOVING:
                if (spinButton) {
                    feederMotor.set(COLORWHEEL_SPEED);
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
