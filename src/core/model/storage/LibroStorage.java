package core.model.storage;

import core.Book;
import java.util.ArrayList;

public class LibroStorage {

    private static LibroStorage instancia;
    private ArrayList<Book> listaLibros;

    private LibroStorage() {
        listaLibros = new ArrayList<>();
    }

    public static LibroStorage getInstance() {
        if (instancia == null) {
            instancia = new LibroStorage();
        }
        return instancia;
    }

    public ArrayList<Book> getLibros() {
        return listaLibros;
    }
    
    public void agregarLibro(Book nuevoLibro) {
        listaLibros.add(nuevoLibro);
    }

    public boolean existeISBN(String isbn) {
        for (Book libro : listaLibros) {
            if (libro.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    public Book buscarPorISBN(String isbn) {
        for (Book libro : listaLibros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
}

