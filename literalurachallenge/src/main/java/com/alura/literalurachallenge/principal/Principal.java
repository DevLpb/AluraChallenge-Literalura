package com.alura.literalurachallenge.principal;

import com.alura.literalurachallenge.service.ConvertirDatos;
import com.alura.literalurachallenge.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvertirDatos conversor = new ConvertirDatos();


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
/*
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

 */
            }
        }
    }
