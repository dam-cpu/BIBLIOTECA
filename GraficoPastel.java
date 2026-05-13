import java.awt.*;
import java.awt.geom.*;

public class GraficoPastel extends GraficoBase {
    
    public GraficoPastel() {
        super();
    }
    
    public void dibujar(Graphics2D g2d) {
        if (datos == null || datos.length == 0) return;
        
        int width = g2d.getClipBounds().width;
        int height = g2d.getClipBounds().height;
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(titulo, 50, 30);
        
        double total = 0;
        for (double d : datos) {
            total += d;
        }
        
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 4;
        
        double startAngle = 0;
        for (int i = 0; i < datos.length; i++) {
            double angle = (datos[i] / total) * 360;
            
            g2d.setColor(colores[i % colores.length]);
            g2d.fill(new Arc2D.Double(centerX - radius, centerY - radius,
                                    radius * 2, radius * 2, startAngle, angle, Arc2D.PIE));

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.draw(new Arc2D.Double(centerX - radius, centerY - radius,
                                    radius * 2, radius * 2, startAngle, angle, Arc2D.PIE));
            
            startAngle += angle;
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        startAngle = 0;
        for (int i = 0; i < datos.length; i++) {
            double angle = (datos[i] / total) * 360;
            double midAngle = startAngle + angle / 2;
            
            int labelX = (int)(centerX + (radius + 30) * Math.cos(Math.toRadians(midAngle - 90)));
            int labelY = (int)(centerY + (radius + 30) * Math.sin(Math.toRadians(midAngle - 90)));
            
            if (etiquetas != null && i < etiquetas.length) {
                g2d.drawString(etiquetas[i], labelX, labelY);
            }
            
            int percent = (int)((datos[i] / total) * 100);
            g2d.drawString(percent + "%", labelX, labelY + 15);
            
            startAngle += angle;
        }
    }
}