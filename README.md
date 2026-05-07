# Laboratorio 08 - Implementación de Gráficos Básico

Este proyecto implementa un sistema completo de gráficos básicos en Java utilizando Java 2D API, siguiendo exactamente las especificaciones del laboratorio oficial.

## Clases Implementadas

### Gráficos Básicos
- **TestGraficos.java**: Ventana principal para probar gráficos de estadísticas
- **GraficoEstadisticas.java**: Componente Canvas que dibuja gráficos de barras para estadísticas de préstamos
- **TestDiagramaBiblioteca.java**: Ventana para probar el diagrama de biblioteca
- **DiagramaBiblioteca.java**: Componente Canvas que dibuja un diagrama esquemático de una biblioteca

### Mejoras Propuestas
- **IGrafico.java**: Interfaz común para todos los tipos de gráficos
- **GraficoBase.java**: Clase abstracta con funcionalidad compartida
- **GraficoLineal.java**: Implementación de gráficos de líneas con tendencias
- **GraficoPastel.java**: Implementación de gráficos circulares (pastel)
- **TestMejorasGraficos.java**: Ventana para probar las nuevas implementaciones

### Utilidades
- **MainGraficos.java**: Programa principal que lanza todas las ventanas de prueba

## Características Implementadas

### GraficoEstadisticas
- Gráfico de barras verticales
- Escalado automático basado en valores máximos
- Etiquetas de meses en el eje X
- Valores numéricos encima de cada barra
- Ejes X e Y con etiquetas
- Título del gráfico

### DiagramaBiblioteca
- Paredes exteriores de la biblioteca
- 4 estanterías verticales
- 2 cubículos de empleados
- 6 mesas de estudio
- Sillas alrededor de las mesas
- Puerta y ventana
- Etiquetas identificativas
- Leyenda con colores

### Mejoras Implementadas
- Interfaz común IGrafico para estandarización
- Arquitectura extensible con GraficoBase
- Gráfico lineal con puntos de datos
- Gráfico circular (pastel) con porcentajes
- Sistema de colores configurable
- Títulos personalizables

## Cómo Ejecutar

1. Compilar todas las clases:
```bash
javac *.java
```

2. Ejecutar el programa principal:
```bash
java MainGraficos
```

Esto abrirá 3 ventanas:
- Gráfico de Estadísticas (barras)
- Diagrama de Biblioteca
- Mejoras de Gráficos (líneas y pastel)

## Tecnologías Utilizadas

- **Java AWT**: Para componentes de ventana y manejo de eventos
- **Java 2D API**: Para gráficos avanzados con antialiasing
- **Graphics2D**: Para dibujado de formas geométricas
- **RenderingHints**: Para suavizado de bordes
- **BasicStroke**: Para configuración de líneas
- **Shape classes**: Rectangle2D, Arc2D para formas complejas

## Arquitectura

El proyecto sigue una arquitectura de componentes Canvas que extienden la clase base y sobrescriben el método paint() para dibujar gráficos personalizados. Las mejoras incluyen una interfaz común y herencia para facilitar la extensión futura.

## Próximas Mejoras Sugeridas

- Gráfico de dispersión (GraficoDispersion)
- Gráfico radar (GraficoRadar)
- Interactividad con mouse
- Animaciones
- Exportación a imágenes
- Configuración desde archivos