import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

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

        String[] columnas = {"ID", "Nombre", "Tipo", "Libros Prestados"};
        Object[][] datos = {};
        modelo = new DefaultTableModel(datos, columnas);
        tblUsuarios = new JTable(modelo);
        tblUsuarios.setEnabled(false);
        JScrollPane scrollTabla = new JScrollPane(tblUsuarios);

        for (Usuario usuario : biblioteca.getUsuarios()) {
            Object[] fila = {
                usuario.getId(),
                usuario.getNombre(),
                usuario.getTipo(),
                usuario.getLibrosPrestados().size()
            };
            modelo.addRow(fila);
        }

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        pnlBotones.add(btnCerrar);

        add(scrollTabla, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);
    }
}
