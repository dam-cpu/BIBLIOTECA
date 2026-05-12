import java.util.*;

public class Empleado extends Persona {
    private String numeroEmpleado;
    private String puesto;
    private double salario;
    private int turno;
    private Queue<Prestamo> prestamosEnProceso;
    private List<Prestamo> historialPrestamos;
    private static int contadorId = 0;
    public static final int MATUTINO = 0;
    public static final int VESPERTINO = 1;
    public static final int MIXTO = 2;

    public Empleado(String nombre, String id, String numeroEmpleado, String puesto) {
        super(nombre, id);
        this.numeroEmpleado = numeroEmpleado;
        setPuesto(puesto);
        this.prestamosEnProceso = new LinkedList<>();
        this.historialPrestamos = new ArrayList<>();
    }

    public Empleado(String id, String nombre, String puesto) {
        super(nombre, id);
        this.numeroEmpleado = id;
        setPuesto(puesto);
        this.prestamosEnProceso = new LinkedList<>();
        this.historialPrestamos = new ArrayList<>();
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        if (puesto == null || puesto.trim().isEmpty()) {
            throw new IllegalArgumentException("El puesto no puede ser null o vacío");
        }
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario < 0) {
            throw new IllegalArgumentException("El salario no puede ser negativo");
        }
        this.salario = salario;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        if (turno != MATUTINO && turno != VESPERTINO && turno != MIXTO) {
            throw new IllegalArgumentException("Turno inválido. Debe ser MATUTINO, VESPERTINO o MIXTO");
        }
        this.turno = turno;
    }

    public Queue<Prestamo> getPrestamosEnProceso() {
        return new LinkedList<>(prestamosEnProceso);
    }

    public List<Prestamo> getHistorialPrestamos() {
        return new ArrayList<>(historialPrestamos);
    }

    public String obtenerTipo() {
        return "Empleado";
    }

    public static String generarId() {
        contadorId++;
        return "P" + String.format("%04d", contadorId);
    }

    public boolean procesarPrestamo(Libro libro, Usuario usuario) {
        if (libro != null && usuario != null && !libro.isPrestado()) {
            if (usuario.solicitarPrestamo(libro)) {
                Prestamo nuevoPrestamo = new Prestamo(generarId(), usuario, libro);
                prestamosEnProceso.offer(nuevoPrestamo);
                historialPrestamos.add(nuevoPrestamo);
                return true;
            }
        }
        return false;
    }

    public boolean devolverPrestamo() {
        if (!prestamosEnProceso.isEmpty()) {
            prestamosEnProceso.poll();
            return true;
        }
        return false;
    }

    public String toString() {
        return "Empleado [puesto=" + puesto +
               ", salario=" + salario +
               ", turno=" + turno +
               ", prestamosEnProceso=" + prestamosEnProceso.size() +
               ", historialPrestamos=" + historialPrestamos.size() +
               ", nombre=" + getNombre() +
               ", id=" + getId() + "]";
    }
}