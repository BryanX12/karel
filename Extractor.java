import kareltherobot.*;
import java.awt.Color;

public class Extractor extends Robot implements Runnable {
    private static final int CAPACIDAD_MAXIMA = 50;
    private int currentStreet;
    private int currentAvenue;
    private int beepersInBag;

    public Extractor(int street, int avenue, Direction direction, int beepers, Color badge) {
        super(street, avenue, direction, beepers, badge);
        this.currentStreet = street;
        this.currentAvenue = avenue;
        this.beepersInBag = 0; // Inicialmente no hay beepers en la bolsa
        Thread thread = new Thread(this);
        thread.start();
    }

    public int beepersInBag() {
        return this.beepersInBag;
    }

    private void extraerBeepers() {
        moveToExtraccion();

        while (beepersInBag() < CAPACIDAD_MAXIMA && nextToABeeper()) {
            pickBeeper();
        }

        informarRecogidaBeepers();

        esperarNotificacionTrenes();
    }

    private void moveToExtraccion() {
        while (currentStreet != 1) {
            if (!facingNorth()) {
                turnLeft();
            }
            move();
            currentStreet--;
        }

        while (currentAvenue != 2) {
            if (!facingWest()) {
                turnLeft();
            }
            move();
            currentAvenue--;
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
        extraerBeepers();
    }
}
