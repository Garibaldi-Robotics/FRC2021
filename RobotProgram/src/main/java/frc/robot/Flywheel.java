package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

public class Flywheel {
    

    private CANSparkMax motor;
    private CANEncoder encoder;

    public Flywheel(int CANID) {
        motor = new CANSparkMax(CANID, MotorType.kBrushless);
        motor.setInverted(true);
        encoder = motor.getEncoder();
    }

    public boolean setRPM(double rpm) {
        double currentRPM = getRPM();

        if (currentRPM < rpm) {
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


}
