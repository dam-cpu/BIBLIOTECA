# Laboratorio 11 - Implementación de Excepciones

Este proyecto implementa un sistema robusto de manejo de excepciones para el sistema de biblioteca, incluyendo un logger de eventos, excepciones personalizadas y validaciones en las clases del modelo y la lógica de negocio.

## Clases Implementadas

### Logger
- **BibliotecaLogger.java**: Sistema de registro de eventos usando `java.util.logging`. Escribe en `biblioteca.log` y provee métodos estáticos para registrar errores, advertencias e información.

### Excepciones Personalizadas
- **LibroNoDisponibleException.java**: Lanzada cuando un libro no existe o ya está prestado
- **UsuarioSuspendidoException.java**: Lanzada cuando se intenta operar con un usuario suspendido
- **LimitePrestamosException.java**: Lanzada cuando el usuario excede su límite de préstamos
- **UsuarioNoEncontradoException.java**: Lanzada cuando no se encuentra un usuario por ID
- **EmpleadoNoEncontradoException.java**: Lanzada cuando no se encuentra un empleado por ID
- **PrestamoInvalidoException.java**: Lanzada cuando los datos de un préstamo son inválidos
- **LibroNoRegistradoException.java**: Lanzada cuando el ISBN no existe en el sistema
- **EstadoInvalidoException.java**: Lanzada cuando un libro no está en el estado esperado
- **DuplicadoException.java**: Lanzada al intentar agregar un libro con ISBN ya existente
- **OperacionDenegadaException.java**: Lanzada cuando no se tienen permisos para una operación
- **ValidacionDatosException.java**: Lanzada cuando los datos de un usuario son inválidos
- **UsuarioExistenteException.java**: Lanzada al intentar registrar un usuario duplicado
- **DependenciasActivasException.java**: Lanzada al intentar eliminar un usuario con préstamos activos

### Clases del Modelo Modificadas
- **Persona.java**: Validaciones en constructor (`nombre` e `id` no nulos), `setEmail()` con regex y `setTelefono()` con validación de 10 dígitos
- **Usuario.java**: Validaciones en `solicitarPrestamo()` y `devolverLibro()`, atributos `suspendido` y `limitePrestamos` con sus getters y setters
- **Empleado.java**: Validaciones en `setSalario()` (no negativo), `setPuesto()` (no vacío) y `setTurno()` (solo MATUTINO, VESPERTINO o MIXTO)

### Sistema de Logger
- Escritura en archivo `biblioteca.log` con modo append
- Formato legible con `SimpleFormatter`
- Tres niveles de log: `logInfo()`, `logWarning()`, `logError()`
- Inicialización con manejo de `IOException`
- Registro automático de todos los eventos de la interfaz

### Excepciones Personalizadas
- 13 clases de excepción personalizadas que extienden `Exception`
- Cada una recibe un mensaje descriptivo en el constructor
- Organizadas por área: catálogo, usuarios, empleados y operaciones

### Validaciones en el Modelo
- Constructor de `Persona` valida nombre e ID no nulos ni vacíos
- `setEmail()` valida formato con regex `^[A-Za-z0-9+_.-]+@(.+)$`
- `setTelefono()` valida exactamente 10 dígitos
- `setSalario()` rechaza valores negativos
- `setTurno()` solo acepta las constantes `MATUTINO`, `VESPERTINO` o `MIXTO`

### Puntos de Implementación Try-Catch
- `prestarLibro()`: valida libro, usuario, suspensión, límite de préstamos y empleado
- `devolverLibro()`: valida ISBN registrado y estado del libro
- `buscarUsuarioPorId()`: valida ID no nulo y existencia del usuario
- `agregarLibro()` / `eliminarLibro()`: valida nulos, duplicados y permisos
- `agregarUsuario()` / `eliminarUsuario()`: valida datos, duplicados y dependencias activas

### Operaciones de Archivo
- `registrarOperacion()` usa try-with-resources con `BufferedWriter` para escribir en `operaciones.log`
- `cargarConfiguracion()` usa try-with-resources con `BufferedReader` para leer archivos de configuración
- Manejo de `FileNotFoundException` e `IOException` con log de errores

## Cómo Ejecutar

1. Compilar todas las clases:
```bash
javac *.java
```

2. Ejecutar el programa principal:
```bash
java BibliotecaGUI
```

Esto abrirá la ventana principal del sistema de biblioteca. Al iniciar se crearán automáticamente los archivos de log:
- `biblioteca.log`: registro de eventos del sistema
- `operaciones.log`: registro de préstamos y devoluciones

## Tecnologías Utilizadas

- **java.util.logging**: Para el sistema de registro de eventos (Logger, FileHandler, SimpleFormatter)
- **java.io**: Para operaciones de archivo con BufferedWriter, BufferedReader y FileWriter
- **Try-with-resources**: Para manejo automático del cierre de recursos de archivo
- **Excepciones checked**: Todas las excepciones personalizadas extienden `Exception`
- **IllegalArgumentException / NullPointerException**: Para validaciones de parámetros de entrada