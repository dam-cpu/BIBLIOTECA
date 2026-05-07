package Biblioteca;

public class Main {
    public static void main(String[] args) {
		 // Crear una nueva biblioteca
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central", "Av. Universidad 3000");
        
        // Crear y agregar empleados
        Empleado emp1 = new Empleado("E001", "Juan Pérez", "Bibliotecario");
        Empleado emp2 = new Empleado("E002", "María García", "Asistente");
        biblioteca.agregarEmpleado(emp1);
        biblioteca.agregarEmpleado(emp2);
        
        // Crear y agregar libros
        Libro libro1 = new Libro("Don Quijote", "Miguel de Cervantes", "9788423919");
        Libro libro2 = new Libro("Crimen y Castigo", "Fiódor Dostoyevski", "9780140449");
        Libro libro3 = new Libro("Cien años de soledad", "Gabriel García Márquez", "9788420674");
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarLibro(libro3);
        
        // Crear y agregar usuarios
        Usuario user1 = new Usuario("U001", "Ana López", "Estudiante");
        Usuario user2 = new Usuario("U002", "Carlos Ruiz", "Profesor");
        biblioteca.agregarUsuario(user1);
        biblioteca.agregarUsuario(user2);
        
        // Probar búsqueda de libros
        System.out.println("Búsqueda de libros con 'don':");
        for (Libro libro : biblioteca.buscarLibrosPorTitulo("don")) {
            System.out.println(libro.getTitulo());
        }
        
        // Probar préstamo de libro
        System.out.println("\nProbando préstamo de libro:");
        if (biblioteca.prestarLibro("9788423919", "U001", "E001")) {
            System.out.println("Préstamo realizado con éxito");
        } else {
            System.out.println("No se pudo realizar el préstamo");
        }

         System.out.println("\nProbando préstamo de libro:");
        if (biblioteca.prestarLibro("9780140449", "U001", "E001")) {
            System.out.println("Préstamo realizado con éxito");
        } else {
            System.out.println("No se pudo realizar el préstamo");
        }
        
        // Mostrar libros prestados
        System.out.println("\nLibros prestados:");
        for (Libro libro : biblioteca.getLibrosPrestados()) {
            System.out.println(libro.getTitulo());
        }
        
        // Probar devolución de libro
        System.out.println("\nProbando devolución de libro:");
        if (biblioteca.devolverLibro("9788423919", "E001")) {
            System.out.println("Devolución realizada con éxito");
        } else {
            System.out.println("No se pudo realizar la devolución");
        }
        
        // Mostrar estado final de la biblioteca
        System.out.println("\nEstado final de la biblioteca:");
        System.out.println(biblioteca.toString());
    }
        
}
