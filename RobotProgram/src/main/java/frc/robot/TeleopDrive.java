package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class TeleopDrive {

    
    
    public YourMa drive(Joystick controller) {

        double x = controller.getRawAxis(0);
        double y = controller.getRawAxis(1);

        float deadzone = 0.1f;
        x = deadzone(x, deadzone);
        y = deadzone(y, deadzone);
        
        if (x == 0 && y == 0)
            return new YourMa(YourMa.STOP);
        

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


        //TODO: test this

        if (angle >= 0 && angle < 90)
            move = YourMa.BlendBetween(YourMa.FORWARD, YourMa.RIGHT, Math.abs(x));
        if (angle >= 90 && angle < 180)
            move = YourMa.BlendBetween(YouMa.RIGHT, YourMa.BACKWARD, Math.abs(x));
        if (angle >= 180 && angle < 270)
            move = YourMa.BlandBetween(YourMa.BACKWARD, YourMa.LEFT, Math.abs(x));
        if (angle >= 270 && angle < 360)
            move = YourMa.BlendBetween(YourMa.LEFT, YourMa.FORWARD, Math.abs(x));


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