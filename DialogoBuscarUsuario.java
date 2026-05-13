import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class DialogoBuscarUsuario extends JDialog {
    private JTextField txtBusqueda;
    private JTable tblResultados;
    private DefaultTableModel modelo;

    public DialogoBuscarUsuario(JFrame parent, Biblioteca biblioteca) {
        super(parent, "Buscar Usuario", true);
        
        inicializarComponentes(biblioteca);
        pack();
        setLocationRelativeTo(parent);
        setResizable(true);
        setSize(500, 300);
        setVisible(true);
    }

    private void inicializarComponentes(Biblioteca biblioteca) {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlBusqueda.add(new JLabel("Buscar:"));
        
        txtBusqueda = new JTextField(20);
        pnlBusqueda.add(txtBusqueda);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> realizarBusqueda(biblioteca));
        pnlBusqueda.add(btnBuscar);

        String[] columnas = {"ID", "Nombre", "Tipo"};
        Object[][] datos = {};
        modelo = new DefaultTableModel(datos, columnas);
        tblResultados = new JTable(modelo);
        tblResultados.setEnabled(false);
        JScrollPane scrollTabla = new JScrollPane(tblResultados);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        pnlBotones.add(btnCerrar);

        add(pnlBusqueda, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);
    }

    private void realizarBusqueda(Biblioteca biblioteca) {
        modelo.setRowCount(0);

        String termino = txtBusqueda.getText().trim().toLowerCase();
        
        if (termino.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese un término de búsqueda",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Usuario usuario : biblioteca.getUsuarios()) {
            if (usuario.getNombre().toLowerCase().contains(termino) ||
                usuario.getId().toLowerCase().contains(termino)) {
                Object[] fila = {
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getTipo()
                };
                modelo.addRow(fila);
            }
        }

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron usuarios que coincidan con la búsqueda",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
