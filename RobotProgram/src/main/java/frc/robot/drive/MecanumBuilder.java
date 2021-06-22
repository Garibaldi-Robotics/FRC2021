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
        NeoMotor[] rfArray = new NeoMotor[rightFrontGroup.size()];
        rfArray = rightFrontGroup.toArray(rfArray);
        NeoMotor[] rbArray = new NeoMotor[rightBackGroup.size()];
        rbArray = rightBackGroup.toArray(rbArray);
        NeoMotor[] lfArray = new NeoMotor[leftFrontGroup.size()];
        lfArray = leftFrontGroup.toArray(lfArray);
        NeoMotor[] lbArray = new NeoMotor[leftBackGroup.size()];
        lbArray = leftBackGroup.toArray(lbArray);

        return new MecanumDrive(new NeoMotor[][] {
            rfArray,
            lfArray,
            rbArray,
            lbArray
        });
    }
}
