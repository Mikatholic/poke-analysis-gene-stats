package com.aluracursos.desafiojtlssf.principal;

import com.aluracursos.desafiojtlssf.model.Datos;
import com.aluracursos.desafiojtlssf.model.DatosPokemonDetalle;
import com.aluracursos.desafiojtlssf.model.DatosGeneracion;
import com.aluracursos.desafiojtlssf.util.DatosGeneracionGestor;
import com.aluracursos.desafiojtlssf.service.ConsumoAPI;
import com.aluracursos.desafiojtlssf.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * Clase principal que gestiona la interacción del usuario,
 * la carga de datos de la API de Pokémon y las funcionalidades de análisis.
 */
public class Principal {

    // URL base de la generación de Pokémon seleccionada por el usuario.
    private String urlGeneracionActual;
    // Instancia para realizar peticiones a la PokeAPI.
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    // Instancia para convertir la respuesta JSON en objetos Java.
    private final ConvierteDatos conversor = new ConvierteDatos();
    // Objeto para leer la entrada del usuario desde la consola.
    private final Scanner teclado = new Scanner(System.in);
    // Lista para almacenar los detalles de todos los Pokémon de la generación actual.
    private List<DatosPokemonDetalle> detallesPokemon = new ArrayList<>();

    /**
     * Muestra el menú inicial para que el usuario seleccione una generación de Pokémon.
     * El bucle continúa hasta que el usuario selecciona '0' para salir.
     */
    public void muestraMenuSeleccionGeneracion() {
        // Obtiene el mapa de todas las generaciones disponibles.
        Map<Integer, DatosGeneracion> generaciones = DatosGeneracionGestor.getGeneraciones();
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- 🌍 Selección de Generación Pokémons ---");
            System.out.println("       ");
            // Itera sobre las generaciones para mostrarlas en el menú.
            generaciones.forEach((key, value) ->
                    System.out.printf("%d - Pokedex %s | #%d a #%d\n",
                            key, value.nombre(), value.offset() + 1, value.offset() + value.limit())
            );
            System.out.println("       ");
            System.out.println("0 - Salir de la aplicación");
            System.out.println("       ");
            System.out.print("Elige un número de generación (1-9) o 0: ");

            try {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Consumir el salto de línea

                if (opcion == 0) {
                    System.out.println("Cerrando la aplicación...");
                    break;
                }

                DatosGeneracion datosGen = generaciones.get(opcion);

                if (datosGen != null) {
                    // Construye la URL para obtener la lista de Pokémon de la generación seleccionada.
                    urlGeneracionActual = String.format("https://pokeapi.co/api/v2/pokemon/?offset=%d&limit=%d",
                            datosGen.offset(), datosGen.limit());

                    detallesPokemon.clear();
                    // Llama al método para cargar los datos de los Pokémon.
                    cargarDetallesPokemon();

                    // Muestra el menú de opciones para la generación cargada.
                    muestraElMenu();

                } else {
                    System.out.println("⚠️ Opción de generación inválida. Inténtalo de nuevo.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("⚠️ Error: Por favor, ingresa un número válido.");
                teclado.nextLine(); // Limpiar el buffer de entrada en caso de error
            }
        }
    }

    /**
     * Carga los detalles completos de todos los Pokémon de la generación actual
     * realizando múltiples llamadas a la API de forma paralela.
     */
    private void cargarDetallesPokemon() {
        // 1. Obtener la lista inicial de nombres y URLs de la generación.
        var json = consumoAPI.obtenerDatos(urlGeneracionActual);
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        DatosGeneracion generacionActual = getDatosGeneracionActual();

        System.out.println("Cargando detalles de " + datos.conteo() + " Pokémons de la " + generacionActual.nombre());

        // 2. Usar un 'parallelStream' para llamar a la API por cada Pokémon simultáneamente.
        detallesPokemon = datos.nombresPokemon().parallelStream()
                .map(pokemon -> {
                    // Llama a la API para obtener el detalle de un Pokémon.
                    var jsonDetalle = consumoAPI.obtenerDatos(pokemon.url());
                    // Convierte el JSON del detalle a un objeto DatosPokemonDetalle.
                    return conversor.obtenerDatos(jsonDetalle, DatosPokemonDetalle.class);
                })
                .collect(Collectors.toList()); // Recolecta los resultados en la lista.

        System.out.println("¡Carga completa de " + detallesPokemon.size() + " Pokémons!\n");
    }

    /**
     * Muestra el menú de funcionalidades disponibles para la generación cargada.
     * Permite al usuario interactuar con los datos de los Pokémon.
     */
    public void muestraElMenu(){
        DatosGeneracion generacionActual = getDatosGeneracionActual();
        int opcion = -1;
        while (opcion != 0) {
            // Usa un bloque de texto formateado para el menú.
            var menu = """
                    \n--- Menú de la Generación %s ---
                    1 - Buscar por nombre 
                    2 - Top 5 más pesados
                    3 - Top 5 más altos
                    4 - Top 5 más ataque base
                    5 - Top 5 más defensa base
                    6 - Top 5 más total de estadísticas base 
                    7 - Promedios de altura y peso
                    8 - Promedio de ataque y defensa base 
                    
                    0 - Volver a Selección de Generación
                    
                    Elige una opción: 
                    """.formatted(generacionActual.nombre());
            System.out.print(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                // Lógica del menú principal (switch-case).
                switch (opcion) {
                    case 1: buscarPokemonPorNombreParcial(); break;
                    case 2: mostrarTopPeso(); break;
                    case 3: mostrarTopAltura(); break;
                    case 4: mostrarTopEstadistica("attack"); break;
                    case 5: mostrarTopEstadistica("defense"); break;
                    case 6: mostrarTopTotalEstadisticas(); break;
                    case 7: mostrarPromedios(); break;
                    case 8: mostrarPromedioAtaqueDefensa(); break;
                    case 0: return; // Regresa al menú de selección de generación.
                    default: System.out.println("Opción inválida.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Por favor, ingresa un número válido.");
                teclado.nextLine();
            }
        }
    }

    /**
     * Determina el objeto DatosGeneracion que corresponde a la URL de carga actual.
     * Esto permite obtener el nombre de la generación para los menús.
     * @return El objeto DatosGeneracion actual, o Kanto (Gen 1) por defecto si no se encuentra.
     */
    private DatosGeneracion getDatosGeneracionActual() {
        for (DatosGeneracion datos : DatosGeneracionGestor.getGeneraciones().values()) {
            // Crea un fragmento de la URL con offset y limit para buscar la coincidencia.
            String urlEsperada = String.format("offset=%d&limit=%d", datos.offset(), datos.limit());
            if (urlGeneracionActual != null && urlGeneracionActual.contains(urlEsperada)) {
                return datos;
            }
        }
        return DatosGeneracionGestor.getGeneraciones().get(1); // Retorna Generación 1 si falla la detección.
    }

    /**
     * Calcula la suma de todas las estadísticas base de un Pokémon (Base Stat Total - BST).
     * @param pokemon El detalle del Pokémon.
     * @return El valor total de las estadísticas base.
     */
    private int calcularTotalEstadisticas(DatosPokemonDetalle pokemon) {
        return pokemon.estadisticas().stream()
                .mapToInt(e -> e.baseStat()) // Mapea cada estadística a su valor base.
                .sum(); // Suma todos los valores base.
    }

    /**
     * Obtiene el valor de una estadística base específica (ej. "attack", "defense").
     * @param pokemon El detalle del Pokémon.
     * @param nombreStat El nombre de la estadística a buscar.
     * @return El valor de la estadística base, o 0 si no se encuentra.
     */
    private double obtenerStatBase(DatosPokemonDetalle pokemon, String nombreStat) {
        return pokemon.estadisticas().stream()
                .filter(s -> s.estadistica().nombre().equalsIgnoreCase(nombreStat)) // Filtra por el nombre de la estadística.
                .mapToInt(s -> s.baseStat())
                .findFirst()
                .orElse(0); // Valor por defecto si no se encuentra la estadística.
    }


    /**
     * Calcula y muestra el promedio general de la suma de ataque y defensa base, dividido por 2.
     */
    private void mostrarPromedioAtaqueDefensa() {
        System.out.println("\n--- ⚔️ Promedio de ataque y defensa base ---");

        // Calcula la suma de los promedios individuales (ataque + defensa) / 2 para cada Pokémon.
        double sumaPromediosIndividuales = detallesPokemon.stream()
                .mapToDouble(p -> {
                    double ataque = obtenerStatBase(p, "attack");
                    double defensa = obtenerStatBase(p, "defense");
                    return (ataque + defensa) / 2.0;
                })
                .sum();

        // Calcula el promedio general.
        double promedioGeneral = sumaPromediosIndividuales / detallesPokemon.size();

        System.out.printf("👉 Promedio General (ataque + defensa) / 2: %.2f\n", promedioGeneral);
        System.out.printf("\n(Calculado sobre %d Pokémons cargados)\n", detallesPokemon.size());
    }

    /**
     * Permite al usuario buscar Pokémon por un fragmento de su nombre
     * y muestra los resultados con su BST, altura y peso.
     */
    private void buscarPokemonPorNombreParcial() {
        System.out.print("\nIngresa parte o el nombre completo del Pokémon a buscar: ");
        String busqueda = teclado.nextLine().toLowerCase();

        // Filtra la lista de Pokémon cuyos nombres contienen el texto de búsqueda.
        List<DatosPokemonDetalle> resultados = detallesPokemon.stream()
                .filter(p -> p.nombre().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            System.out.printf("No se encontró ningún Pokémon que contenga '%s' en su nombre.\n", busqueda);
        } else {
            System.out.printf("\n--- Resultados de búsqueda para '%s' (%d encontrados) ---\n", busqueda, resultados.size());
            // Imprime los detalles de cada Pokémon encontrado.
            resultados.forEach(p -> {
                int bst = calcularTotalEstadisticas(p);
                System.out.printf("  %s (BST: %d): Altura %.2f m, Peso %.2f kg\n",
                        p.nombre().toUpperCase(), bst, p.getAlturaMetros(), p.getPesoKilogramos());
            });
        }
    }

    /**
     * Muestra el Top 5 de los Pokémon más pesados de la generación cargada.
     */
    private void mostrarTopPeso() {
        System.out.println("\n--- ⚖️ Top 5 Pokémons más pesados ---");

        detallesPokemon.stream()
                // Ordena por peso en orden descendente.
                .sorted(Comparator.comparing(DatosPokemonDetalle::getPesoKilogramos).reversed())
                .limit(5) // Limita a los primeros 5.
                .forEach(p ->
                        System.out.printf("  %s - %.2f kg\n", p.nombre().toUpperCase(), p.getPesoKilogramos())
                );
    }

    /**
     * Muestra el Top 5 de los Pokémon más altos de la generación cargada.
     */
    private void mostrarTopAltura() {
        System.out.println("\n--- 🏆 Top 5 Pokémons más altos ---");

        detallesPokemon.stream()
                // Ordena por altura en orden descendente.
                .sorted(Comparator.comparing(DatosPokemonDetalle::getAlturaMetros).reversed())
                .limit(5)
                .forEach(p ->
                        System.out.printf("  %s - %.2f metros\n", p.nombre().toUpperCase(), p.getAlturaMetros())
                );
    }

    /**
     * Muestra el Top 5 de los Pokémon con el valor más alto en una estadística base específica (ataque o defensa).
     * @param nombreStat El nombre de la estadística a analizar ("attack" o "defense").
     */
    private void mostrarTopEstadistica(String nombreStat) {
        // Determina el título a imprimir en función de la estadística.
        String titulo = nombreStat.equals("attack") ? "ataque base" : "defensa base";
        System.out.printf("\n--- ⭐ Top 5 Pokémon más %s ---\n", titulo);

        // Crea un comparador que extrae el valor de la estadística base deseada.
        Comparator<DatosPokemonDetalle> comparadorStat = Comparator.comparing(p ->
                p.estadisticas().stream()
                        .filter(s -> s.estadistica().nombre().equalsIgnoreCase(nombreStat))
                        .mapToInt(s -> s.baseStat())
                        .findFirst()
                        .orElse(0)
        );

        detallesPokemon.stream()
                .sorted(comparadorStat.reversed()) // Ordena en orden descendente.
                .limit(5)
                .forEach(p -> {
                    // Extrae el valor de la estadística base nuevamente para la impresión.
                    int valorStat = p.estadisticas().stream()
                            .filter(s -> s.estadistica().nombre().equalsIgnoreCase(nombreStat))
                            .mapToInt(s -> s.baseStat())
                            .findFirst()
                            .orElse(0);
                    System.out.printf("  %s - %d de %s\n", p.nombre().toUpperCase(), valorStat, titulo);
                });
    }

    /**
     * Muestra el Top 5 de los Pokémon con el mayor total de estadísticas base (BST).
     */
    private void mostrarTopTotalEstadisticas() {
        System.out.println("\n--- 💎 Top 5 Pokémon más total de estadísticas base ---");

        detallesPokemon.stream()
                // Ordena usando el método 'calcularTotalEstadisticas' como criterio de comparación.
                .sorted(Comparator.comparing(this::calcularTotalEstadisticas).reversed())
                .limit(5)
                .forEach(p -> {
                    int total = calcularTotalEstadisticas(p);
                    System.out.printf("  %s - Total: %d\n", p.nombre().toUpperCase(), total);
                });
    }

    /**
     * Calcula y muestra los promedios de altura y peso de todos los Pokémon cargados.
     */
    private void mostrarPromedios() {
        System.out.println("\n--- 📏 Promedios de la muestra (" + detallesPokemon.size() + " Pokémons) ---");

        // Calcula el promedio de altura en metros.
        double promedioAltura = detallesPokemon.stream()
                .mapToDouble(DatosPokemonDetalle::getAlturaMetros)
                .average() // Método para calcular el promedio de un DoubleStream.
                .orElse(0.0);

        // Calcula el promedio de peso en kilogramos.
        double promedioPeso = detallesPokemon.stream()
                .mapToDouble(DatosPokemonDetalle::getPesoKilogramos)
                .average()
                .orElse(0.0);

        System.out.printf("👉 Promedio de altura: %.2f metros\n", promedioAltura);
        System.out.printf("👉 Promedio de peso:   %.2f kilogramos\n", promedioPeso);
        System.out.printf("\n(Calculado sobre %d Pokémons cargados)\n", detallesPokemon.size());
    }
}