import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class DialogoEstadisticas extends JDialog {
    private PanelGrafico panelGrafico;

    public DialogoEstadisticas(JFrame parent, Biblioteca biblioteca) {
        super(parent, "Estadísticas de Préstamos", true);
        
        inicializarComponentes(biblioteca);
        pack();
        setLocationRelativeTo(parent);
        setResizable(true);
        setSize(700, 500);
        setVisible(true);
    }

    private void inicializarComponentes(Biblioteca biblioteca) {
        setLayout(new BorderLayout(10, 10));

        panelGrafico = new PanelGrafico(biblioteca);
        add(panelGrafico, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        pnlBotones.add(btnCerrar);

        add(pnlBotones, BorderLayout.SOUTH);
    }

    private static class PanelGrafico extends JPanel {
        private Biblioteca biblioteca;
        private int[] datosPrestamosPorMes;

        public PanelGrafico(Biblioteca biblioteca) {
            this.biblioteca = biblioteca;
            this.datosPrestamosPorMes = new int[12]; 
            
            datosPrestamosPorMes[0] = 5; 
            datosPrestamosPorMes[1] = 8; 
            datosPrestamosPorMes[2] = 12;
            datosPrestamosPorMes[3] = 10;
            datosPrestamosPorMes[4] = 15;
            datosPrestamosPorMes[5] = 18;
            datosPrestamosPorMes[6] = 14;
            datosPrestamosPorMes[7] = 20;
            datosPrestamosPorMes[8] = 16;
            datosPrestamosPorMes[9] = 12;
            datosPrestamosPorMes[10] = 9;
            datosPrestamosPorMes[11] = 7;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int margenIzq = 50;
            int margenDer = 20;
            int margenArriba = 30;
            int margenAbajo = 50;

            int anchoGrafico = getWidth() - margenIzq - margenDer;
            int altoGrafico = getHeight() - margenArriba - margenAbajo;

            g2d.setColor(new Color(240, 240, 240));
            g2d.fillRect(margenIzq, margenArriba, anchoGrafico, altoGrafico);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("Préstamos de Libros por Mes", margenIzq + 200, margenArriba - 10);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(margenIzq, margenArriba, margenIzq, getHeight() - margenAbajo);
            g2d.drawLine(margenIzq, getHeight() - margenAbajo, getWidth() - margenDer, getHeight() - margenAbajo);

            int maxValor = Arrays.stream(datosPrestamosPorMes).max().orElse(20);

            int anchoBarra = anchoGrafico / 12;
            int espacioEntreBarra = 5;
            int actualAnchoBarra = anchoBarra - espacioEntreBarra;

            String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

            for (int i = 0; i < 12; i++) {
                double porcentaje = (double) datosPrestamosPorMes[i] / maxValor;
                int altoBarra = (int) (altoGrafico * porcentaje);

                int x = margenIzq + (i * anchoBarra) + espacioEntreBarra;
                int y = getHeight() - margenAbajo - altoBarra;

                GradientPaint gradient = new GradientPaint(x, y, new Color(70, 130, 180),
                        x, getHeight() - margenAbajo, new Color(100, 150, 200));
                g2d.setPaint(gradient);
                g2d.fillRect(x, y, actualAnchoBarra, altoBarra);

                g2d.setColor(Color.DARK_GRAY);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(x, y, actualAnchoBarra, altoBarra);

                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                String valor = String.valueOf(datosPrestamosPorMes[i]);
                g2d.drawString(valor, x + actualAnchoBarra / 2 - 5, y - 5);

                g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                g2d.drawString(meses[i], x + actualAnchoBarra / 2 - 10, getHeight() - margenAbajo + 15);
            }

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString("Cantidad", 5, margenArriba + 20);

            g2d.drawString("Meses", getWidth() / 2 - 20, getHeight() - 5);

            g2d.setColor(new Color(200, 200, 200));
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{5}, 0));
            int yMitad = margenArriba + altoGrafico / 2;
            g2d.drawLine(margenIzq, yMitad, getWidth() - margenDer, yMitad);
        }
    }
}
