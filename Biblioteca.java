package Biblioteca;

import java.util.*;

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
        libros.add(libro);
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(libro);
            }
        }
        return resultados;
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
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
		    Libro libro = null;
		    for (Libro l : libros) {
		        if (l.getIsbn().equals(isbn)) {
		            libro = l;
		            break;
		        }
		    }
		    
		    Usuario usuario = buscarUsuarioPorId(idUsuario);
		    Empleado empleado = empleados.get(idEmpleado);
		
		    if (libro != null && usuario != null && empleado != null && !libro.isPrestado()) {
		        return empleado.procesarPrestamo(libro, usuario);
		    }
		    return false;
		}

    public boolean devolverLibro(String idLibro, String idEmpleado) {
		    Libro libro = null;
		    for (Libro l : libros) {
		        if (l.getIsbn().equals(idLibro)) {
		            libro = l;
		            break;
		        }
		    }
		    
		    Empleado empleado = empleados.get(idEmpleado);
		
		    if (libro != null && empleado != null && libro.isPrestado()) {
		        libro.devolverLibro();
		        empleado.devolverPrestamo();
		        return true;
		    }
		    return false;
		}

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }

    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
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
    public String toString() {
       String estado = "";
        estado += "Biblioteca: " + nombre + "\n";
        estado += "Ubicación: " + ubicacion + "\n";
        estado += "Total de libros: " + libros.size() + "\n";
        estado += "Libros disponibles: " + getLibrosDisponibles().size() + "\n";
        estado += "Libros prestados: " + getLibrosPrestados().size() + "\n";
        estado += "Total de usuarios registrados: " + usuarios.size() + "\n";
        estado += "Total de empleados: " + empleados.size() + "\n";
        
        // Información detallada de libros prestados
        estado += "\nLibros actualmente prestados:\n";
        for (Libro libro : getLibrosPrestados()) {
            estado += "- " + libro.getTitulo() + "\n";
        }
        
        return estado;
    }

}
