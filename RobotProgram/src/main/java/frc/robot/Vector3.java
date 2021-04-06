package frc.robot;

public class Vector3 {

    double x, y, z;

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    double length()
    {
        return Math.sqrt(x*x + y*y + z*z);
    }
    
    Vector3 normalize()
    {
        Vector3 result = new Vector3();

        double length, ilength;
        length = length();
        if (length == 0.0) length = 1.0;
        ilength = 1.0/length;

        result.x *= ilength;
        result.y *= ilength;
        result.z *= ilength;

        return result;
    }
}
