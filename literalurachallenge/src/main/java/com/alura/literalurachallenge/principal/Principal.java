package com.alura.literalurachallenge.principal;

import com.alura.literalurachallenge.model.Autor;
import com.alura.literalurachallenge.model.DatosLibro;
import com.alura.literalurachallenge.model.DatosResultados;
import com.alura.literalurachallenge.model.Libro;
import com.alura.literalurachallenge.repository.AutorRepository;
import com.alura.literalurachallenge.repository.LibroRepository;
import com.alura.literalurachallenge.service.ConvertirDatos;
import com.alura.literalurachallenge.service.ConsumoAPI;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvertirDatos conversor = new ConvertirDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    List<Autor> autores;
    List<Libro> libros;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("Elija una opción ingresando un número");
            var menu = """
                    1 - Buscar libro por título 
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en determinado año
                    5 - Mostrar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorFecha();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibrosPorTitulo(){
        System.out.println("Ingrese el título del libro a buscar");
        String tituloIngresado = teclado.nextLine();

        String busqueda = "?search=" + tituloIngresado.replace(" ", "+");
        var json = consumoApi.obtenerDatos(URL_BASE + busqueda);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, DatosResultados.class);

        DatosLibro datosLibro = datos.resultados().get(0);
        Libro libro = new Libro(datosLibro);
        Autor autor = new Autor().obtenerAutor(datosLibro);

        System.out.println(libro);
        guardarLibroAutor(libro, autor);
    }

    private void guardarLibroAutor(Libro libro, Autor autor) {
        //Verifica el autor en la base de datos y lo guarda si no existe
        Optional<Autor> autorBuscado = autorRepository.findByNombreContains(autor.getNombre());

        if(autorBuscado.isPresent()) {
            System.out.println("Ya existe ese autor");
            libro.setAutor(autorBuscado.get());
        } else {
            System.out.println("Adicionando nuevo autor");
            autorRepository.save(autor);
            libro.setAutor(autor);
        }

        //Guarda el libro
        try {
            System.out.println("Adicionando nuevo libro");
            libroRepository.save(libro);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al guardar el libro: " + e.getMessage());
        }
    }

    private void mostrarLibrosRegistrados() {
        libros = libroRepository.findAll();

        printLibros(libros);
    }

    private void mostrarAutoresRegistrados() {
        autores = autorRepository.findAll();

        printAutores(autores);
    }

    private void mostrarAutoresVivosPorFecha() {
        System.out.println("Ingresa el año en el que deseas buscar autores");
        Integer year = Integer.valueOf(teclado.nextLine());

        autores = autorRepository
                .findByFechaDeNacimientoLessThanEqualAndFechaDeMuerteGreaterThanEqual(year, year);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año ingresado");
        } else {
            printAutores(autores);
        }
    }

    private void printLibros(List<Libro> libros) {
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void printAutores(List<Autor> autores) {
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                1 - Inglés
                2 - Español
                3 - Francés
                4 - Portugués
                """);

        var seleccion = teclado.nextInt();
        String idioma;
        switch (seleccion) {
            case 1:
                idioma = "en";
                break;
            case 2:
                idioma = "es";
                break;
            case 3:
                idioma = "fr";
                break;
            case 4:
                idioma = "pt";
                break;
            default:
                System.out.println("Selección inválida. Intente nuevamente.");
                return;
        }

        libros = libroRepository.findByIdiomasContains(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se han encontrado libros en el idioma seleccionado");
        } else {
            printLibros(libros);
        }
    }
}
