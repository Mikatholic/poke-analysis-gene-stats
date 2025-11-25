# Análisis de Pokémons: Generaciones y Estadísticas
## 📝 Descripción General
Este proyecto es una aplicación de consola desarrollada con Java 21 y Spring Boot que consume la PokeAPI para obtener y analizar datos de Pokémon por generación.

La aplicación utiliza la programación funcional moderna de Java (APIs de Stream, Expresiones Lambda) para realizar cálculos estadísticos avanzados, como la suma total de estadísticas (BST) y el cálculo eficiente de promedios, mínimos y máximos mediante DoubleSummaryStatistics.

## 🌐 Integración con la PokeAPI
Este proyecto se basa completamente en la información proporcionada por la PokeAPI (Pokémon API), un recurso RESTful gratuito y abierto.

 - URL Base: https://pokeapi.co/api/v2/

 - Endpoints Clave: La aplicación consulta principalmente los endpoints de listado de Pokémon por límite/offset (para simular generaciones) y el detalle de Pokémon por URL (/pokemon/{id_o_nombre}).

 - Política de Uso Justo: La PokeAPI no requiere autenticación, pero se anima a los desarrolladores a limitar la frecuencia de solicitudes para mantener la disponibilidad del servicio.

 - Implementación: Las llamadas se gestionan en ConsumoAPI.java utilizando el Java HTTP Client, y la deserialización se maneja con la librería Jackson.

## 🚀 Tecnologías Utilizadas
Java 21: Lenguaje principal.

Spring Boot: Framework para gestionar la estructura y el punto de entrada de la aplicación (CommandLineRunner).

Jackson: Biblioteca para la Deserialización de datos JSON a Records de Java (@JsonAlias, @JsonIgnoreProperties).

Java HTTP Client: (Integrado en Java 11+) Usado para realizar peticiones HTTP a la API.

## 🧱 Estructura de Paquetes
El proyecto está modularizado de la siguiente manera:

- com.aluracursos.desafiojtlssf

   - model: Contiene los Records de Java que reflejan la estructura JSON de la PokeAPI (Datos.java, DatosPokemonDetalle.java, etc.).

   - service: Clases de servicio encargadas de la lógica externa:

     - ConsumoAPI: Maneja las peticiones HTTP.

     - ConvierteDatos / IConvierteDatos: Implementa la conversión de JSON a objetos Java, utilizando Generics para la flexibilidad.

   - util: Clases de utilidad, como DatosGeneracionGestor, que mapea los límites y offsets de cada generación.

   - principal: Contiene la clase Principal.java, que gestiona el menú, la lógica de Stream y el análisis de datos.

## ✨ Funcionalidades Clave
La aplicación permite al usuario seleccionar una generación (1 a 9) y realizar los siguientes análisis funcionales sobre los datos obtenidos:

### 1. Carga Paralela de Datos: Utiliza un parallelStream para acelerar la obtención de los detalles de todos los Pokémon de la generación seleccionada.

### 2. Análisis Estadístico Avanzado:

   - Cálculo de Top 5 por peso, altura, y estadísticas base.

   - Cálculo del Promedio de Ataque y Defensa base.

   - Uso de DoubleSummaryStatistics para obtener de forma eficiente el promedio, mínimo y máximo de la altura y el peso en una sola pasada de Stream.

### 3. Filtrado Funcional: Búsqueda rápida de Pokémon por un fragmento de su nombre utilizando la operación .filter() del API de Stream.


## ⚙️ Cómo Ejecutar el Proyecto

### 1. Clonar el Repositorio:

```bash
  git clone [URL_DEL_REPOSITORIO]
```

Ir al directorio del proyecto

```bash
  cd Desafiojtlssf
```

### 2. Construir (Maven/Gradle): 
Asegúrate de tener las dependencias de Spring Boot y Jackson configuradas en tu pom.xml o build.gradle.

### 3. Ejecutar: 
El punto de entrada es la clase DesafiojtlssfApplication.java, que implementa CommandLineRunner.

```bash
# Usando el Wrapper de Maven (si está disponible)
./mvnw spring-boot:run

# O ejecutar directamente el JAR generado
java -jar target/Desafiojtlssf-0.0.1-SNAPSHOT.jar
```

### 4. Interacción: 
La aplicación se iniciará y te pedirá seleccionar una generación del menú de consola.

## Demo

https://github.com/user-attachments/assets/f3f1d9c8-cbde-45d2-8910-22ebfcb01ae8

## Authors

- [@mikatholic](https://github.com/Mikatholic)

