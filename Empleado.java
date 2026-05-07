package Biblioteca;

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
        this.puesto = puesto;
        this.prestamosEnProceso = new LinkedList<>();
        this.historialPrestamos = new ArrayList<>();
    }

    public Empleado(String id, String nombre, String puesto) {
        super(nombre, id);
        this.numeroEmpleado = id;
        this.puesto = puesto;
        this.prestamosEnProceso = new LinkedList<>();
        this.historialPrestamos = new ArrayList<>();
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario > 0 ? salario : 0;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
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
