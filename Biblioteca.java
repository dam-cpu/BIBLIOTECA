import java.util.*;
import java.io.*;

public class Biblioteca {
    private String nombre;
    private String ubicacion;
    private List<Libro> libros;
    private Set<Usuario> usuarios;
    private Map<String, Empleado> empleados;

    public Biblioteca(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.libros = new ArrayList<>();
        this.usuarios = new HashSet<>();
        this.empleados = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void agregarLibro(Libro libro) {
        try {
            if (libro == null) {
                throw new NullPointerException("El libro no puede ser null");
            }
            for (Libro l : libros) {
                if (l.getIsbn().equals(libro.getIsbn())) {
                    throw new DuplicadoException("Ya existe un libro con el ISBN: " + libro.getIsbn());
                }
            }
            libros.add(libro);
            BibliotecaLogger.logInfo("Libro agregado: " + libro.getIsbn());
        } catch (NullPointerException e) {
            BibliotecaLogger.logError("Error al agregar libro: parámetro nulo", e);
        } catch (DuplicadoException e) {
            BibliotecaLogger.logWarning("Error al agregar libro: " + e.getMessage());
        }
    }

    public void eliminarLibro(Libro libro) {
        try {
            if (libro == null) {
                throw new NullPointerException("El libro no puede ser null");
            }
            if (libro.isPrestado()) {
                throw new OperacionDenegadaException("No se puede eliminar un libro que está prestado: " + libro.getIsbn());
            }
            libros.remove(libro);
            BibliotecaLogger.logInfo("Libro eliminado: " + libro.getIsbn());
        } catch (NullPointerException e) {
            BibliotecaLogger.logError("Error al eliminar libro: parámetro nulo", e);
        } catch (OperacionDenegadaException e) {
            BibliotecaLogger.logWarning("Operación denegada: " + e.getMessage());
        }
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        try {
            if (titulo == null) {
                throw new NullPointerException("El título de búsqueda no puede ser null");
            }
            List<Libro> resultados = new ArrayList<>();
            for (Libro libro : libros) {
                if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                    resultados.add(libro);
                }
            }
            if (resultados.isEmpty()) {
                throw new LibroNoDisponibleException("No se encontró ningún libro con el título: " + titulo);
            }
            return resultados;
        } catch (NullPointerException e) {
            BibliotecaLogger.logError("Error al acceder al catálogo", e);
            return new ArrayList<>();
        } catch (LibroNoDisponibleException e) {
            BibliotecaLogger.logWarning(e.getMessage());
            return new ArrayList<>();
        }
    }

    private Libro buscarLibroPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorLibro(Libro libro) {
        for (Usuario usuario : usuarios) {
            for (Libro prestado : usuario.getLibrosPrestados()) {
                if (prestado.getIsbn().equals(libro.getIsbn())) {
                    return usuario;
                }
            }
        }
        return null;
    }

