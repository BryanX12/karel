import kareltherobot.*;
import java.awt.Color;

public class Tren extends Robot implements Runnable {

    private static int capacidadMaxima = 120;
    private int currentStreet;
    private int currentAvenue;
    private int beepersInBag;
    private Extraccion extraccion;


    public Tren(int street, int avenue, Direction direction, Color badge) {
        super(street, avenue, direction, capacidadMaxima, badge);
        this.currentStreet = street;
        this.currentAvenue = avenue;
        this.beepersInBag = 0; // Inicialmente no hay beepers en la bolsa
        this.extraccion = new Extraccion();
        Thread thread = new Thread(this);
        thread.start();
    }

    
    public int beepersInBag() {
        return this.beepersInBag;
    }

    public void recogerBeepersDelPuntoDeTransporte() {
        // Mover al tren al punto de transporte (calle 11, avenida 13)
        moveToTransportPoint();

        // Recoger los beepers del punto de transporte
        while (nextToABeeper() && beepersInBag() < capacidadMaxima) {
            pickBeeper();
        }

        // Llevar los beepers al punto de extracción
        llevarBeepersAlPuntoDeExtraccion();
    }

    private void llevarBeepersAlPuntoDeExtraccion() {
        // Mover al tren al punto de extracción (calle 1, avenida 2)
        moveToExtraccion();

        // Depositar los beepers en el punto de extracción
        while (beepersInBag() > 0) {
            putBeeper();
        }

        // Notificar a los extractores que los beepers están disponibles en el punto de extracción
        extraccion.notifyExtractores();
    }

    private void moveToTransportPoint() {
        // Mover al tren a la calle 11
        while (currentStreet < 11) {
            move();
            currentStreet++;
        }

        // Mover al tren a la avenida 13
        turnLeft();
        while (currentAvenue < 13) {
            move();
            currentAvenue++;
        }
    }

    private void moveToExtraccion() {
        // Mover al tren a la calle 1
        turnAround();
        while (currentStreet > 1) {
            move();
            currentStreet--;
        }

        // Mover al tren a la avenida 2
        turnLeft();
        while (currentAvenue > 2) {
            move();
            currentAvenue--;
        }
    }

    private void turnAround() {
        turnLeft();
        turnLeft();
    }

    @Override
    public void run() {
        recogerBeepersDelPuntoDeTransporte();
    }
}
