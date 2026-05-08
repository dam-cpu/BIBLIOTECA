import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diálogo para búsqueda avanzada de libros con criterios múltiples (no excluyentes)
 */
public class DialogoBusquedaAvanzada extends JDialog {
    private JTextField txtCampo;
    private JCheckBox chkTitulo;
    private JCheckBox chkAutor;
    private JCheckBox chkISBN;
    private List<Libro> resultados;
    private boolean busquedaRealizada;

    public DialogoBusquedaAvanzada(JFrame parent, List<Libro> librosDisponibles) {
        super(parent, "Búsqueda Avanzada de Libros", true);
        this.resultados = new ArrayList<>();
        this.busquedaRealizada = false;
        
        inicializarComponentes(librosDisponibles);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    private void inicializarComponentes(List<Libro> librosDisponibles) {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de búsqueda
        JPanel pnlBusqueda = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Etiqueta y campo de texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlBusqueda.add(new JLabel("Término de búsqueda:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtCampo = new JTextField(20);
        pnlBusqueda.add(txtCampo, gbc);
        
        // Checkboxes para criterios
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        chkTitulo = new JCheckBox("Título", true);
        pnlBusqueda.add(chkTitulo, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        chkAutor = new JCheckBox("Autor", true);
        pnlBusqueda.add(chkAutor, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        chkISBN = new JCheckBox("ISBN", true);
        pnlBusqueda.add(chkISBN, gbc);
        
        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBuscar = new JButton("Buscar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBusqueda(librosDisponibles);
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        pnlBotones.add(btnBuscar);
        pnlBotones.add(btnCancelar);
        
        // Agregar componentes a la ventana
        add(pnlBusqueda, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);
    }

    private void realizarBusqueda(List<Libro> librosDisponibles) {
        String termino = txtCampo.getText().trim().toLowerCase();
        
        // Validar que al menos un criterio esté seleccionado
        if (!chkTitulo.isSelected() && !chkAutor.isSelected() && !chkISBN.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione al menos un criterio de búsqueda",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar que el campo no esté vacío
        if (termino.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese un término de búsqueda",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Realizar búsqueda según los criterios seleccionados
        resultados.clear();
        for (Libro libro : librosDisponibles) {
            boolean encontrado = false;
            
            if (chkTitulo.isSelected() && 
                libro.getTitulo().toLowerCase().contains(termino)) {
                encontrado = true;
            }
            
            if (!encontrado && chkAutor.isSelected() && 
                libro.getAutor().toLowerCase().contains(termino)) {
                encontrado = true;
            }
            
            if (!encontrado && chkISBN.isSelected() && 
                libro.getIsbn().toLowerCase().contains(termino)) {
                encontrado = true;
            }
            
            if (encontrado) {
                resultados.add(libro);
            }
        }
        
        busquedaRealizada = true;
        
        // Mostrar resultados
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron libros que coincidan con los criterios de búsqueda",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Se encontraron " + resultados.size() + " libro(s)",
                    "Búsqueda completada", JOptionPane.INFORMATION_MESSAGE);
        }
        
        dispose();
    }

    public List<Libro> getResultados() {
        return resultados;
    }

    public boolean isBusquedaRealizada() {
        return busquedaRealizada;
    }
}
