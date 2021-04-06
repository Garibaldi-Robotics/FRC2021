import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;

public class Flywheel {
    

    private CANSparkMax motor;
    private CANEncoder encoder;

    public Flywheel(int CANID) {
        motor = new CANSparkMax(CANID, MotorType.kBrushless);
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
    }

    public double getRPM() {
        assert encoder != null;
        return encoder.getVelocity();
    }


}
