import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * Diálogo para listar todos los usuarios registrados
 */
public class DialogoListarUsuarios extends JDialog {
    private JTable tblUsuarios;
    private DefaultTableModel modelo;

    public DialogoListarUsuarios(JFrame parent, Biblioteca biblioteca) {
        super(parent, "Listar Usuarios", true);
        
        inicializarComponentes(biblioteca);
        pack();
        setLocationRelativeTo(parent);
        setResizable(true);
        setSize(500, 400);
        setVisible(true);
    }

    private void inicializarComponentes(Biblioteca biblioteca) {
        setLayout(new BorderLayout(10, 10));

        // Tabla de usuarios
        String[] columnas = {"ID", "Nombre", "Tipo", "Libros Prestados"};
        Object[][] datos = {};
        modelo = new DefaultTableModel(datos, columnas);
        tblUsuarios = new JTable(modelo);
        tblUsuarios.setEnabled(false); // Tabla de solo lectura
        JScrollPane scrollTabla = new JScrollPane(tblUsuarios);

        // Llenar tabla
        for (Usuario usuario : biblioteca.getUsuarios()) {
            Object[] fila = {
                usuario.getId(),
                usuario.getNombre(),
                usuario.getTipo(),
                usuario.getLibrosPrestados().size()
            };
            modelo.addRow(fila);
        }

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        pnlBotones.add(btnCerrar);

        // Agregar componentes
        add(scrollTabla, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);
    }
}
