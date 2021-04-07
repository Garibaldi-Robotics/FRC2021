package frc.robot.drive;

import java.util.ArrayList;

public class MecanumBuilder {
    ArrayList<NeoMotor> rightFrontGroup, rightBackGroup, leftFrontGroup, leftBackGroup;

    public MecanumBuilder() {
        rightFrontGroup = new ArrayList<NeoMotor>();
        rightBackGroup = new ArrayList<NeoMotor>();
        leftFrontGroup = new ArrayList<NeoMotor>();
        leftBackGroup = new ArrayList<NeoMotor>();
    }

    public MecanumBuilder addMotorRF(int address) {
        rightFrontGroup.add(new NeoMotor(address, false));
        return this;
    }
    public MecanumBuilder addMotorLF(int address) {
        leftFrontGroup.add(new NeoMotor(address, true));
        return this;
    }
    public MecanumBuilder addMotorRB(int address) {
        rightBackGroup.add(new NeoMotor(address, false));
        return this;
    }
    public MecanumBuilder addMotorLB(int address) {
        leftBackGroup.add(new NeoMotor(address, true));
        return this;
    }

    public MecanumDrive build() {
        NeoMotor[] rfArray = (NeoMotor[]) rightFrontGroup.toArray();
        NeoMotor[] rbArray = (NeoMotor[]) rightBackGroup.toArray();
        NeoMotor[] lfArray = (NeoMotor[]) leftFrontGroup.toArray();
        NeoMotor[] lbArray = (NeoMotor[]) leftBackGroup.toArray();

        return new MecanumDrive(new NeoMotor[][] {
            rfArray,
            lfArray,
            rbArray,
            lbArray
        });
    }
}
