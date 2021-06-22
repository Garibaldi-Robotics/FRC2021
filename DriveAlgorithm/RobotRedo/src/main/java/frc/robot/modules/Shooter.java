package frc.robot.modules;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.lib.TimedAuto;

public class Shooter {
    public static final double MAXRPM = 5800.0;
    private CANSparkMax motor;
    private CANEncoder encoder;

    private Pneumatics pneumatics;
    private TimedAuto pneumaticTimer;

    public Shooter() {
        motor = new CANSparkMax(6, MotorType.kBrushless);
        motor.setInverted(true);
        encoder = motor.getEncoder();

        pneumatics = new Pneumatics();
    }

    public boolean setRPM(double rpm) {
        double currentRPM = getRPM();

        if (currentRPM < rpm && rpm != 0) {
            motor.set(1);
        } else
        if (currentRPM > rpm) {
            motor.set(rpm / currentRPM);
        }

        return currentRPM == rpm;
    }

    public double getRPM() {
        assert encoder != null;
        return encoder.getVelocity();
    }


    public void Loop() {
        if (pneumaticTimer != null) {
            if (pneumaticTimer.isTime(0))
                setRPM(MAXRPM);
            if (pneumaticTimer.isTime(0.5))
                pneumatics.toggleSolenoids();
            if (pneumaticTimer.isTime(1))
                pneumatics.toggleSolenoids();
            if (pneumaticTimer.isTime(1.5)) {
                setRPM(0);
                pneumaticTimer = null;
            }
        } else {
            setRPM(0);
        }
    }

    public void Shoot() {
        if (pneumaticTimer == null) {
            pneumaticTimer = new TimedAuto();
            pneumaticTimer.start();
        }
    }

    
}
