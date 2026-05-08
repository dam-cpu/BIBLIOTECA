import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoNuevoLibro extends JDialog {
    
    private JTextField txtISBN, txtTitulo, txtAutor, txtEditorial;
    private JSpinner spnPaginas;
    private JButton btnGuardar, btnCancelar;
    private JPanel pnlBotones;
    
    private boolean guardadoExitoso = false;
    private Libro nuevoLibro;
    
    public DialogoNuevoLibro(Frame parent) {
        super(parent, "Nuevo Libro", true);
        
        inicializarComponentes();
        
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }
    
    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Crear campos de texto
        txtISBN = new JTextField(20);
        txtTitulo = new JTextField(20);
        txtAutor = new JTextField(20);
        txtEditorial = new JTextField(20);
        
        // Crear spinner de páginas
        spnPaginas = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        
        // Agregar componentes con sus etiquetas
        agregarComponente("Título:", txtTitulo, gbc, 0);
        agregarComponente("Autor:", txtAutor, gbc, 1);
        agregarComponente("ISBN:", txtISBN, gbc, 2);
        agregarComponente("Editorial:", txtEditorial, gbc, 3);
        agregarComponente("Páginas:", spnPaginas, gbc, 4);
        
        // Panel de botones
        pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(new BotonGuardar());
        btnCancelar.addActionListener(new BotonCancelar());
        
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);
        
        // Agregar panel de botones al diálogo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pnlBotones, gbc);
    }
    
    private void agregarComponente(String etiqueta, JComponent componente, GridBagConstraints gbc, int y) {
        // Etiqueta
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel(etiqueta), gbc);
        
        // Componente
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(componente, gbc);
    }
    
    private boolean validarCampos() {
        if (txtISBN.getText().trim().isEmpty() || txtTitulo.getText().trim().isEmpty() ||
                txtAutor.getText().trim().isEmpty() || txtEditorial.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos requeridos", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar formato básico ISBN
        String isbn = txtISBN.getText().trim().replace("-", ""); // Eliminar guiones
        if (isbn.length() != 10 && isbn.length() != 13) {
            JOptionPane.showMessageDialog(this, "El ISBN debe tener 10 o 13 dígitos", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void guardarLibro() {
        if (validarCampos()) {
            nuevoLibro = new Libro(txtTitulo.getText().trim(), txtAutor.getText().trim(),
                    txtISBN.getText().trim(), (int) spnPaginas.getValue());
            JOptionPane.showMessageDialog(this, "Libro guardado exitosamente", "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            guardadoExitoso = true;
            dispose();
        }
    }
    
    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
    
    public Libro getLibro() {
        return nuevoLibro;
    }
    
    // Clase interna para el botón Guardar
    private class BotonGuardar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guardarLibro();
        }
    }
    
    // Clase interna para el botón Cancelar
    private class BotonCancelar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DialogoNuevoLibro dialogo = new DialogoNuevoLibro(null);
            System.exit(0);
        });
    }
}