import java.awt.*;
import java.awt.geom.*;

public class GraficoLineal extends GraficoBase {
    
    public GraficoLineal() {
        super();
    }
    
    public void dibujar(Graphics2D g2d) {
        if (datos == null || datos.length == 0) return;
        
        int width = g2d.getClipBounds().width;
        int height = g2d.getClipBounds().height;
        
        // Dibujar título
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(titulo, 50, 30);
        
        // Encontrar máximo para escalar
        double max = 0;
        for (double d : datos) {
            max = Math.max(max, d);
        }
        
        // Dibujar línea
        g2d.setColor(colores[0]);
        g2d.setStroke(new BasicStroke(3.0f));
        
        int prevX = 50;
        int prevY = height - 50 - (int)(datos[0] / max * (height - 100));
        
        for (int i = 1; i < datos.length; i++) {
            int x = 50 + i * (width - 100) / (datos.length - 1);
            int y = height - 50 - (int)(datos[i] / max * (height - 100));
            
            g2d.drawLine(prevX, prevY, x, y);
            
            // Dibujar punto
            g2d.fillOval(x - 3, y - 3, 6, 6);
            
            prevX = x;
            prevY = y;
        }
        
        // Dibujar etiquetas
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = 0; i < datos.length; i++) {
            int x = 50 + i * (width - 100) / (datos.length - 1);
            int y = height - 50 - (int)(datos[i] / max * (height - 100));
            if (etiquetas != null && i < etiquetas.length) {
                g2d.drawString(etiquetas[i], x - 10, height - 20);
            }
            g2d.drawString(String.valueOf((int)datos[i]), x - 10, y - 10);
        }
    }
}