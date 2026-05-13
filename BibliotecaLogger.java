import java.util.logging.*;
import java.io.IOException;

public class BibliotecaLogger {

    private static final Logger logger = Logger.getLogger(BibliotecaLogger.class.getName());

    public static void inicializar() {
        try {
            FileHandler fh = new FileHandler("biblioteca.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.severe("No se pudo inicializar el archivo de log: " + e.getMessage());
        }
    }

    public static void logError(String mensaje, Exception e) {
        logger.severe(mensaje + ": " + e.getMessage());
    }

    public static void logWarning(String mensaje) {
        logger.warning(mensaje);
    }

    public static void logInfo(String mensaje) {
        logger.info(mensaje);
    }

}