package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import com.studica.frc.TitanQuad;

public class DriveMotors {

    private CANSparkMax motors[];
    //private TitanQuad titanQuad;

    int biases[];

    public DriveMotors(int[] canIDs, int[] biases) {
        if (canIDs.length != 4 || biases.length != 4) {
            System.out.println("DriveMotors is only made to use 4 drive motors");
            System.exit(1);
        }

        motors = new CANSparkMax[4];

        for (int i = 0; i < 4; i++)
            motors[i] = new CANSparkMax(canIDs[i], MotorType.kBrushless);
 
        this.biases = biases;
    }

    public void driveUniform(double speed, Axis axis) {

        double speeds[] = new double[4];
        for (int i = 0; i < 4; i++)
            speeds[i] = speed;


        if (axis == Axis.Y) {
            drive(speeds);
        } else if (axis == Axis.X) {
            drive(new double[]{speeds[0], speeds[1] * -1, speeds[2] * -1, speeds[3]});
        }
    }

    public void driveTurn(double speed) {

        double speeds[] = new double[4];
        for (int i = 0; i < 4; i++)
            speeds[i] = speed;
        
        drive(new double[]{speeds[0] * -1, speeds[1], speeds[2] * -1, speeds[3]});
    }


    public void drive(double[] speeds) {
        for (int i = 0; i < 4; i++) {
            setMotor(i, speeds[i] * biases[i]);
        }
    }

    public void stop() {
        for (int i = 0; i < 4; i++) {
            setMotor(i, 0);
        }
    }

    private void setMotor(int motor, double speed) {
        if (speed == 0 || speed == -0)
            speed = 0.001;

        motors[motor].set(speed);
    }


    public enum Axis {
        X, Y
    }
    
    public enum Type {
        SparkMax, TitanQuad
    }
}