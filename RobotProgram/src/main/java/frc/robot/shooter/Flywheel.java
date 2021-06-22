package frc.robot.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

public class Flywheel {
    public static final double MAXRPM = 5800.0;
    

    private CANSparkMax motor;
    private CANEncoder encoder;

    public Flywheel(int CANID) {
        motor = new CANSparkMax(CANID, MotorType.kBrushless);
        motor.setInverted(true);
        encoder = motor.getEncoder();
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


}
