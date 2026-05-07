import java.awt.*;

public class MainGraficos {

    public static void main(String[] args) {
        // Crear ventana para gráfico de estadísticas
        TestGraficos ventanaEstadisticas = new TestGraficos("Gráfico de Estadísticas");
        ventanaEstadisticas.setSize(600, 400);
        ventanaEstadisticas.setVisible(true);

        // Crear ventana para diagrama de biblioteca
        TestDiagramaBiblioteca ventanaDiagrama = new TestDiagramaBiblioteca("Diagrama de Biblioteca");
        ventanaDiagrama.setSize(600, 400);
        ventanaDiagrama.setVisible(true);

        // Crear ventana para mejoras de gráficos
        TestMejorasGraficos ventanaMejoras = new TestMejorasGraficos("Mejoras de Gráficos");
        ventanaMejoras.setSize(800, 400);
        ventanaMejoras.setVisible(true);
    }
}