package frc.robot.shooter;

public class ShootCoroutine extends Thread {

    private Revolver revolver;

    public ShootCoroutine(Revolver revolver) {
        this.revolver = revolver;
    }

    @Override
    public void run() {
        revolver.Rev();

        // Wait for the flywheel to be at target speed
        while (!revolver.isTargetSpeed()) {}

        revolver.ToggleSolenoid();

        safeSleep(500);

        revolver.ToggleSolenoid();

        safeSleep(500);

        revolver.RotateChamber();
        revolver.Slow();
    }

    private void safeSleep(long milis) {
        try { sleep(milis); } catch (InterruptedException e) {}
    }
    
}
