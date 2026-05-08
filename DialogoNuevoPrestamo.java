import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Diálogo para registrar un nuevo préstamo de libro
 */
public class DialogoNuevoPrestamo extends JDialog {
    private JComboBox<String> cbxLibros;
    private JComboBox<String> cbxUsuarios;
    private JComboBox<String> cbxEmpleados;
    private JSpinner spnFecha;
    private boolean guardadoExitoso;
    private String libroISBN;
    private String usuarioID;
    private String empleadoID;

    public DialogoNuevoPrestamo(JFrame parent, Biblioteca biblioteca) {
        super(parent, "Nuevo Préstamo", true);
        this.guardadoExitoso = false;
        
        inicializarComponentes(biblioteca);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    private void inicializarComponentes(Biblioteca biblioteca) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Seleccionar libro
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Libro:"), gbc);

        gbc.gridx = 1;
        String[] librosArray = new String[biblioteca.getLibros().size()];
        for (int i = 0; i < biblioteca.getLibros().size(); i++) {
            Libro libro = biblioteca.getLibros().get(i);
            librosArray[i] = libro.getTitulo() + " (" + libro.getIsbn() + ")";
        }
        cbxLibros = new JComboBox<>(librosArray);
        add(cbxLibros, gbc);

        // Seleccionar usuario
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        String[] usuariosArray = new String[biblioteca.getUsuarios().size()];
        if (biblioteca.getUsuarios().size() > 0) {
            for (int i = 0; i < biblioteca.getUsuarios().size(); i++) {
                Usuario usuario = biblioteca.getUsuarios().get(i);
                usuariosArray[i] = usuario.getNombre() + " (" + usuario.getId() + ")";
            }
        }
        cbxUsuarios = new JComboBox<>(usuariosArray);
        add(cbxUsuarios, gbc);

        // Seleccionar empleado
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Empleado:"), gbc);

        gbc.gridx = 1;
        String[] empleadosArray = new String[biblioteca.getEmpleados().size()];
        if (biblioteca.getEmpleados().size() > 0) {
            for (int i = 0; i < biblioteca.getEmpleados().size(); i++) {
                Empleado empleado = biblioteca.getEmpleados().get(i);
                empleadosArray[i] = empleado.getNombre() + " (" + empleado.getId() + ")";
            }
        }
        cbxEmpleados = new JComboBox<>(empleadosArray);
        add(cbxEmpleados, gbc);

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPrestamo(biblioteca);
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(pnlBotones, gbc);
    }

    private void guardarPrestamo(Biblioteca biblioteca) {
        if (cbxLibros.getSelectedIndex() < 0 || cbxUsuarios.getSelectedIndex() < 0 ||
            cbxEmpleados.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione libro, usuario y empleado",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Extraer ISBN del item seleccionado
            String libroSeleccionado = (String) cbxLibros.getSelectedItem();
            libroISBN = libroSeleccionado.substring(libroSeleccionado.lastIndexOf("(") + 1,
                    libroSeleccionado.lastIndexOf(")"));

            // Extraer ID del usuario
            String usuarioSeleccionado = (String) cbxUsuarios.getSelectedItem();
            usuarioID = usuarioSeleccionado.substring(usuarioSeleccionado.lastIndexOf("(") + 1,
                    usuarioSeleccionado.lastIndexOf(")"));

            // Extraer ID del empleado
            String empleadoSeleccionado = (String) cbxEmpleados.getSelectedItem();
            empleadoID = empleadoSeleccionado.substring(empleadoSeleccionado.lastIndexOf("(") + 1,
                    empleadoSeleccionado.lastIndexOf(")"));

            // Realizar préstamo
            if (biblioteca.prestarLibro(libroISBN, usuarioID, empleadoID)) {
                guardadoExitoso = true;
                JOptionPane.showMessageDialog(this,
                        "Préstamo realizado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo realizar el préstamo. Verifique que el libro esté disponible",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al procesar el préstamo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }

    public String getLibroISBN() {
        return libroISBN;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public String getEmpleadoID() {
        return empleadoID;
    }
}
