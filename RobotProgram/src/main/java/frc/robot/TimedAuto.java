package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class TimedAuto {

    private Timer timer;
    private ArrayList<Double> executedTimes = new ArrayList<Double>();

    public void start() {
        timer = new Timer();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public boolean isTime(double time) {

        if (timer == null) {
            System.out.println("TimedAuto needs to be started before the time can be checked");
            System.exit(1);
            return false;
        }
        
        if (executedTimes.contains(time))
            return false;

        if (timer.get() >= time) {
            executedTimes.add(time);
            return true;
        }

        return false;
    }
    
}