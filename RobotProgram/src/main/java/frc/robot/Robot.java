package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.DriveMotors.Axis;
import frc.robot.drive.*;

public class Robot extends TimedRobot {

  public static MecanumDrive mecanumDrive;
  public static Pneumatics pneumatics;
  public static BNO08x imu;

  Joystick controller;

  TeleopDrive teleop;
  Flywheel flywheel;
  

  @Override
  public void robotInit() {
    mecanumDrive = new MecanumBuilder()
      .addMotorRF(0)
      .addMotorLF(1)
      .addMotorRB(2)
      .addMotorLB(3)
      .build();

    //imu = new BNO08x();
    controller = new Joystick(0);
    teleop = new TeleopDrive();

    pneumatics = new Pneumatics();
    flywheel = new Flywheel(5);
  }

  @Override
  public void robotPeriodic() {
    
  }

  TimedAuto timedAuto;

  @Override
  public void autonomousInit() {
    //imu.init();

    timedAuto = new TimedAuto();
    timedAuto.start();
  }

  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
    //imu.init();
  }


  @Override
  public void teleopPeriodic() {
    //Vector3 rotation = imu.read();

    YourMa drive = teleop.drive(controller);
    YourMa steering = teleop.rotate(controller, imu.read().y);

    YourMa finalMove = YourMa.BlendBetween(drive, steering, 0.5);

    //finalMove.drive(driveMotors, controller.getRawAxis(3));

    if (controller.getRawButtonPressed(1)) {
      pneumatics.toggleSolenoids();
    }


    if (controller.getRawButton(2))
      flywheel.setRPM(5800);
    else
      flywheel.setRPM(0);
  }

  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {}

}
