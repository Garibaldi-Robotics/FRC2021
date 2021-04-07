package frc.robot.drive;

import edu.wpi.first.wpilibj.Joystick;

import frc.robot.YourMa;

public class TeleopDrive {

    
    
    public YourMa drive(Joystick controller) {

        /* Angles cheat sheet
         *  315  0  45
         *     \ | /
         * 270 - + - 90
         *     / | \
         *  225 180 135
        */

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

    public YourMa rotate(Joystick controller, double yRot) {
        double x = controller.getRawAxis(4);
        double y = controller.getRawAxis(5);

        float deadzone = 0.1f;
        x = deadzone(x, deadzone);
        y = deadzone(y, deadzone);
        
        if (x == 0 && y == 0)
            return new YourMa(YourMa.STOP);
        

        double angleTarget = Math.atan2(y, x) * (180 / Math.PI) + 90;
        if (angleTarget < 0)
            angleTarget += 360;

        double angleToMove = angleTarget - yRot;
        angleToMove = deadzone(angleToMove, 10);

        YourMa move = new YourMa();

        if (angleToMove > 0) {
            move = YourMa.TURNRIGHT;
        } else if (angleToMove < 0) {
            move = YourMa.TURNLEFT;
        } else {
            move = YourMa.STOP;
        }
        
        return move;

    }


    

    private double deadzone(double input, float deadzone) {
        if (input > deadzone)
            input -= deadzone;
        else if (input > 0 && input <= deadzone)
            input = 0;
        else if (input < -deadzone)
        input += deadzone;
        else if (input >= -deadzone && input < 0)
            input = 0;

        return input;
    }
    
}