package frc.robot.shooter;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.TimedAuto;

public class Revolver {

    private TalonSRX motor;
    private Encoder encoder;

    private Pneumatics pneumatics;
    private Flywheel flywheel;

    private double chamberPosition;
    private double chamberTarget;

    private double flywheelTarget;

    private TimedAuto shootTimer;

    public Revolver(int revolverAddress, int flywheelAddress) {
        motor = new TalonSRX(revolverAddress);
        pneumatics = new Pneumatics();
        flywheel = new Flywheel(flywheelAddress);
        encoder = new Encoder(8, 9, false, EncodingType.k4X);

        encoder.setDistancePerPulse(1024);

        assert motor != null && pneumatics != null && flywheel != null;
    }

    public void Loop() {
        

        //System.out.println(chamberPosition);

        chamberPosition = encoder.get();

        flywheel.setRPM(flywheelTarget);


        if (shootTimer != null) {
            motor.set(TalonSRXControlMode.PercentOutput, 0);

            if (shootTimer.isTime(0))
                ToggleSolenoid();
            if (shootTimer.isTime(0.5))
                ToggleSolenoid();
            if (shootTimer.isTime(1)) {
                Slow();
                shootTimer = null;
            }
        } else {
            motor.set(TalonSRXControlMode.PercentOutput, 0.1);
        }
        
    }

    public void Shoot() {
        if (shootTimer == null) {
            System.out.println("Shoot");
            shootTimer = new TimedAuto();
            shootTimer.start();
        }
    }

    public void Rev() {
        flywheelTarget = Flywheel.MAXRPM;
    }

    public void Slow() {
        flywheelTarget = 0;
    }

    public void RotateChamber() {
        chamberTarget += 90;
    }

    public void ToggleSolenoid() {
        pneumatics.toggleSolenoids();
    }

    public boolean isTargetSpeed() {
        return flywheel.getRPM() + 100 < flywheelTarget && flywheel.getRPM() - 100 > flywheelTarget;
    }
}
