package frc.robot.lib;

public class Vector2 {
    public double x, y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double length()
    {
        return Math.sqrt(x*x + y*y);
    }
    
    Vector2 normalize()
    {
        Vector2 result = new Vector2();

        double length, ilength;
        length = length();
        if (length == 0.0) length = 1.0;
        ilength = 1.0/length;

        result.x *= ilength;
        result.y *= ilength;

        return result;
    }
}
