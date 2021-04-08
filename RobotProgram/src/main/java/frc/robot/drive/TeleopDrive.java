package frc.robot.drive;



import edu.wpi.first.wpilibj.Joystick;

import frc.robot.drive.YourMa;

public class TeleopDrive {

    
    
    public static YourMa drive(Joystick controller) {

        // Angles cheat sheet
        //  315  0  45
        //     \ | /
        // 270 - + - 90
        //     / | \
        //  225 180 135

        switch (controller.getPOV(0)) {
            case 0:
                return YourMa.FORWARD;
            case 45:
                return YourMa.FORWARD_RIGHT;
            case 90:
                return YourMa.RIGHT;
            case 135:
                return YourMa.BACKWARD_RIGHT;
            case 180:
                return YourMa.BACKWARD;
            case 225:
                return YourMa.BACKWARD_LEFT;
            case 270:
                return YourMa.LEFT;
            case 315:
                return YourMa.FORWARD_LEFT;
        }
        
        return YourMa.STOP;
    }
}