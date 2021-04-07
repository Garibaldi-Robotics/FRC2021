package frc.robot.drive;

import frc.robot.BNO08x;
import frc.robot.Vector3;

public class AutoDrive {
    private MecanumDrive drive;
    private BNO08x imu;

    private Vector3 rotation;
    private double distance;
    private double distanceZero;

    public AutoDrive(MecanumDrive drive, BNO08x imu) {
        this.drive = drive;
        this.imu = imu;

        assert imu != null && drive != null && imu.isConnected();
    }

    public boolean SetRotation(double targetAngle) {
        rotation = imu.read();

        double dist = targetAngle - rotation.y;

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

    public boolean DriveDistance(double targetDistance) {
        distance = drive.getPosition() - distanceZero;

        double dist = targetDistance - distance;

        if (dist > 0 || dist < 0)  {
            drive.Drive(dist / 10, dist / 10, dist / 10, dist / 10);
            return false;
        } else {
            distanceZero += distance;
            return true;
        }
    }
    
}
