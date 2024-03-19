import kareltherobot.*;
import java.awt.Color;

public class Extractor extends MejorRobot {

    public Extractor(int street, int avenue, Color badge) {
        super(street, avenue, badge, 5);
    }

    private void extraerBeepers() {
        while (beepers < max && nextToABeeper()) {
            pickBeeper();
        }
    }

    private void esperarNotificacionTrenes() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void informarRecogidaBeepers() {
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void run() {
        moveToEntrance();
        // extraerBeepers();
    }
}
