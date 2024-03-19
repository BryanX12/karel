import java.awt.Color;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import kareltherobot.Directions;
import kareltherobot.Robot;

public class MejorRobot extends Robot {
    public static Semaphore moveMutex = new Semaphore(1);
    public static Vector<MejorRobot> robots = new Vector<>();

    public int street;
    public int avenue;
    public int beepers;
    public int max;

    public static final Direction[] DIRECTIONS = { Directions.West, Directions.South, Directions.East, Directions.North };
    public int direction;

    Thread thread;

    public MejorRobot(int street, int avenue, Color color, int max) {
        super(street, avenue, Directions.North, 0, color);
        this.street = street;
        this.avenue = avenue;
        this.beepers = 0;
        this.direction = 3;
        this.max = max;

        robots.add(this);

        this.thread = new Thread(this);
        this.thread.start();
    }

    public static boolean checkRobot(MejorRobot you, int street, int avenue) {
        Enumeration<MejorRobot> r = robots.elements();
        MejorRobot o;
        do {
            if (!r.hasMoreElements()) return false;
            o = r.nextElement();
        } while (o == you || (street != o.street || avenue != o.avenue));
        return true;
    }

    @Override
    public void turnLeft() {
        direction = (direction + 1) & 0b11;
        super.turnLeft();
    }

    public void turn(Direction dir) {
        while (DIRECTIONS[direction] != dir) turnLeft();
    }

    @Override
    public void move() {
        boolean moveVer = (direction & 1) == 1;
        boolean increase = (direction >> 1 & 1) == 1;

        int i = increase ? 1 : -1;

        int newStreet = street;
        int newAvenue = avenue;
        if (moveVer) newStreet += i;
        else newAvenue += i;

        try {
            moveMutex.acquire();
            boolean occupied = checkRobot(this, newStreet, newAvenue);

            if (occupied) {
                moveMutex.release();
                return;
            }

            street = newStreet;
            avenue = newAvenue;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        moveMutex.release();
        super.move();
    }

    public void moveTo(int pos) {
        boolean moveVer = (direction & 1) == 1;
        if (moveVer) 
            while (street != pos) move();
        else
            while (avenue != pos) move();
    }

    public void moveToEntrance() {
        // Se mueve a (1, 1)
        turn(West);
        moveTo(2);
        turn(South);
        moveTo(7);
        turn(West);
        moveTo(1);
        turn(South);
        moveTo(1);
    }

    public void moveToMine() {
        // Sigue el recorrido desde extraccion hacia (13, 11)
        turn(East);
        moveTo(8);
        turn(North);
        moveTo(11);
        turn(East);
        moveTo(12);
    }

    public void moveToExtraction() {
        // Sigue el recorrido desde la mina hacia (3, 1)
        turn(South);
        moveTo(6);
        turn(West);
        moveTo(3);
        turn(South);
        moveTo(1);
    }
    public void moveToWait() {
        turn(South);
        moveTo(10);
        turn(East);
        moveTo(14);
    }
    
}