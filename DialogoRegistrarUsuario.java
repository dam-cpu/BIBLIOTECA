import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Diálogo para registrar un nuevo usuario
 */
public class DialogoRegistrarUsuario extends JDialog {
    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtTipo;
    private boolean guardadoExitoso;
    private Usuario usuarioCreado;

    public DialogoRegistrarUsuario(JFrame parent) {
        super(parent, "Registrar Usuario", true);
        this.guardadoExitoso = false;
        
        inicializarComponentes();
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID del Usuario:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtID = new JTextField(15);
        add(txtID, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(15);
        add(txtNombre, gbc);

        // Tipo (Estudiante, Profesor, etc.)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        add(new JLabel("Tipo de Usuario:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTipo = new JTextField(15);
        add(txtTipo, gbc);

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnCancelar.addActionListener(e -> dispose());

        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(pnlBotones, gbc);
    }

    private void guardarUsuario() {
        String id = txtID.getText().trim();
        String nombre = txtNombre.getText().trim();
        String tipo = txtTipo.getText().trim();

        // Validar campos
        if (id.isEmpty() || nombre.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear nuevo usuario
        usuarioCreado = new Usuario(id, nombre, tipo);
        guardadoExitoso = true;
        
        JOptionPane.showMessageDialog(this,
                "Usuario registrado exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }

    public Usuario getUsuario() {
        return usuarioCreado;
    }
}
