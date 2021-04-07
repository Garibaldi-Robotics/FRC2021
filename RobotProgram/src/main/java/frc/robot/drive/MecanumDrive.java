package frc.robot.drive;

import frc.robot.Vector2;

public class MecanumDrive {
    private NeoMotor[][] motors;

    protected MecanumDrive(NeoMotor[][] motors) {
        this.motors = motors;
    }

    public void Drive(Vector2 speed) {

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
                sum += neoMotor.getPosition();
                totalCount++;
            }

        return sum / totalCount;
    }
}
