package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auto.AutoDrive;
import frc.robot.auto.AutoInstruction;
import frc.robot.auto.AutoRotate;
import frc.robot.auto.AutoSequence;
import frc.robot.drive.*;
import frc.robot.shooter.Revolver;

public class Robot extends TimedRobot {

  MecanumDrive mecanumDrive;
  BNO08x imu;
  Revolver revolver;

  Joystick controller;

  @Override
  public void robotInit() {
    mecanumDrive = new MecanumBuilder()
      .addMotorRF(1)
      .addMotorLF(2)
      .addMotorRB(3)
      .addMotorLB(4)
      .build();

    //imu = new BNO08x();
    controller = new Joystick(0);
    revolver = new Revolver(13, 5);

    imu = new BNO08x();
  }

  @Override
  public void robotPeriodic() {
    
  }

  AutoSequence sequence = new AutoSequence();

  @Override
  public void autonomousInit() {
    imu.init();

    sequence.addAll(new AutoInstruction[] {
      new AutoDrive(mecanumDrive, 10),
      new AutoRotate(mecanumDrive, imu, 90),
    });
  }

  @Override
  public void autonomousPeriodic() {
    sequence.Execute();
  }

  @Override
  public void teleopInit() {
    imu.init();
  }


  @Override
  public void teleopPeriodic() {
    revolver.Loop();


    //mecanumDrive.Drive(new Vector2(controller.getX() * controller.getRawAxis(3), controller.getY() * controller.getRawAxis(3)));
    
    YourMa motorctrl = TeleopDrive.drive(controller);
    double speed = controller.getRawAxis(3);

    
    mecanumDrive.Drive(motorctrl.frontRight * speed, motorctrl.frontLeft * speed, motorctrl.backRight * speed, motorctrl.backLeft * speed);

    if (controller.getRawButton(5))
      revolver.Rev();
    else
      revolver.Slow();

    if (controller.getRawButtonPressed(2)) {
      revolver.Shoot();
    }
    if (controller.getRawButtonPressed(3)) {
      //revolver.ToggleSolenoid();
    }


    
  }

  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {}

}
