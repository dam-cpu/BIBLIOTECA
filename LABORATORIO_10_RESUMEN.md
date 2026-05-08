# Laboratorio 10: Sistema de Biblioteca - Interfaz Gráfica

## Resumen de Implementación Completada

### Componentes Principales Implementados

#### 1. **BibliotecaGUI.java** - Ventana Principal
- **Características implementadas:**
  - Ventana principal (JFrame) de 800x600 píxeles
  - Barra de menús completa con 5 menús: Archivo, Catálogo, Préstamo, Usuarios, Ayuda
  - Panel de búsqueda con campo de texto y botón "Buscar"
  - Tabla de libros con columnas: Título, Autor, ISBN, Páginas, Estado
  - Panel de acciones con botones: Nuevo Libro, Editar, Eliminar, Ubicar Libro
  - Barra de estado con mensajes informativos
  - Integración MVC con clase Biblioteca como modelo

#### 2. **DialogoNuevoLibro.java** - Diálogo para Agregar Libros
- **Características implementadas:**
  - Formulario modal con GridBagLayout
  - Campos de entrada: Título, Autor, ISBN, Editorial, Páginas (spinner)
  - Validación completa de campos requeridos
  - Validación de formato ISBN (10 o 13 dígitos)
  - Botones Guardar y Cancelar
  - Métodos getter para obtener libro y estado de guardado

#### 3. **DialogoUbicacion.java** - Visualización de Ubicación de Libro
- **Características implementadas:**
  - Diálogo que muestra el mapa de la biblioteca
  - Extrae últimos 3 dígitos del ISBN para determinar ubicación
  - Muestra información del libro (Título, ISBN, Número de Librero)
  - Integración con DiagramaBiblioteca para visualización gráfica

#### 4. **DiagramaBiblioteca.java** - Visualización Gráfica (Modificada)
- **Características nuevas:**
  - Método `setLibroUbicacion(int ubicacion)` para marcar ubicación
  - Método `marcarUbicacion()` que dibuja una X roja en la estantería
  - Sistema de rango de números para ubicar libros en estantes específicos

#### 5. **Biblioteca.java** - Modelo de Datos (Modificada)
- **Método agregado:**
  - `getLibros()` - Devuelve la lista completa de libros

### Funcionalidades Implementadas

#### Menús Completos
- **Archivo:** Nueva Biblioteca, Abrir BD, Guardar BD, Salir
- **Catálogo:** Agregar Libro, Buscar Libro, Listar Todos
- **Préstamo:** Nuevo Préstamo, Devolver Préstamo, Mostrar Préstamos
- **Usuarios:** Registrar Usuario, Buscar Usuario, Listar Usuarios
- **Ayuda:** Acerca de...

#### Búsqueda Rápida Mejorada
- Busca en Título, Autor e ISBN simultáneamente
- Búsqueda case-insensitive para Título y Autor
- Actualiza tabla y barra de estado en tiempo real

#### Diálogo "Acerca de"
- Ventana modal que muestra:
  - Nombre de la aplicación: "Sistema de Biblioteca"
  - Versión 1.0
  - Identificación del laboratorio: "Laboratorio 10 - POO"
  - Mención de equipo de desarrollo

#### Casos de Uso Implementados
1. **Agregar Nuevo Libro** - Completamente funcional con diálogo, validación y actualización
2. **Busca Rápida de Libro** - Con búsqueda por Título, Autor e ISBN
3. **Ubicar Libro** - Visualización gráfica de ubicación en estantería
4. **Listar Todos los Libros** - Opción en menú Catálogo

### Características Técnicas

- **Patrón MVC:** Separación clara entre modelo (Biblioteca), vista (BibliotecaGUI) y controlador (listeners)
- **GridBagLayout:** Usado en DialogoNuevoLibro para organización precisa de componentes
- **BorderLayout:** Usado en BibliotecaGUI para distribución de paneles
- **FlowLayout:** Usado en paneles de búsqueda y acciones
- **DefaultTableModel:** Para gestión dinámica de datos en tabla
- **Validación de datos:** Campos requeridos e ISBN con formato específico
- **Encoding UTF-8:** Compilación con soporte a caracteres acentuados

### Datos de Prueba

La aplicación se precarga con 4 libros de ejemplo:
1. Don Quijote de la Mancha - Miguel de Cervantes
2. Cien años de soledad - Gabriel García Márquez
3. El Principito - Antoine de Saint-Exupéry
4. 1984 - George Orwell

### Cómo Ejecutar

```bash
# Compilar
javac -encoding UTF-8 *.java

# Ejecutar
java BibliotecaGUI
```

### Versiones y Laboratorios Anteriores

Esta implementación se basa en:
- **Laboratorio 08:** Gráficos Java 2D (DiagramaBiblioteca)
- **Laboratorio 09:** Casos de uso y diseño (estructuras de casos de uso)
- **Laboratorio 07:** Colecciones de objetos (uso de List, Map, Set)

### Mejoras Futuras Sugeridas

1. Implementar casos de uso adicionales (Nuevo Préstamo, Devolución, etc.)
2. Agregar dialogo de búsqueda avanzada con checkboxes para criterios
3. Integrar base de datos persistente
4. Implementar sistema de multas por retraso
5. Agregar reportes y estadísticas visuales
6. Mejorar interfaz con temas visuales