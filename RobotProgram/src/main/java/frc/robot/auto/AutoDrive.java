package frc.robot.auto;

import frc.robot.drive.MecanumDrive;

public class AutoDrive implements AutoInstruction {

    private boolean started = false;

    private MecanumDrive drive;

    private double targetDistance;
    private double currentDistance;
    private double zeroDistance;

    public AutoDrive(MecanumDrive drive, double distance) {
        this.drive = drive;

        this.targetDistance = distance;
    }

    @Override
    public boolean execute() {
        if (!started) {
            zeroDistance = drive.getPosition();

            started = true;
        }

        currentDistance = drive.getPosition() - zeroDistance;

        double dist = targetDistance - currentDistance;

        if (dist > 0 || dist < 0)  {
            drive.Drive(dist / 10, dist / 10, dist / 10, dist / 10);
            return false;
        } else {
            zeroDistance += currentDistance;
            return true;
        }
    }
    
}
