import kareltherobot.*;
import java.awt.Color;
import java.util.concurrent.Semaphore;

public class Extractor extends MejorRobot implements Runnable {

    private static final Semaphore mutex = new Semaphore(1);
    private static Minero minero;
    public int DoneExt = 0;

    public Extractor(int street, int avenue, Color badge) {
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
            MinaMain.salida.beepers++;
        }
    }

    public static void despertarMinero(){
        minero.minerWaiting.release();
    }

    @Override
    public void run() {
        moveToEntrance();
        while (true) {
            
            while (true) {
                turn(East);
                try {
                    moveMutex.acquire();
                    if (!checkRobot(this, street, avenue + 1) && MinaMain.salida.getBeepers() >= max) break;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    moveMutex.release();
                }
            }
    
            turn(East);
            moveTo(3);
            while (beepers < max) MinaMain.salida.pickBeeper(this);
            moveToBodegas();

            while (beepers > 0) {
                if (MinaMain.bodega.getBeepers() < 30){
                    MinaMain.bodega.putBeeper(this);
                    DoneExt+=1;
                }
                
                else if(MinaMain.bodega2.getBeepers() < 30){ 
                    if (beepers == 5) move();  
                    MinaMain.bodega2.putBeeper(this);
                    DoneExt+=1;
                }
                else if(MinaMain.bodega3.getBeepers() < 30){ 
                    if (beepers == 5) moveTo(5);  
                    MinaMain.bodega3.putBeeper(this);
                    DoneExt+=1;
                }
                else if(MinaMain.bodega4.getBeepers() < 30){ 
                    if (beepers == 5) moveTo(6);  
                    MinaMain.bodega4.putBeeper(this);
                    DoneExt+=1;
                }
                else if(MinaMain.bodega5.getBeepers() < 30){ 
                    if (beepers == 5) moveTo(7);  
                    MinaMain.bodega5.putBeeper(this);
                    DoneExt+=1;
                }

            }
            turn(North);
            move();
            if (DoneExt == 120) {
                despertarMinero();
                moveTo(9);
                turnOff();
            }
            else{
                moveToEntrance();
            }
        }
    }
}
