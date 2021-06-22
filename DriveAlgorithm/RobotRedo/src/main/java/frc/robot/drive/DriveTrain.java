package frc.robot.drive;

public class DriveTrain {
    NeoMotor motors[];

    
    public DriveTrain() {
        motors = new NeoMotor[] {
            new NeoMotor(1, false), new NeoMotor(2, true),
            new NeoMotor(3, false), new NeoMotor(4, true)
        };
    }

    public void DriveTank(float leftSpeed, float rightSpeed) {

        motors[0].set(leftSpeed);
        motors[1].set(leftSpeed);

        motors[2].set(rightSpeed);
        motors[3].set(rightSpeed);

    }

    public void Brake() {
        for (NeoMotor neoMotor : motors)
            neoMotor.set(0);
    }

    public void Disable() {
        for (NeoMotor neoMotor : motors)
            neoMotor.disable();
    }



    public void Drive2D(float speed, float angle) {
        
    }
}
