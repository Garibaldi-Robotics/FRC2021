package frc.robot;

/**
 * This holds information on how the motors should behave.
 * Thanks to Kudzzy for the creative name
 */
public class YourMa {
    public static final YourMa STOP = new YourMa(0, 0, 0, 0);

    public static final YourMa TURNRIGHT = new YourMa(1, 1, -1, -1);
    public static final YourMa TURNLEFT = new YourMa(-1, -1, 1, 1);

    public static final YourMa FORWARD = new YourMa(1, 1, 1, 1);
    public static final YourMa BACKWARD = new YourMa(-1, -1, -1, -1);
    public static final YourMa LEFT = new YourMa(-1, 1, 1, -1);
    public static final YourMa RIGHT = new YourMa(1, -1, -1, 1);

    public static final YourMa FORWARD_LEFT = new YourMa(0, 1, 1, 0);
    public static final YourMa FORWARD_RIGHT = new YourMa(1, 0, 0, 1);
    public static final YourMa BACKWARD_LEFT = new YourMa(-1, 0, 0, -1);
    public static final YourMa BACKWARD_RIGHT = new YourMa(0, -1, -1, 0);


    public double frontLeft, frontRight, backLeft, backRight;

    public YourMa() {
        frontLeft = 0;
        frontRight = 0;
        backLeft = 0;
        backRight = 0;
    }

    public YourMa(YourMa yourMa) {
        frontLeft = yourMa.frontLeft;
        frontRight = yourMa.frontRight;
        backLeft = yourMa.backLeft;
        backRight = yourMa.backRight;
    }

    public YourMa(double frontLeft, double frontRight, double backLeft, double backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }

    public void scale(double scalar) {
        frontLeft *= scalar;
        frontRight *= scalar;
        backLeft *= scalar;
        backRight *= scalar;
    }

    public void drive(DriveMotors drivetrain, double speed) {
        drivetrain.drive(new double[] {frontLeft * speed, frontRight * speed, backLeft * speed, backRight * speed});
    }
    
    /**
     * For now, averages two YourMa directions
     * @param dir1 the first direction
     * @param dir2 the second direction
     * @param bias unused for now
     * @return
     */
    public static YourMa BlendBetween(YourMa dir1, YourMa dir2, double bias) {
        YourMa output = new YourMa();

        output.frontLeft = ((dir1.frontLeft * bias) + (dir2.frontLeft * (1 - bias))) / 2;
        output.frontRight = ((dir1.frontRight * bias) + (dir2.frontRight * (1 - bias))) / 2;
        output.backLeft = ((dir1.backLeft * bias) + (dir2.backLeft * (1 - bias))) / 2;
        output.backRight = ((dir1.backRight * bias) + (dir2.backRight * (1 - bias))) / 2;

        YourMa unsigned = new YourMa(Math.abs(output.frontLeft), Math.abs(output.frontRight), Math.abs(output.backLeft), Math.abs(output.backRight));
        double max = Math.max(unsigned.frontLeft, Math.max(unsigned.frontRight, Math.max(unsigned.backLeft, unsigned.backRight)));

        unsigned.frontLeft /= max;
        unsigned.frontRight /= max;
        unsigned.backLeft /= max;
        unsigned.backRight /= max;

        // restore signs
        if (output.frontLeft < 0)
            unsigned.frontLeft *= -1;
        if (output.frontRight < 0)
            unsigned.frontRight *= -1;
        if (output.backLeft < 0)
            unsigned.backLeft *= -1;
        if (output.backRight < 0)
            unsigned.backRight *= -1;


        return unsigned;
    }
}