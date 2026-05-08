import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoUbicacion extends JDialog {
    
    private DiagramaBiblioteca diagrama;
    private Libro libro;
    
    public DialogoUbicacion(JFrame parent, Libro libro) {
        super(parent, "Ubicación del Libro", true);
        this.libro = libro;
        
        // Configuración básica del diálogo
        setSize(800, 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // Crear y agregar el diagrama
        diagrama = new DiagramaBiblioteca();
        add(diagrama, BorderLayout.CENTER);
        
        // Extraer últimos 3 dígitos del ISBN
        String isbn = libro.getIsbn();
        String ubicacion = isbn.substring(Math.max(0, isbn.length() - 3));
        int numLibrero = Integer.parseInt(ubicacion);
        diagrama.setLibroUbicacion(numLibrero);
        
        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        // Etiquetas de información
        JLabel titleLabel = new JLabel("Título: " + libro.getTitulo());
        JLabel isbnLabel = new JLabel("ISBN: " + libro.getIsbn());
        JLabel ubicacionLabel = new JLabel("Número de Librero: " + numLibrero);
        
        infoPanel.add(titleLabel);
        infoPanel.add(isbnLabel);
        infoPanel.add(ubicacionLabel);
        
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoPanel, BorderLayout.NORTH);
        
        // Botón de cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new CierraDialogo());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private class CierraDialogo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}