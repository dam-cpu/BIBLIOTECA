import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class BibliotecaGUI extends JFrame {
    
    // Componentes de la interfaz
    private JMenuBar menuBar;
    private JMenu mnuArchivo, mnuCatalogo, mnuPrestamo, mnuUsuarios, mnuAyuda;
    
    private JTextField txtBusqueda;
    private JButton btnBuscar, btnNuevoLibro, btnBuscarLibro, btnEliminarLibro, btnUbicarLibro;
    
    private JPanel pnlBusqueda, pnlAcciones, pnlPrincipal;
    private JTable tablaLibros;
    private JScrollPane scrollTabla;
    private JLabel lblEstado;
    
    // Modelo de datos
    private Biblioteca biblioteca;
    
    public BibliotecaGUI() {
        // Crear instancia de la biblioteca
        biblioteca = new Biblioteca("Biblioteca Central", "Puebla, Puebla");
        
        // Configurar la ventana
        setTitle("Sistema de Biblioteca - " + biblioteca.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Llenar la base con algunos libros de prueba
        llenaBase();
        
        // Crear componentes
        crearMenu();
        crearPanelPrincipal();
        
        // Hacer visible
        setVisible(true);
    }
    
    private void llenaBase() {
        biblioteca.agregarLibro(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498", 863));
        biblioteca.agregarLibro(new Libro("Cien años de soledad", "Gabriel García Márquez", "9780307474728", 417));
        biblioteca.agregarLibro(new Libro("El Principito", "Antoine de Saint-Exupéry", "9788498381498", 96));
        biblioteca.agregarLibro(new Libro("1984", "George Orwell", "9788499790944", 326));
    }
    
    private void crearMenu() {
        // Crear la barra de menús
        menuBar = new JMenuBar();
        
        // Crear menús principales
        mnuArchivo = new JMenu("Archivo");
        mnuCatalogo = new JMenu("Catálogo");
        mnuPrestamo = new JMenu("Préstamo");
        mnuUsuarios = new JMenu("Usuarios");
        mnuAyuda = new JMenu("Ayuda");
        
        // Menú Archivo
        JMenuItem mnuNuevaBiblioteca = new JMenuItem("Nueva Biblioteca");
        JMenuItem mnuAbrirBD = new JMenuItem("Abrir Base de Datos");
        JMenuItem mnuGuardarBD = new JMenuItem("Guardar Base de Datos");
        JMenuItem mnuSalir = new JMenuItem("Salir");
        mnuSalir.addActionListener(e -> System.exit(0));
        
        mnuArchivo.add(mnuNuevaBiblioteca);
        mnuArchivo.add(mnuAbrirBD);
        mnuArchivo.add(mnuGuardarBD);
        mnuArchivo.addSeparator();
        mnuArchivo.add(mnuSalir);
        
        // Menú Catálogo
        JMenuItem mnuNuevoLibro = new JMenuItem("Agregar Libro");
        mnuNuevoLibro.addActionListener(new MostrarDialogoNuevo());
        JMenuItem mnuBuscarLibro = new JMenuItem("Buscar Libro");
        mnuBuscarLibro.addActionListener(e -> new DialogoBusquedaAvanzada(this, biblioteca.getLibros()));
        JMenuItem mnuListarTodos = new JMenuItem("Listar Todos");
        mnuListarTodos.addActionListener(e -> {
            actualizarTablaLibros(biblioteca.getLibros());
            actualizarEstado("Mostrando todos los libros");
        });
        
        mnuCatalogo.add(mnuNuevoLibro);
        mnuCatalogo.add(mnuBuscarLibro);
        mnuCatalogo.addSeparator();
        mnuCatalogo.add(mnuListarTodos);
        
        // Menú Préstamo
        JMenuItem mnuNuevoPrestamo = new JMenuItem("Nuevo Préstamo");
        mnuNuevoPrestamo.addActionListener(e -> {
            DialogoNuevoPrestamo dialog = new DialogoNuevoPrestamo(this, biblioteca);
            if (dialog.isGuardadoExitoso()) {
                actualizarEstado("Préstamo realizado exitosamente");
            }
        });
        
        JMenuItem mnuDevolverPrestamo = new JMenuItem("Devolver Préstamo");
        mnuDevolverPrestamo.addActionListener(e -> {
            DialogoDevolucionPrestamo dialog = new DialogoDevolucionPrestamo(this, biblioteca);
            if (dialog.isGuardadoExitoso()) {
                double multa = dialog.getMulta();
                if (multa > 0) {
                    actualizarEstado("Préstamo devuelto. Multa: $" + String.format("%.2f", multa));
                } else {
                    actualizarEstado("Préstamo devuelto sin multa");
                }
            }
        });
        
        JMenuItem mnuMostrarPrestamos = new JMenuItem("Mostrar Préstamos");
        mnuMostrarPrestamos.addActionListener(e -> new DialogoConsultarPrestamos(this, biblioteca));
        
        mnuPrestamo.add(mnuNuevoPrestamo);
        mnuPrestamo.add(mnuDevolverPrestamo);
        mnuPrestamo.addSeparator();
        mnuPrestamo.add(mnuMostrarPrestamos);
        
        // Menú Usuarios
        JMenuItem mnuRegistrarUsuario = new JMenuItem("Registrar Usuario");
        mnuRegistrarUsuario.addActionListener(e -> {
            DialogoRegistrarUsuario dialog = new DialogoRegistrarUsuario(this);
            if (dialog.isGuardadoExitoso()) {
                Usuario nuevoUsuario = dialog.getUsuario();
                biblioteca.agregarUsuario(nuevoUsuario);
                actualizarEstado("Usuario '" + nuevoUsuario.getNombre() + "' registrado exitosamente");
            }
        });
        
        JMenuItem mnuBuscarUsuario = new JMenuItem("Buscar Usuario");
        mnuBuscarUsuario.addActionListener(e -> new DialogoBuscarUsuario(this, biblioteca));
        
        JMenuItem mnuListarUsuarios = new JMenuItem("Listar Usuarios");
        mnuListarUsuarios.addActionListener(e -> new DialogoListarUsuarios(this, biblioteca));
        
        mnuUsuarios.add(mnuRegistrarUsuario);
        mnuUsuarios.add(mnuBuscarUsuario);
        mnuUsuarios.addSeparator();
        mnuUsuarios.add(mnuListarUsuarios);
        
        // Menú Ayuda
        JMenuItem mnuEstadisticas = new JMenuItem("Estadísticas");
        mnuEstadisticas.addActionListener(e -> new DialogoEstadisticas(this, biblioteca));
        
        JMenuItem mnuAcercaDe = new JMenuItem("Acerca de...");
        mnuAcercaDe.addActionListener(e -> mostrarAcercaDe());
        
        mnuAyuda.add(mnuEstadisticas);
        mnuAyuda.addSeparator();
        mnuAyuda.add(mnuAcercaDe);
        
        // Agregar menús a la barra
        menuBar.add(mnuArchivo);
        menuBar.add(mnuCatalogo);
        menuBar.add(mnuPrestamo);
        menuBar.add(mnuUsuarios);
        menuBar.add(mnuAyuda);
        
        // Establecer la barra de menús en el frame
        setJMenuBar(menuBar);
    }
    
    private void mostrarAcercaDe() {
        JDialog acercaDe = new JDialog(this, "Acerca de Sistema de Biblioteca", true);
        acercaDe.setSize(400, 300);
        acercaDe.setLocationRelativeTo(this);
        acercaDe.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("Sistema de Biblioteca");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel version = new JLabel("Versión 1.0");
        JLabel linea = new JLabel("Laboratorio 10 - POO");
        JLabel equipo = new JLabel("Equipo de Desarrollo");
        
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(version);
        panel.add(linea);
        panel.add(Box.createVerticalStrut(20));
        panel.add(equipo);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> acercaDe.dispose());
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCerrar);
        
        acercaDe.add(panel, BorderLayout.CENTER);
        acercaDe.add(panelBotones, BorderLayout.SOUTH);
        
        acercaDe.setVisible(true);
    }
    
    private void crearPanelPrincipal() {
        // Panel principal con BorderLayout
        pnlPrincipal = new JPanel(new BorderLayout());
        
        // Panel de búsqueda
        pnlBusqueda = new JPanel(new FlowLayout());
        txtBusqueda = new JTextField(30);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new RealizaBusqueda());
        pnlBusqueda.add(txtBusqueda);
        pnlBusqueda.add(btnBuscar);
        
        // Tabla de libros
        String[] columnas = {"Título", "Autor", "ISBN", "Páginas", "Estado"};
        Object[][] datos = {};
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        tablaLibros = new JTable(modelo);
        scrollTabla = new JScrollPane(tablaLibros);
        
        // Panel de acciones
        pnlAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNuevoLibro = new JButton("Nuevo Libro");
        btnNuevoLibro.addActionListener(new MostrarDialogoNuevo());
        btnBuscarLibro = new JButton("Editar");
        btnBuscarLibro.addActionListener(new EditarLibro());
        btnEliminarLibro = new JButton("Eliminar");
        btnEliminarLibro.addActionListener(new EliminarLibro());
        btnUbicarLibro = new JButton("Ubicar Libro");
        btnUbicarLibro.addActionListener(new MostrarUbicacion());
        
        pnlAcciones.add(btnNuevoLibro);
        pnlAcciones.add(btnBuscarLibro);
        pnlAcciones.add(btnEliminarLibro);
        pnlAcciones.add(btnUbicarLibro);
        
        // Barra de estado
        lblEstado = new JLabel(" Listo");
        lblEstado.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Integración final
        pnlPrincipal.add(pnlBusqueda, BorderLayout.NORTH);
        pnlPrincipal.add(scrollTabla, BorderLayout.CENTER);
        pnlPrincipal.add(pnlAcciones, BorderLayout.SOUTH);
        
        this.add(pnlPrincipal, BorderLayout.CENTER);
        this.add(lblEstado, BorderLayout.SOUTH);
        
        // Actualizar tabla con los libros iniciales
        actualizarTablaLibros(biblioteca.getLibros());
    }

    private void actualizarEstado(String mensaje) {
        lblEstado.setText(" " + mensaje);
        BibliotecaLogger.logInfo(mensaje);
    }
    
    private void actualizarTablaLibros(List<Libro> libros) {
        DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();
        modelo.setRowCount(0);
        
        String estado;
        for (Libro libro : libros) {
            if (libro.isPrestado())
                estado = "Prestado";
            else
                estado = "Disponible";
            
            Object[] fila = {
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn(),
                libro.getNumPaginas(),
                estado
            };
            modelo.addRow(fila);
        }
    }
    
    private void mostrarDialogoNuevoLibro() {
        DialogoNuevoLibro dialogo = new DialogoNuevoLibro(this);
        if (dialogo.isGuardadoExitoso()) {
            Libro nuevoLibro = dialogo.getLibro();
            biblioteca.agregarLibro(nuevoLibro);
            actualizarEstado("Catálogo Actualizado");
            actualizarTablaLibros(biblioteca.getLibros());
        }
    }
    
    private void realizaBusquedaRapida() {
        String termino = txtBusqueda.getText().trim();
        if (!termino.isEmpty()) {
            List<Libro> resultados = new ArrayList<>();
            
            // Buscar en Título, Autor e ISBN
            for (Libro libro : biblioteca.getLibros()) {
                if (libro.getTitulo().toLowerCase().contains(termino.toLowerCase()) ||
                    libro.getAutor().toLowerCase().contains(termino.toLowerCase()) ||
                    libro.getIsbn().contains(termino)) {
                    resultados.add(libro);
                }
            }
            
            actualizarTablaLibros(resultados);
            actualizarEstado("Búsqueda rápida completada: " + resultados.size() + " libros encontrados");
        }
    }
    
    private void mostrarDialogoUbicacion() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            String titulo = (String) tablaLibros.getValueAt(filaSeleccionada, 0);
            String autor = (String) tablaLibros.getValueAt(filaSeleccionada, 1);
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
            
            // Crear un nuevo objeto Libro con los datos
            Libro libroSeleccionado = new Libro();
            libroSeleccionado.setTitulo(titulo);
            libroSeleccionado.setAutor(autor);
            libroSeleccionado.setIsbn(isbn);
            
            DialogoUbicacion dialog = new DialogoUbicacion(this, libroSeleccionado);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un libro para ubicar",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Clase interna para manejar evento de búsqueda
    private class RealizaBusqueda implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            realizaBusquedaRapida();
        }
    }
    
    // Clase interna para mostrar diálogo de nuevo libro
    private class MostrarDialogoNuevo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarDialogoNuevoLibro();
        }
    }
    
    // Clase interna para mostrar ubicación del libro
    private class MostrarUbicacion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarDialogoUbicacion();
        }
    }
    
    private void editarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            String titulo = (String) tablaLibros.getValueAt(filaSeleccionada, 0);
            String autor = (String) tablaLibros.getValueAt(filaSeleccionada, 1);
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
            int paginas = (Integer) tablaLibros.getValueAt(filaSeleccionada, 3);
            
            // Crear un objeto Libro con los datos
            Libro libroSeleccionado = new Libro(titulo, autor, isbn, paginas);
            
            DialogoEditarLibro dialog = new DialogoEditarLibro(this, libroSeleccionado);
            
            if (dialog.isGuardadoExitoso()) {
                Libro libroActualizado = dialog.getLibroEditado();
                
                // Buscar y actualizar el libro en la biblioteca
                for (Libro libro : biblioteca.getLibros()) {
                    if (libro.getIsbn().equals(isbn)) {
                        libro.setTitulo(libroActualizado.getTitulo());
                        libro.setAutor(libroActualizado.getAutor());
                        libro.setNumPaginas(libroActualizado.getNumPaginas());
                        break;
                    }
                }
                
                actualizarTablaLibros(biblioteca.getLibros());
                actualizarEstado("Libro actualizado exitosamente");
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un libro para editar",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            String titulo = (String) tablaLibros.getValueAt(filaSeleccionada, 0);
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
            
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar '" + titulo + "'?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                // Buscar y eliminar el libro
                Libro libroAEliminar = null;
                for (Libro libro : biblioteca.getLibros()) {
                    if (libro.getIsbn().equals(isbn)) {
                        libroAEliminar = libro;
                        break;
                    }
                }
                
                if (libroAEliminar != null) {
                    biblioteca.eliminarLibro(libroAEliminar);
                    actualizarTablaLibros(biblioteca.getLibros());
                    actualizarEstado("Libro '" + titulo + "' eliminado");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un libro para eliminar",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Clase interna para editar libro
    private class EditarLibro implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editarLibro();
        }
    }
    
    // Clase interna para eliminar libro
    private class EliminarLibro implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            eliminarLibro();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BibliotecaGUI gui = new BibliotecaGUI();
            // Inicializar el logger al inicio de la aplicación
            try {
                BibliotecaLogger.inicializar();
                BibliotecaLogger.logInfo("Sistema de Biblioteca iniciado correctamente");
            
            } catch (Exception e) {
                System.err.println("Error al inicializar el sistema: " + e.getMessage());
                BibliotecaLogger.logError(e.getMessage(), e);
                System.exit(1);
            }
        });
    }
}