package frc.robot.drive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class NeoMotor {
    public static double MAXRPM = 5800.0;

    private int address;
    private CANSparkMax sparkMax;
    private CANEncoder encoder;

    private double ratio = 1;

    public NeoMotor(int address) {
        this.address = address;

        sparkMax = new CANSparkMax(address, MotorType.kBrushless);

        assert sparkMax != null;

        encoder = sparkMax.getEncoder();
    }

    public NeoMotor(int address, boolean isInverted) {
        this(address);
        
        sparkMax.setInverted(isInverted);
    }

    public NeoMotor(int address, double ratio) {
        this(address);

        this.ratio = ratio;
    }

    public NeoMotor(int address, boolean isInverted, double ratio) {
        this(address, isInverted);

        this.ratio = ratio;
    }

    public void set(double speed) {

        // Brakes, because they removed it for some reason
        if (speed == 0) {

            speed = 0.01;
            double velocity = encoder.getVelocity();

            if (velocity > 0) {
                //speed = -(velocity / MAXRPM) * 2;
            } else
            if (velocity < 0) {
                //speed = (velocity / MAXRPM) * 2;
            } else {
                //speed = 0.001;
            }
        }

        sparkMax.set(speed);
    }

    public void disable() {
        sparkMax.set(0);
    }

    public double getRPM() {
        return encoder.getVelocity() * ratio;
    }

    public double getPosition() {
        return encoder.getPosition() * ratio;
    }

    public double getRotations() {
        return (encoder.getPosition() / encoder.getCountsPerRevolution()) * ratio;
    }

    public int getAddress() { return address; }
    
}
