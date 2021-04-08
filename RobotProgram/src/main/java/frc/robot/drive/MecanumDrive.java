package frc.robot.drive;

import frc.robot.Vector2;

public class MecanumDrive {
    private static double WHEELCIRCUMFERENCE = Math.PI * 7.62 * 7.62;

    private NeoMotor[][] motors;

    protected MecanumDrive(NeoMotor[][] motors) {
        this.motors = motors;
    }

    public void Drive(Vector2 vector) {

        

        /*

        YourMa motorctrl = new YourMa();

        if (vector.y > 0 && vector.x > 0)
            motorctrl = YourMa.BlendBetween(YourMa.FORWARD, YourMa.RIGHT, vector.y);
        if (vector.y > 0 && vector.x < 0)
            motorctrl = YourMa.BlendBetween(YourMa.FORWARD, YourMa.LEFT, vector.y);
        if (vector.y < 0 && vector.x > 0)
            motorctrl = YourMa.BlendBetween(YourMa.BACKWARD, YourMa.RIGHT, vector.y);
        if (vector.y < 0 && vector.x < 0)
            motorctrl = YourMa.BlendBetween(YourMa.BACKWARD, YourMa.LEFT, vector.y);

        Drive(motorctrl.frontRight, motorctrl.frontLeft, motorctrl.backRight, motorctrl.backLeft);

        */
    }

    public void Drive(double rightFront, double leftFront, double rightBack, double leftBack) {
        for (NeoMotor motor : motors[0]) motor.set(rightFront);
        for (NeoMotor motor : motors[1]) motor.set(leftFront);
        for (NeoMotor motor : motors[2]) motor.set(rightBack);
        for (NeoMotor motor : motors[3]) motor.set(leftBack);
    }

    public void Stop() {
        for (NeoMotor[] neoMotors : motors)
            for (NeoMotor neoMotor : neoMotors)
                neoMotor.set(0);
    }

    public void Disable() {
        for (NeoMotor[] neoMotors : motors)
            for (NeoMotor neoMotor : neoMotors)
                neoMotor.disable();
    }

    public double getPosition()  {
        double sum = 0;
        int totalCount = 0;

        for (NeoMotor[] neoMotors : motors)
            for (NeoMotor neoMotor : neoMotors)  {
                sum += neoMotor.getRotations();
                totalCount++;
            }

        return (sum / totalCount) * WHEELCIRCUMFERENCE;
    }
}
