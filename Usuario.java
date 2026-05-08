

import java.util.*;

public class Usuario extends Persona{
    private List<Libro> librosPrestados;
    private Set<String> historialPrestamos;
    private String tipo;

    public Usuario(String nombre, String id) {
        super(nombre, id);
        this.librosPrestados = new ArrayList<>();
        this.historialPrestamos = new HashSet<>();
        this.tipo = "Usuario";
    }

    public Usuario(String id, String nombre, String tipo) {
        super(nombre, id);
        this.librosPrestados = new ArrayList<>();
        this.historialPrestamos = new HashSet<>();
        this.tipo = tipo;
    }
    
    public Usuario(Usuario usuario) {
        super(usuario.getNombre(), usuario.getId());
        this.librosPrestados = usuario.getLibrosPrestados();
        this.historialPrestamos = usuario.getHistorialPrestamos();
    }

    public boolean solicitarPrestamo(Libro libro) {
        if (libro != null && !libro.isPrestado() && libro.prestarLibro()) {
            librosPrestados.add(libro);
            historialPrestamos.add(libro.getIsbn());
            return true;
        }
        return false;
    }

    public boolean devolverLibro(Libro libro) {
        if (librosPrestados.contains(libro)) {
            libro.devolverLibro();
            librosPrestados.remove(libro);
            return true;
        }
        return false;
    }

    /**
     * @deprecated Use getLibrosPrestados() que retorna List<Libro>.
     * Obtiene una copia del primer libro prestado actualmente.
     *
     * @return Una copia del primer libro prestado o null si no hay préstamos activos
     */
    @Deprecated
    public Libro getLibroPrestado() {
        if (!librosPrestados.isEmpty()) {
            return new Libro(librosPrestados.get(0));
        }
        return null;
    }

    public List<Libro> getLibrosPrestados() {
        return new ArrayList<>(librosPrestados);
    }

    public Set<String> getHistorialPrestamos() {
        return new HashSet<>(historialPrestamos);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String obtenerTipo() {
        return tipo;
    }

    public String toString() {
        String cad = "ID: " + getId() + ", " + "Nombre: " + getNombre() + ". ";
        if (librosPrestados.size() > 0)
            cad += "Tiene en préstamo " + librosPrestados.size() + " libros.";
        else
            cad += "No tiene en préstamo un libro.";
        return cad;
    }
    
}
