import kareltherobot.*;
import java.awt.Color;
import java.awt.Robot;
import java.awt.color.*;

public class MinaMain implements Directions {
    public static void main(String[] args) {
        // Obtener los argumentos de línea de comandos para la cantidad de robots
        int cantidadMineros = 2; // Valor por defecto
        int cantidadTrenes = 2; // Valor por defecto
        int cantidadExtractores = 2; // Valor por defecto

        World.readWorld("mina.kwld");
        World.setVisible(true);

        // Analizar los argumentos de línea de comandos
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-m")) {
                cantidadMineros = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("-t")) {
                cantidadTrenes = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("-e")) {
                cantidadExtractores = Integer.parseInt(args[i + 1]);
            }
        }

        // Crear los mineros
        for (int i = 0; i < cantidadMineros; i++) {
            Minero minero = new Minero(7, 1, East, 0, 50, Color.black);
        }

        // Crear los trenes
        for (int i = 0; i < cantidadTrenes; i++) {
            Tren tren = new Tren(11, 13, East, Color.blue);
        }

        // Crear los extractores
        for (int i = 0; i < cantidadExtractores; i++) {
            Extractor extractor = new Extractor(1, 2, East, 0, Color.red);
        }
    }
}
