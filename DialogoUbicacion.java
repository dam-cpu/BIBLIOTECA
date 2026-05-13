import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoUbicacion extends JDialog {
    
    private DiagramaBiblioteca diagrama;
    private Libro libro;
    
    public DialogoUbicacion(JFrame parent, Libro libro) {
        super(parent, "Ubicación del Libro", true);
        this.libro = libro;
        
        setSize(800, 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        diagrama = new DiagramaBiblioteca();
        add(diagrama, BorderLayout.CENTER);
        
        String isbn = libro.getIsbn();
        String ubicacion = isbn.substring(Math.max(0, isbn.length() - 3));
        int numLibrero = Integer.parseInt(ubicacion);
        diagrama.setLibroUbicacion(numLibrero);
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("Título: " + libro.getTitulo());
        JLabel isbnLabel = new JLabel("ISBN: " + libro.getIsbn());
        JLabel ubicacionLabel = new JLabel("Número de Librero: " + numLibrero);
        
        infoPanel.add(titleLabel);
        infoPanel.add(isbnLabel);
        infoPanel.add(ubicacionLabel);
        
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoPanel, BorderLayout.NORTH);
        
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