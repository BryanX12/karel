import kareltherobot.*;
import java.awt.Color;

public class Minero extends Robot implements Runnable {

    private int capacidadMaxima;
    private int currentStreet;
    private int currentAvenue;
    private int beepersInBag;

    public Minero(int street, int avenue, Direction direction, int beepers, int capacidadMaxima, Color badge) {
        super(street, avenue, direction, beepers, badge);
        this.capacidadMaxima = capacidadMaxima;
        this.currentStreet = street;
        this.currentAvenue = avenue;
        this.beepersInBag = 0; // Inicialmente no hay beepers en la bolsa
        Thread thread = new Thread(this);
        thread.start();
    }

    public int beepersInBag() {
        return this.beepersInBag();
    }

    public void extraer() {
        while (beepersInBag < capacidadMaxima && nextToABeeper()) {
            pickBeeper();
            this.beepersInBag++; // Incrementar el contador de beepers
        }
        llevarBeepersAlPuntoDeTransporte();
    }

    private void llevarBeepersAlPuntoDeTransporte() {
        // Mover al minero al punto de transporte (calle 11, avenida 13)
        moveToTransportPoint();

        // Depositar los beepers en el punto de transporte
        while (beepersInBag() > 0) {
            putBeeper();
        }

        // Informar a los trenes que los beepers están disponibles
        // Aquí debería ir el código para informar a los trenes, asumiendo que se implementará más tarde
    }

    private void moveToTransportPoint() {
        // Mover al minero a la calle 11
        while (currentStreet < 11) {
            move();
            currentStreet++;
        }

        // Mover al minero a la avenida 13
        turnLeft();
        while (currentAvenue < 13) {
            move();
            currentAvenue++;
        }
    }

    @Override
    public void run() {
        extraer();
    }
}
