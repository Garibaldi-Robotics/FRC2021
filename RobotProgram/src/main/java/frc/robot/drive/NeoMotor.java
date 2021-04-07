package frc.robot.drive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class NeoMotor {
    public static double MAXRPM = 5800.0;

    private int address;
    private CANSparkMax sparkMax;
    private CANEncoder encoder;

    public NeoMotor(int address) {
        this.address = address;

        sparkMax = new CANSparkMax(address, MotorType.kBrushless);

        assert sparkMax != null;

        encoder = sparkMax.getEncoder();
    }

    public NeoMotor(int address, boolean isInverted) {
        this.address = address;

        sparkMax = new CANSparkMax(address, MotorType.kBrushless);

        assert sparkMax != null;

        encoder = sparkMax.getEncoder();
        
        sparkMax.setInverted(isInverted);
    }

    public void set(double speed) {

        // Brakes, because they removed it for some reason
        if (speed == 0) {
            double velocity = encoder.getVelocity();

            if (velocity > 0) {
                speed = -(velocity / MAXRPM);
            } else
            if (velocity < 0) {
                speed = (velocity / MAXRPM);
            } else {
                speed = 0.000001;
            }
        }

        sparkMax.set(speed);
    }

    public void disable() {
        sparkMax.set(0);
    }

    public double getRPM() {
        return encoder.getVelocity();
    }

    public double getPosition() {
        return encoder.getPosition();
    }

    public int getAddress() { return address; }
    
}
