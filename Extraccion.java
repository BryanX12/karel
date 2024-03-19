import kareltherobot.*;
import java.util.concurrent.Semaphore;

public class Extraccion {
    public volatile int beepers;

    public Semaphore beepMutex = new Semaphore(1);

    public Extraccion() {
        this.beepers = 0;
    }

    public int getBeepers() {
        try {
            beepMutex.acquire();
            return beepers;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        } finally {
            beepMutex.release();
        }
    }

    public void putBeeper(MejorRobot robot) {
        try {
            beepMutex.acquire();
            beepers++;
            robot.beepers--;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            beepMutex.release();
        }
        robot.putBeeper();
    }

    public void pickBeeper(MejorRobot robot) {
        try {
            beepMutex.acquire();
            beepers--;
            robot.beepers++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            beepMutex.release();
        }
        robot.pickBeeper();
    }
}
