

public abstract class Persona {
    private String nombre;
    private String id;
    private String email;
    private String telefono;
    public Persona(String nombre, String id) {
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
        this.email = email;
    }
    
    public void setTelefono(String telefono) {
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