    public List<Libro> getLibrosPrestados() {
        List<Libro> prestados = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.isPrestado()) {
                prestados.add(libro);
            }
        }
        return prestados;
    }

    public boolean prestarLibro(String isbn, String idUsuario, String idEmpleado) {
        try {
            if (isbn == null || idUsuario == null || idEmpleado == null) {
                throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
            }

            Libro libro = null;
            for (Libro l : libros) {
                if (l.getIsbn().equals(isbn)) {
                    libro = l;
                    break;
                }
            }
            if (libro == null) {
                throw new LibroNoDisponibleException("El libro con ISBN " + isbn + " no existe en la biblioteca");
            }
            if (libro.isPrestado()) {
                throw new LibroNoDisponibleException("El libro con ISBN " + isbn + " ya está prestado");
            }

            Usuario usuario = buscarUsuarioPorId(idUsuario);
            if (usuario == null) {
                throw new UsuarioNoEncontradoException("Usuario con ID " + idUsuario + " no encontrado");
            }
            if (usuario.estaSuspendido()) {
                throw new UsuarioSuspendidoException("El usuario " + idUsuario + " está suspendido");
            }
            if (usuario.getPrestamosActuales() >= usuario.getLimitePrestamos()) {
                throw new LimitePrestamosException("El usuario ha excedido su límite de préstamos");
            }

            Empleado empleado = empleados.get(idEmpleado);
            if (empleado == null) {
                throw new EmpleadoNoEncontradoException("Empleado con ID " + idEmpleado + " no encontrado");
            }

            boolean resultado = empleado.procesarPrestamo(libro, usuario);
            if (resultado) {
                BibliotecaLogger.logInfo("Préstamo exitoso: Libro " + isbn + " al usuario " + idUsuario);
                registrarOperacion("PRESTAMO: ISBN=" + isbn + " Usuario=" + idUsuario);
            }
            return resultado;

        } catch (IllegalArgumentException e) {
            BibliotecaLogger.logError("Error de validación en préstamo", e);
            return false;
        } catch (LibroNoDisponibleException | UsuarioSuspendidoException |
                 LimitePrestamosException | UsuarioNoEncontradoException |
                 EmpleadoNoEncontradoException e) {
            BibliotecaLogger.logWarning("Error en el préstamo: " + e.getMessage());
            return false;
        } catch (Exception e) {
            BibliotecaLogger.logError("Error inesperado en préstamo", e);
            return false;
        }
    }

    public boolean devolverLibro(String idLibro, String idEmpleado) {
        try {
            if (idLibro == null || idEmpleado == null) {
                throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
            }

            Libro libro = null;
            for (Libro l : libros) {
                if (l.getIsbn().equals(idLibro)) {
                    libro = l;
                    break;
                }
            }
            if (libro == null) {
                throw new LibroNoRegistradoException("El libro con ISBN " + idLibro + " no está registrado");
            }
            if (!libro.isPrestado()) {
                throw new EstadoInvalidoException("El libro con ISBN " + idLibro + " no está marcado como prestado");
            }

            Empleado empleado = empleados.get(idEmpleado);
            if (empleado == null) {
                throw new EmpleadoNoEncontradoException("Empleado con ID " + idEmpleado + " no encontrado");
            }

            libro.devolverLibro();
            empleado.devolverPrestamo();
            BibliotecaLogger.logInfo("Devolución exitosa: Libro " + idLibro);
            registrarOperacion("DEVOLUCION: ISBN=" + idLibro);
            return true;

        } catch (IllegalArgumentException e) {
            BibliotecaLogger.logError("Error de validación en devolución", e);
            return false;
        } catch (LibroNoRegistradoException | EstadoInvalidoException |
                 EmpleadoNoEncontradoException e) {
            BibliotecaLogger.logWarning("Error en la devolución: " + e.getMessage());
            return false;
        } catch (Exception e) {
            BibliotecaLogger.logError("Error inesperado en devolución", e);
            return false;
        }
    }

    public void agregarUsuario(Usuario usuario) {
        try {
            if (usuario == null) {
                throw new ValidacionDatosException("El usuario no puede ser null");
            }
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty() ||
                usuario.getId() == null || usuario.getId().trim().isEmpty()) {
                throw new ValidacionDatosException("El usuario tiene datos inválidos (nombre o ID vacío)");
            }
            for (Usuario u : usuarios) {
                if (u.getId().equals(usuario.getId())) {
                    throw new UsuarioExistenteException("Ya existe un usuario con el ID: " + usuario.getId());
                }
            }
            usuarios.add(usuario);
            BibliotecaLogger.logInfo("Usuario agregado: " + usuario.getId());
        } catch (ValidacionDatosException e) {
            BibliotecaLogger.logError("Error de validación al agregar usuario", e);
        } catch (UsuarioExistenteException e) {
            BibliotecaLogger.logWarning("Usuario duplicado: " + e.getMessage());
        }
    }

    public void eliminarUsuario(Usuario usuario) {
        try {
            if (usuario == null) {
                throw new ValidacionDatosException("El usuario no puede ser null");
            }
            if (usuario.getPrestamosActuales() > 0) {
                throw new DependenciasActivasException("No se puede eliminar al usuario " +
                    usuario.getId() + " porque tiene " + usuario.getPrestamosActuales() + " préstamos activos");
            }
            usuarios.remove(usuario);
            BibliotecaLogger.logInfo("Usuario eliminado: " + usuario.getId());
        } catch (ValidacionDatosException e) {
            BibliotecaLogger.logError("Error de validación al eliminar usuario", e);
        } catch (DependenciasActivasException e) {
            BibliotecaLogger.logWarning("No se puede eliminar usuario: " + e.getMessage());
        }
    }

    public Usuario buscarUsuarioPorId(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("El ID de usuario no puede ser null o vacío");
            }
            for (Usuario usuario : usuarios) {
                if (usuario.getId().equals(id)) {
                    return usuario;
                }
            }
            throw new UsuarioNoEncontradoException("Usuario con ID " + id + " no encontrado");
        } catch (IllegalArgumentException e) {
            BibliotecaLogger.logError("Error de validación en búsqueda de usuario", e);
            return null;
        } catch (UsuarioNoEncontradoException e) {
            BibliotecaLogger.logWarning(e.getMessage());
            return null;
        }
    }

    private void registrarOperacion(String operacion) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("operaciones.log", true))) {
            writer.write(new java.util.Date() + " - " + operacion);
            writer.newLine();
        } catch (FileNotFoundException e) {
            BibliotecaLogger.logError("No se encontró el archivo de operaciones", e);
        } catch (IOException e) {
            BibliotecaLogger.logError("Error al escribir en el archivo de operaciones", e);
        }
    }

    public void cargarConfiguracion(String rutaArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                BibliotecaLogger.logInfo("Configuración cargada: " + linea);
            }
        } catch (FileNotFoundException e) {
            BibliotecaLogger.logError("Archivo de configuración no encontrado: " + rutaArchivo, e);
        } catch (IOException e) {
            BibliotecaLogger.logError("Error al leer el archivo de configuración", e);
        }
    }

    public void agregarEmpleado(Empleado empleado) {
        empleados.put(empleado.getId(), empleado);
    }

    public void eliminarEmpleado(String id) {
        empleados.remove(id);
    }

    public Empleado obtenerEmpleado(String id) {
        return empleados.get(id);
    }

    public boolean prestarLibro(Libro libro, Usuario usuario, Empleado empleado) {
        if (libro != null && usuario != null && empleado != null && !libro.isPrestado()) {
            return empleado.procesarPrestamo(libro, usuario);
        }
        return false;
    }

    public boolean devolverLibro(Libro libro, Usuario usuario, Empleado empleado) {
        if (libro != null && usuario != null && empleado != null && libro.isPrestado()) {
            if (usuario.devolverLibro(libro)) {
                empleado.devolverPrestamo();
                return true;
            }
        }
        return false;
    }

    public List<Libro> getLibrosDisponibles() {
        List<Libro> disponibles = new ArrayList<>();
        for (Libro libro : libros) {
            if (!libro.isPrestado()) {
                disponibles.add(libro);
            }
        }
        return disponibles;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Empleado> getEmpleados() {
        return new ArrayList<>(empleados.values());
    }

    public String toString() {
        String estado = "";
        estado += "Biblioteca: " + nombre + "\n";
        estado += "Ubicación: " + ubicacion + "\n";
        estado += "Total de libros: " + libros.size() + "\n";
        estado += "Libros disponibles: " + getLibrosDisponibles().size() + "\n";
        estado += "Libros prestados: " + getLibrosPrestados().size() + "\n";
        estado += "Total de usuarios registrados: " + usuarios.size() + "\n";
        estado += "Total de empleados: " + empleados.size() + "\n";

        estado += "\nLibros actualmente prestados:\n";
        for (Libro libro : getLibrosPrestados()) {
            estado += "- " + libro.getTitulo() + "\n";
        }

        return estado;
    }
}