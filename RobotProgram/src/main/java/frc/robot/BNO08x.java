package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;

public class BNO08x {

    private SerialPort connection;

    public void init() {
        if (isConnected())
            deinit();

        connection = new SerialPort(115200, SerialPort.Port.kUSB1);
    }

    public Vector3 read() {
        if (connection == null) {
            System.out.println("You did something stupid");
            return null;
        }

        if (connection.getBytesReceived() > 0) {
            String[] data = connection.readString().split(",");

            double x = Double.parseDouble(data[0]);
            double y = Double.parseDouble(data[1]);
            double z = Double.parseDouble(data[2]);
            return new Vector3(x, y, z);
        }

        return null;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void deinit() {
        connection.flush();
        connection.close();
        connection = null;
    }
}