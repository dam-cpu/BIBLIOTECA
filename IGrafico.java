public interface IGrafico {
    void dibujar(java.awt.Graphics2D g2d);
    void actualizarDatos(double[] datos);
    void setEtiquetas(String[] etiquetas);
    void setColores(java.awt.Color[] colores);
    void setTitulo(String titulo);
}