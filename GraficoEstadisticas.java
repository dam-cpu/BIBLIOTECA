import java.awt.*;

public class GraficoEstadisticas extends Canvas {
    
    private int[] valores;
    private String[] etiquetas;
    
    public GraficoEstadisticas(int[] valores, String[] etiquetas) {
        this.valores = valores;
        this.etiquetas = etiquetas;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        // Fondo
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        // Título
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Estadísticas de Préstamos Mensuales", 50, 30);
        
        // Dibujar gráfico de barras
        int numBars = valores.length;
        int barWidth = (width - 100) / numBars;
        int maxValue = 0;
        for (int v : valores) {
            maxValue = Math.max(maxValue, v);
        }
        
        for (int i = 0; i < numBars; i++) {
            int barHeight = (int) ((double) valores[i] / maxValue * (height - 100));
            int x = 50 + i * barWidth;
            int y = height - 50 - barHeight;
            
            // Barra
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, y, barWidth - 10, barHeight);
            
            // Borde
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth - 10, barHeight);
            
            // Etiqueta
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(etiquetas[i], x, height - 30);
            
            // Valor
            g2d.drawString(String.valueOf(valores[i]), x, y - 5);
        }
    }
}