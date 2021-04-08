package frc.robot.auto;

import frc.robot.BNO08x;
import frc.robot.drive.MecanumDrive;

public class AutoRotate implements AutoInstruction {

    private boolean started = false;

    private MecanumDrive drive;
    private BNO08x imu;

    private double targetAngle;
    private double currentAngle;
    private double zeroAngle;

    public AutoRotate(MecanumDrive drive, BNO08x imu, double targetAngle) {
        this.targetAngle = targetAngle;

        this.drive = drive;
        this.imu = imu;
    }

    @Override
    public boolean execute() {

        if (!started) {
            zeroAngle = imu.read().y;

            started = true;
        }

        currentAngle = imu.read().y - zeroAngle;

        double dist = targetAngle - currentAngle;

        if (dist > 0) {
            drive.Drive(dist / -10, dist / 10, dist / -10, dist / 10);
            return false;
        } else
        if (dist < 0) {
            drive.Drive(dist / 10, dist / -10, dist / 10, dist / -10);
            return false;
        } else {
            return true;
        }
    }
    
    
}
