import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * Diálogo para consultar préstamos de un usuario
 */
public class DialogoConsultarPrestamos extends JDialog {
    private JComboBox<String> cbxUsuarios;
    private JTable tblPrestamos;
    private DefaultTableModel modelo;

    public DialogoConsultarPrestamos(JFrame parent, Biblioteca biblioteca) {
        super(parent, "Consultar Préstamos", true);
        
        inicializarComponentes(biblioteca);
        pack();
        setLocationRelativeTo(parent);
        setResizable(true);
        setSize(600, 400);
        setVisible(true);
    }

    private void inicializarComponentes(Biblioteca biblioteca) {
        setLayout(new BorderLayout(10, 10));

        // Panel superior con selección de usuario
        JPanel pnlSeleccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSeleccion.add(new JLabel("Usuario:"));

        String[] usuariosArray = new String[biblioteca.getUsuarios().size()];
        if (biblioteca.getUsuarios().size() > 0) {
            for (int i = 0; i < biblioteca.getUsuarios().size(); i++) {
                Usuario usuario = biblioteca.getUsuarios().get(i);
                usuariosArray[i] = usuario.getNombre() + " (" + usuario.getId() + ")";
            }
        }
        cbxUsuarios = new JComboBox<>(usuariosArray);
        cbxUsuarios.addActionListener(e -> mostrarPrestamosUsuario(biblioteca));
        pnlSeleccion.add(cbxUsuarios);

        // Tabla de préstamos
        String[] columnas = {"Libro", "ISBN", "Estado"};
        Object[][] datos = {};
        modelo = new DefaultTableModel(datos, columnas);
        tblPrestamos = new JTable(modelo);
        tblPrestamos.setEnabled(false); // Tabla de solo lectura
        JScrollPane scrollTabla = new JScrollPane(tblPrestamos);

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        pnlBotones.add(btnCerrar);

        // Agregar componentes
        add(pnlSeleccion, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);

        // Mostrar préstamos del primer usuario por defecto
        if (cbxUsuarios.getSelectedIndex() >= 0) {
            mostrarPrestamosUsuario(biblioteca);
        }
    }

    private void mostrarPrestamosUsuario(Biblioteca biblioteca) {
        modelo.setRowCount(0); // Limpiar tabla

        String usuarioSeleccionado = (String) cbxUsuarios.getSelectedItem();
        if (usuarioSeleccionado == null) return;

        String usuarioID = usuarioSeleccionado.substring(usuarioSeleccionado.lastIndexOf("(") + 1,
                usuarioSeleccionado.lastIndexOf(")"));

        Usuario usuario = biblioteca.buscarUsuarioPorId(usuarioID);
        if (usuario != null) {
            for (Libro libro : usuario.getLibrosPrestados()) {
                Object[] fila = {
                    libro.getTitulo(),
                    libro.getIsbn(),
                    libro.isPrestado() ? "Prestado" : "Disponible"
                };
                modelo.addRow(fila);
            }
        }
    }
}
