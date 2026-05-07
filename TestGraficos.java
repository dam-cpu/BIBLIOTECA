import java.awt.*;

public class TestGraficos extends Frame {
    
    private GraficoEstadisticas grafica;
    private int[] valores;
    private String[] etiquetas;
    
    public TestGraficos (String titulo) {
        super(titulo);
        // Inicializar datos de ejemplo
        valores = new int[]{10, 20, 15, 25, 30};
        etiquetas = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo"};
        // Aquí creamos la instancia del componente gráfico a crear  
        grafica = new GraficoEstadisticas(valores, etiquetas);
        add(grafica,"Center");
    }
    
    public boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_DESTROY) {
            hide();
            dispose();
            return true;
        }
        return super.handleEvent(e);
    }
}