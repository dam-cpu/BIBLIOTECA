import java.awt.*;

public abstract class GraficoBase implements IGrafico {
    protected String titulo;
    protected double[] datos;
    protected String[] etiquetas;
    protected Color[] colores;
    
    public GraficoBase() {
        this.titulo = "Gráfico";
        this.colores = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE};
    }
    
    public void actualizarDatos(double[] datos) {
        this.datos = datos;
    }
    
    public void setEtiquetas(String[] etiquetas) {
        this.etiquetas = etiquetas;
    }
    
    public void setColores(Color[] colores) {
        this.colores = colores;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    protected void calcularEscala() {
    }
    
    public abstract void dibujar(Graphics2D g2d);
}