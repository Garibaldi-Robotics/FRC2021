package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class TeleopDrive {

    
    
    public YourMa drive(Joystick controller) {

        double x = controller.getRawAxis(0);
        double y = controller.getRawAxis(1);

        float deadzone = 0.1f;
        x = deadzone(x, deadzone);
        y = deadzone(y, deadzone);
        
        //if (x == 0 && y == 0)
        //    return new YourMa(YourMa.STOP);
        

        double angle = Math.atan2(y, x) * (180 / Math.PI) + 90;
        if (angle < 0)
            angle += 360;

        YourMa move = new YourMa();

        /* Angles cheat sheet
         *  315  0  45
         *     \ | /
         * 270 - + - 90
         *     / | \
         *  225 180 135
        */

        angle = controller.getPOV(0);

        if (angle >= 337.5 || angle < 22.5)
            move = YourMa.FORWARD;
        if (angle >= 22.5 && angle < 67.5)
            move = YourMa.FORWARD_RIGHT;
        if (angle >= 67.5 && angle < 112.5)
            move = YourMa.RIGHT;
        if (angle >= 112.5 && angle < 157.5)
            move = YourMa.BACKWARD_RIGHT;
        if (angle >= 157.5 && angle < 202.5)
            move = YourMa.BACKWARD;
        if (angle >= 202.5 && angle < 247.5)
            move = YourMa.BACKWARD_LEFT;
        if (angle >= 247.5 && angle < 292.5)
            move = YourMa.LEFT;
        if (angle >= 292.5 && angle < 337.5)
            move = YourMa.FORWARD_LEFT;



        return move;
    }

    public YourMa rotate(Joystick controller) {
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

        double angleCurrent = Robot.imu.read().y;

        double angleToMove = angleTarget - angleCurrent;
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