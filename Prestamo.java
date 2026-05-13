import java.time.LocalDate;

public class Prestamo {
    private String id;
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private int estado; 
    public static final int ACTIVO = 0;
    public static final int DEVUELTO = 1;
    public static final int VENCIDO = 2;
    public Prestamo(String id, Usuario usuario, Libro libro) {
				this.id = id;
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEsperada = fechaPrestamo.plusDays(14);
        this.fechaDevolucionReal = null;
        this.estado = ACTIVO;
    }
    public String getId() { 
        return id; 
    }
    
    public Usuario getUsuario() { 
        return new Usuario(usuario); 
    }
    
    public Libro getLibro() { 
        return new Libro(libro); 
    }
    
    public LocalDate getFechaPrestamo() { 
        return fechaPrestamo; 
    }
    
    public LocalDate getFechaDevolucionEsperada() { 
        return fechaDevolucionEsperada; 
    }
    
    public LocalDate getFechaDevolucionReal() { 
        return fechaDevolucionReal; 
    }
    
    public int getEstado() { 
        return estado; 
    }
    public boolean registrarPrestamo() {
        if (libro == null || libro.isPrestado()) {
            return false;
        }
        if (usuario.solicitarPrestamo(libro)) {
            estado = ACTIVO;
            return true;
        }
        return false;
    }

    
public boolean procesarDevolucion() {
    if (estado == ACTIVO) {
        fechaDevolucionReal = LocalDate.now();
        libro.devolverLibro();
        if (usuario.devolverLibro(libro)) {
            estado = DEVUELTO;
            return true;
        }
        libro.prestarLibro();
    }
    return false;
}
    public void verificarEstado() {
        if (estado == ACTIVO && LocalDate.now().isAfter(fechaDevolucionEsperada)) {
            estado = VENCIDO;
        }
    }
    
    public boolean extenderPrestamo(int dias) {
        if (estado == ACTIVO && !LocalDate.now().isAfter(fechaDevolucionEsperada)) {
            fechaDevolucionEsperada = fechaDevolucionEsperada.plusDays(dias);
            return true;
        }
        return false;
    }
    


}
