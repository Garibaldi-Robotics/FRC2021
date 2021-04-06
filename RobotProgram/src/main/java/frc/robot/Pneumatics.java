package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Pneumatics {
    Compressor compressor;
    DoubleSolenoid solenoid1;
    DoubleSolenoid solenoid2;

    boolean compressorState = false;


    public Pneumatics() {
        compressor = new Compressor();
        solenoid1 = new DoubleSolenoid(0, 0, 1);
        solenoid2 = new DoubleSolenoid(0, 6, 7);

        compressor.setClosedLoopControl(true);
    }

    public void toggleSolenoids() {
        solenoid1.toggle();
        solenoid2.toggle();
    }

    public void toggleCompressor() {
        compressorState = !compressorState;

        if (compressorState)
            compressor.start();
        else
            compressor.stop();
    }
}
