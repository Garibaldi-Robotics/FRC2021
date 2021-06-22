package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

import frc.robot.drive.*;
import frc.robot.modules.*;
import frc.robot.lib.*;

public class Robot extends TimedRobot {

    DriveTrain driveTrain = new DriveTrain();
    BNO08x imu = new BNO08x();
    Intake intake = new Intake();
    Shooter shooter = new Shooter();

    Joystick controller = new Joystick(0);

    @Override
    public void autonomousInit() {
        imu.init();
    }

    @Override
    public void autonomousPeriodic() {
        
    }


    @Override
    public void teleopInit() {
        imu.init();
    }

    @Override
    public void teleopPeriodic() {
        Vector3 rotation = imu.read();
        shooter.Loop();

        if (controller.getRawButtonPressed(0))
            shooter.Shoot();

        if (controller.getRawButtonPressed(1))
            intake.Start();
        if (controller.getRawButtonReleased(1))
            intake.Stop();

        System.out.println(rotation.y);
    }
}
