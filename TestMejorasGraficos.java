import java.awt.*;

public class TestMejorasGraficos extends Frame {

    private GraficoLineal graficoLineal;
    private GraficoPastel graficoPastel;

    public TestMejorasGraficos(String titulo) {
        super(titulo);

        graficoLineal = new GraficoLineal();
        graficoLineal.setTitulo("Tendencia de Préstamos");
        graficoLineal.actualizarDatos(new double[]{10, 15, 8, 22, 18, 25, 30});
        graficoLineal.setEtiquetas(new String[]{"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul"});

        graficoPastel = new GraficoPastel();
        graficoPastel.setTitulo("Distribución por Categoría");
        graficoPastel.actualizarDatos(new double[]{30, 25, 20, 15, 10});
        graficoPastel.setEtiquetas(new String[]{"Novelas", "Ciencia", "Historia", "Tecnología", "Arte"});

        setLayout(new GridLayout(1, 2));
        add(new CanvasGrafico(graficoLineal));
        add(new CanvasGrafico(graficoPastel));
    }

    public boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_DESTROY) {
            hide();
            dispose();
            return true;
        }
        return super.handleEvent(e);
    }

    private class CanvasGrafico extends Canvas {
        private IGrafico grafico;

        public CanvasGrafico(IGrafico grafico) {
            this.grafico = grafico;
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                               java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            grafico.dibujar(g2d);
        }
    }
}