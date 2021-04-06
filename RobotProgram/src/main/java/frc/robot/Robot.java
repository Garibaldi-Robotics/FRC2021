package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  public static DriveMotors driveMotors;
  public static Pneumatics pneumatics;
  public static BNO08x imu = new BNO08x();

  Joystick controller = new Joystick(0);

  // cause static functions are for suckers
  TeleopDrive teleop = new TeleopDrive();
  

  @Override
  public void robotInit() {
    driveMotors = new DriveMotors(new int[]{1, 2, 3, 4}, new int[]{1, -1, 1, -1});
    pneumatics = new Pneumatics();
  }

  @Override
  public void robotPeriodic() {
    
  }

  TimedAuto timedAuto;

  @Override
  public void autonomousInit() {
    imu.init();

    timedAuto = new TimedAuto();
    timedAuto.start();
  }

  @Override
  public void autonomousPeriodic() {
    if (timedAuto.isTime(0)) {
      driveMotors.drive(new double[] {0.1, 0, 0, 0});
    }

    if (timedAuto.isTime(1)) {
      driveMotors.drive(new double[] {0, 0.1, 0, 0});
    }

    if (timedAuto.isTime(2)) {
      driveMotors.drive(new double[] {0, 0, 0.1, 0});
    }

    if (timedAuto.isTime(3)) {
      driveMotors.drive(new double[] {0, 0, 0, 0.1});
    }

    if (timedAuto.isTime(4.5)) {
      driveMotors.stop();
    }

    

  }

  @Override
  public void teleopInit() {
    imu.init();
  }

  @Override
  public void teleopPeriodic() {
    //Vector3 rotation = imu.read();

    YourMa drive = teleop.drive(controller);
    YourMa steering = teleop.rotate(controller);

    YourMa finalMove = YourMa.BlendBetween(drive, steering, 0.5);

    drive.drive(driveMotors, controller.getRawAxis(3));

    if (controller.getRawButtonPressed(1)) {
      pneumatics.toggleSolenoids();
    }
    
  }

  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {}

}
