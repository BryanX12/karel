import kareltherobot.*;
import java.awt.Color;
import java.awt.Robot;
import java.awt.color.*;

public class MinaMain implements Directions {
    public static Extraccion extraccion = new Extraccion();
    public static Extraccion salida = new Extraccion();
    public static Extraccion bodega = new Extraccion();
    public static Extraccion bodega2 = new Extraccion();
    public static Extraccion bodega3 = new Extraccion();
    public static Extraccion bodega4 = new Extraccion();
    public static Extraccion bodega5 = new Extraccion();
    public static Minero minero;


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
        World.showSpeedControl(true);
        // Crear los mineros
        for (int i = 0; i < cantidadMineros; i++) {
            new Minero(8, i+2, Color.BLACK);
        }

        // Crear los trenes
        for (int i = 0; i < cantidadTrenes; i++) {
            new Tren(9, i+3, Color.BLUE);
        }

        // Crear los extractores
        for (int i = 0; i < cantidadExtractores; i++) {
            new Extractor(10, i+4, Color.RED);

        }
    }
}
