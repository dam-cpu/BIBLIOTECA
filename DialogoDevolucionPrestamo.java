import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Diálogo para devolver un préstamo de libro con cálculo de multa por retraso
 */
public class DialogoDevolucionPrestamo extends JDialog {
    private JComboBox<String> cbxLibros;
    private JComboBox<String> cbxUsuarios;
    private JComboBox<String> cbxEmpleados;
    private JSpinner spnDiasRetraso;
    private JLabel lblMulta;
    private boolean guardadoExitoso;
    private double multaPorDia = 10.0; // 10 por día de retraso

    public DialogoDevolucionPrestamo(JFrame parent, Biblioteca biblioteca) {
        super(parent, "Devolución de Préstamo", true);
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

        // Seleccionar libro prestado
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Libro:"), gbc);

        gbc.gridx = 1;
        String[] librosArray = new String[biblioteca.getLibrosPrestados().size()];
        for (int i = 0; i < biblioteca.getLibrosPrestados().size(); i++) {
            Libro libro = biblioteca.getLibrosPrestados().get(i);
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

        // Días de retraso
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Días de retraso:"), gbc);

        gbc.gridx = 1;
        spnDiasRetraso = new JSpinner(new SpinnerNumberModel(0, 0, 365, 1));
        spnDiasRetraso.addChangeListener(e -> actualizarMulta());
        add(spnDiasRetraso, gbc);

        // Información de multa
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Multa a pagar:"), gbc);

        gbc.gridx = 1;
        lblMulta = new JLabel("$0.00");
        lblMulta.setFont(lblMulta.getFont().deriveFont(Font.BOLD, 14f));
        lblMulta.setForeground(Color.RED);
        add(lblMulta, gbc);

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDevolucion(biblioteca);
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
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(pnlBotones, gbc);
    }

    private void actualizarMulta() {
        int diasRetraso = (Integer) spnDiasRetraso.getValue();
        double multa = diasRetraso * multaPorDia;
        lblMulta.setText(String.format("$%.2f", multa));
    }

    private void guardarDevolucion(Biblioteca biblioteca) {
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
            String libroISBN = libroSeleccionado.substring(libroSeleccionado.lastIndexOf("(") + 1,
                    libroSeleccionado.lastIndexOf(")"));

            // Obtener días de retraso
            int diasRetraso = (Integer) spnDiasRetraso.getValue();
            double multa = diasRetraso * multaPorDia;

            // Realizar devolución
            if (biblioteca.devolverLibro(libroISBN, "E001")) { // Usando ID de empleado estático por ahora
                guardadoExitoso = true;
                
                String mensaje = "Devolución realizada exitosamente\n\n";
                if (diasRetraso > 0) {
                    mensaje += "Días de retraso: " + diasRetraso + "\n";
                    mensaje += "Multa a pagar: $" + String.format("%.2f", multa);
                } else {
                    mensaje += "Sin retraso - Multa: $0.00";
                }
                
                JOptionPane.showMessageDialog(this,
                        mensaje,
                        "Devolución completada", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo procesar la devolución. Verifique los datos",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al procesar la devolución: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }

    public double getMulta() {
        int diasRetraso = (Integer) spnDiasRetraso.getValue();
        return diasRetraso * multaPorDia;
    }
}
