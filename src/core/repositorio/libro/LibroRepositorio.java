package core.repositorio.libro;

import core.Book;
import java.util.ArrayList;

public class LibroRepositorio {

    private static LibroRepositorio instancia;
    private ArrayList<Book> coleccionLibros;

    private LibroRepositorio() {
        coleccionLibros = new ArrayList<>();
    }

    public static LibroRepositorio getInstancia() {
        if (instancia == null) {
            instancia = new LibroRepositorio();
        }
        return instancia;
    }

    public ArrayList<Book> obtenerLibros() {
        return coleccionLibros;
    }

    public void registrarLibro(Book libro) {
        coleccionLibros.add(libro);
    }

    public Book buscarPorISBN(String isbn) {
        for (Book libro : coleccionLibros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public boolean existeISBN(String isbn) {
        for (Book libro : coleccionLibros) {
            if (libro.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }
}

