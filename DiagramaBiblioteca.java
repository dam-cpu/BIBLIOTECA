import java.awt.*;
import java.awt.geom.*;

public class DiagramaBiblioteca extends Canvas {

    public DiagramaBiblioteca() {
        
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setStroke(new BasicStroke(3.0f));
        
        int ancho = getWidth();
        int alto = getHeight();
        int margen = 20;
        
        // Dibujar paredes exteriores
        g2d.setColor(Color.BLACK);
        g2d.drawRect(margen, margen, ancho - 2 * margen, alto - 2 * margen);
        
        // Dibujar estanterías
        g2d.setColor(Color.DARK_GRAY);
        int anchoEstanteria = 40;
        int altoEstanteria = alto - 2 * margen - 100; // Espacio para mesas
        
        for(int i = 0; i < 4; i++) {
            int x = margen + 80 + i * 120;
            g2d.fillRect(x, margen + 50, anchoEstanteria, altoEstanteria);
        }
        
        // Dibujar cubículos de empleados
        g2d.setColor(Color.LIGHT_GRAY);
        int anchoCubiculo = 80;
        int altoCubiculo = 60;
        
        // Cubículo izquierdo
        g2d.fillRect(margen + 20, alto - margen - altoCubiculo - 20, anchoCubiculo, altoCubiculo);
        
        // Cubículo derecho
        g2d.fillRect(ancho - margen - anchoCubiculo - 20, alto - margen - altoCubiculo - 20, anchoCubiculo, altoCubiculo);
        
        // Dibujar mesas de estudio
        g2d.setColor(new Color(139, 69, 19)); // Color marrón
        int anchoMesa = 60;
        int altoMesa = 30;
        
        for(int i = 0; i < 6; i++) {
            int x = margen + 60 + i * 100;
            int y = alto - margen - altoMesa - 100;
            g2d.fillRect(x, y, anchoMesa, altoMesa);
        }
        
        // Dibujar sillas
        g2d.setColor(Color.BLUE);
        int anchoSilla = 20;
        int altoSilla = 20;
        
        for(int i = 0; i < 6; i++) {
            int xMesa = margen + 60 + i * 100;
            int yMesa = alto - margen - altoMesa - 100;
            
            // Silla arriba de la mesa
            g2d.fillRect(xMesa + anchoMesa/2 - anchoSilla/2, yMesa - altoSilla - 5, anchoSilla, altoSilla);
            
            // Silla abajo de la mesa
            g2d.fillRect(xMesa + anchoMesa/2 - anchoSilla/2, yMesa + altoMesa + 5, anchoSilla, altoSilla);
        }
        
        // Dibujar puerta
        g2d.setColor(Color.WHITE);
        int anchoPuerta = 50;
        int altoPuerta = 80;
        int xPuerta = ancho - margen - anchoPuerta - 10;
        int yPuerta = margen + (alto - 2 * margen - altoPuerta) / 2;
        g2d.fillRect(xPuerta, yPuerta, anchoPuerta, altoPuerta);
        
        // Marco de la puerta
        g2d.setColor(Color.BLACK);
        g2d.drawRect(xPuerta, yPuerta, anchoPuerta, altoPuerta);
        
        // Dibujar ventana
        g2d.setColor(Color.CYAN);
        int anchoVentana = 80;
        int altoVentana = 40;
        int xVentana = margen + (ancho - 2 * margen - anchoVentana) / 2;
        int yVentana = margen + 10;
        g2d.fillRect(xVentana, yVentana, anchoVentana, altoVentana);
        
        // Marco de la ventana
        g2d.setColor(Color.BLACK);
        g2d.drawRect(xVentana, yVentana, anchoVentana, altoVentana);
        
        // Dibujar etiquetas
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Biblioteca", ancho/2 - 30, margen - 5);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.drawString("Estanterías", margen + 80, margen + 40);
        g2d.drawString("Cubículos", margen + 20, alto - margen - altoCubiculo - 30);
        g2d.drawString("Mesas", margen + 60, alto - margen - altoMesa - 110);
        g2d.drawString("Puerta", xPuerta + 10, yPuerta + altoPuerta + 15);
        g2d.drawString("Ventana", xVentana + 10, yVentana + altoVentana + 15);
        
        // Leyenda
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("Leyenda:", margen + 20, alto - margen + 20);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        
        // Elementos de la leyenda
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(margen + 20, alto - margen + 30, 15, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Estanterías", margen + 40, alto - margen + 40);
        
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(margen + 20, alto - margen + 45, 15, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Cubículos", margen + 40, alto - margen + 55);
        
        g2d.setColor(new Color(139, 69, 19));
        g2d.fillRect(margen + 20, alto - margen + 60, 15, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Mesas", margen + 40, alto - margen + 70);
        
        g2d.setColor(Color.BLUE);
        g2d.fillRect(margen + 20, alto - margen + 75, 15, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Sillas", margen + 40, alto - margen + 85);
        
    }
}