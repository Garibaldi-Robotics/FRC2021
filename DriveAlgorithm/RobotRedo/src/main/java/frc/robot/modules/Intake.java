package frc.robot.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake {

    VictorSPX motor;

    public Intake() {
        motor = new VictorSPX(5);
    }

    public void Start() {
        motor.set(ControlMode.PercentOutput, 0.2);
    }

    public void Stop() {
        motor.set(ControlMode.PercentOutput, 0);
    }

    public void Reverse() {
        motor.set(ControlMode.PercentOutput, -0.2);
    }
    
}
