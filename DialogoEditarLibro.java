import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoEditarLibro extends JDialog {
    
    private JTextField txtISBN, txtTitulo, txtAutor, txtEditorial;
    private JSpinner spnPaginas;
    private JButton btnGuardar, btnCancelar;
    private JPanel pnlBotones;
    
    private boolean guardadoExitoso = false;
    private Libro libroEditado;
    private Libro libroOriginal;
    
    public DialogoEditarLibro(Frame parent, Libro libro) {
        super(parent, "Editar Libro", true);
        this.libroOriginal = libro;
        this.libroEditado = libro;
        
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
        
        txtISBN = new JTextField(libroOriginal.getIsbn(), 20);
        txtISBN.setEditable(false);
        txtTitulo = new JTextField(libroOriginal.getTitulo(), 20);
        txtAutor = new JTextField(libroOriginal.getAutor(), 20);
        txtEditorial = new JTextField(20);
        
        spnPaginas = new JSpinner(new SpinnerNumberModel(libroOriginal.getNumPaginas(), 1, 9999, 1));
        
        agregarComponente("Título:", txtTitulo, gbc, 0);
        agregarComponente("Autor:", txtAutor, gbc, 1);
        agregarComponente("ISBN:", txtISBN, gbc, 2);
        agregarComponente("Páginas:", spnPaginas, gbc, 3);
        
        pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(new BotonGuardar());
        btnCancelar.addActionListener(new BotonCancelar());
        
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pnlBotones, gbc);
    }
    
    private void agregarComponente(String etiqueta, JComponent componente, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel(etiqueta), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(componente, gbc);
    }
    
    private void guardarLibro() {
        libroEditado = new Libro(txtTitulo.getText().trim(), txtAutor.getText().trim(),
                libroOriginal.getIsbn(), (int) spnPaginas.getValue());
        
        JOptionPane.showMessageDialog(this, "Libro actualizado exitosamente", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        guardadoExitoso = true;
        dispose();
    }
    
    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
    
    public Libro getLibroEditado() {
        return libroEditado;
    }
    
    private class BotonGuardar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guardarLibro();
        }
    }
    
    private class BotonCancelar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}