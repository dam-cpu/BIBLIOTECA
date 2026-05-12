public abstract class Persona {
    private String nombre;
    private String id;
    private String email;
    private String telefono;

    public Persona(String nombre, String id) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        this.nombre = nombre;
        this.id = id;
        this.email = "usuario@servidor.com";
        this.telefono = "0000000000";
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return new String(this.email);
    }

    public String getTelefono() {
        return new String(this.telefono);
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        this.email = email;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("\\d{10}")) {
            throw new IllegalArgumentException("El teléfono debe tener 10 dígitos");
        }
        this.telefono = telefono;
    }

    public abstract String obtenerTipo();

    public String toString() {
        return "Nombre: " + nombre + " ID: " + id + "\nEmail: " + email + "\nTeléfono: " + telefono + "\nTipo: " + obtenerTipo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Persona persona = (Persona) obj;
        return id != null && id.equals(persona.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}