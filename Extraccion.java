import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Extraccion {
    private boolean trenesListos = false;
    private boolean extractoresListos = false;
    private final Lock lock = new ReentrantLock();
    private final Condition trenesCondicion = lock.newCondition();
    private final Condition extractoresCondicion = lock.newCondition();

    public void notifyExtractores() {
        lock.lock();
        try {
            extractoresListos = true;
            extractoresCondicion.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void notifyTrenes() {
        lock.lock();
        try {
            trenesListos = true;
            trenesCondicion.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void esperarExtractores() throws InterruptedException {
        lock.lock();
        try {
            while (!extractoresListos) {
                extractoresCondicion.await();
            }
            extractoresListos = false; // Reiniciar el estado
        } finally {
            lock.unlock();
        }
    }

    public void esperarTrenes() throws InterruptedException {
        lock.lock();
        try {
            while (!trenesListos) {
                trenesCondicion.await();
            }
            trenesListos = false; // Reiniciar el estado
        } finally {
            lock.unlock();
        }
    }
}
