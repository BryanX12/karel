import kareltherobot.*;
import java.awt.Color;
import java.util.concurrent.Semaphore;

public class Minero extends MejorRobot implements Runnable {

    private static final Semaphore mutex = new Semaphore(1);
    public static final Semaphore minerWaiting = new Semaphore(0);
    private static final Semaphore doneMineMutex = new Semaphore(1);
    private static boolean minerMining = false;
    public static int doneMine = 0;


    public Minero(int street, int avenue, Color badge) {
        super(street, avenue, badge, 5);
    }

    public void extract() {
        if (!nextToABeeper()) return;
        while (beepers < max) {
            pickBeeper();
            this.beepers++; // Incrementar el contador de beepers
        }
    }

    public void place() {
        while (beepers > 0) {
            putBeeper();
            this.beepers--;
            MinaMain.extraccion.beepers++;
        }
    }

    @Override
    public void run() {
        moveToEntrance();
        moveToMine();
        move();
        while (true) {
            try {
                mutex.acquire();

                // Verificar si es el turno de minar
                if (minerMining) {
                    // Mina los beepers
                    if (doneMine == 120){
                        minerWaiting.release();
                        minerWaiting.wait();
                        exitMina();
                        turnOff();
                    }
                    turn(East);
                    while (!nextToABeeper()) move();
                    extract();
                    // Bloquear el acceso a doneMine mientras se actualiza
                    doneMineMutex.acquire();
                    try {
                        doneMine += 5;                    
                    } finally {
                        doneMineMutex.release();
                    }                   
                    turn(West);
                    moveTo(13);
                    place();
                    minerWaiting.release();
                    moveToWait();
                    turn(North);
                    minerWaiting.acquire();
                    move();
                } else {
                    // Cambiar el estado a minar
                    minerMining = true;
                    // Ir a esperar
                    moveToWait();
                    turn(North);
                    // Esperar a que el otro minero termine
                    mutex.release();
                    minerWaiting.acquire();
                    move();
                    
                    // Luego de ser despertado, volver a minar
                    continue;
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                mutex.release();
            }
        }
    }

}