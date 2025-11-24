package core.controlador.table;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;
import core.model.book.Book;
import core.model.book.DigitalBook;
import core.model.book.PrintedBook;
import core.model.book.audioBook.AudioBook;
import core.model.persona.utilidades.FullName;
import core.model.storage.LibroStorage;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class LibroTable {

    public static Response updateLibroTable(DefaultTableModel model, String search) {
        try {
            LibroStorage libroStorage = LibroStorage.getInstance();
            ArrayList<Books> libros = libroStorage.getLibros();
            if (libros == null || libros.isEmpty()) {
                return new Response("No books available to display", Status.NO_CONTENT);
            }
            if (search.equalsIgnoreCase("Seleccione uno...")) {
                return new Response(search, Status.BAD_REQUEST);
            }
            if (search.equalsIgnoreCase("Libros Digitales")) {
                for (Book libro : libros) {
                    if (libro instanceof DigitalBook libroDigital) {

                        String autores = FullName.unitVariables(libroDigital.getAuthors().get(0).getFirstname(), libroDigital.getAuthors().get(0).getLastname());
                        for (int i = 1; i < libroDigital.getAuthors().size(); i++) {
                            autores += ", " + FullName.unitVariables(libroDigital.getAuthors().get(i).getFirstname(), libroDigital.getAuthors().get(i).getLastname());
                        }
                        model.addRow(new Object[]{libroDigital.getTitle(), autores, libroDigital.getIsbn(),
                            libroDigital.getGenre(), libroDigital.getFormat(), libroDigital.getValue(),
                            libroDigital.getPublisher().getName(), "-", "-",
                            libroDigital.hasHyperlink() ? libroDigital.getHyperlink() : "No", "-", "-"});
                    }
                }
            }
            if (search.equals("Libros Impresos")) {
                for (Book book : libros) {
                    if (book instanceof PrintedBook printedBook) {
                        String authors = FullName.unitVariables(printedBook.getAuthors().get(0).getFirstname(), printedBook.getAuthors().get(0).getLastname());
                        for (int i = 1; i < printedBook.getAuthors().size(); i++) {
                            authors += (", " + FullName.unitVariables(printedBook.getAuthors().get(i).getFirstname(), printedBook.getAuthors().get(i).getLastname()));
                        }
                        model.addRow(new Object[]{printedBook.getTitle(), authors, printedBook.getIsbn(),
                            printedBook.getGenre(), printedBook.getFormat(), printedBook.getValue(),
                            printedBook.getPublisher().getName(), printedBook.getCopies(), printedBook.getPages(),
                            "-", "-", "-"});
                    }
                }
            }
            if (search.equals("Audiolibros")) {
                for (Book book : libros) {
                    if (book instanceof AudioBook audiobook) {
                        String authors = FullName.unitVariables(audiobook.getAuthors().get(0).getFirstname(), audiobook.getAuthors().get(0).getLastname());
                        for (int i = 1; i < audiobook.getAuthors().size(); i++) {
                            authors += (", " + FullName.unitVariables(audiobook.getAuthors().get(i).getFirstname(), audiobook.getAuthors().get(i).getLastname()));
                        }
                        model.addRow(new Object[]{audiobook.getTitle(), authors, audiobook.getIsbn(),
                            audiobook.getGenre(), audiobook.getFormat(), audiobook.getValue(),
                            audiobook.getPublisher().getName(), "-", "-", "-",
                            FullName.unitVariables(audiobook.getNarrator().getFirstname(), audiobook.getNarrator().getLastname()), audiobook.getDuration()});
                    }
                }
            }
            if (search.equals("Todos los Libros")) {
                for (Book book : libros) {
                    String authors = FullName.unitVariables(book.getAuthors().get(0).getFirstname(), book.getAuthors().get(0).getLastname());
                    for (int i = 1; i < book.getAuthors().size(); i++) {
                        authors += (", " + FullName.unitVariables(book.getAuthors().get(i).getFirstname(), book.getAuthors().get(i).getLastname()));
                    }
                    if (book instanceof PrintedBook printedBook) {
                        model.addRow(new Object[]{printedBook.getTitle(), authors, printedBook.getIsbn(),
                            printedBook.getGenre(), printedBook.getFormat(), printedBook.getValue(),
                            printedBook.getPublisher().getName(), printedBook.getCopies(), printedBook.getPages(),
                            "-", "-", "-"});
                    }
                    if (book instanceof DigitalBook digitalBook) {
                        model.addRow(new Object[]{digitalBook.getTitle(), authors, digitalBook.getIsbn(),
                            digitalBook.getGenre(), digitalBook.getFormat(), digitalBook.getValue(),
                            digitalBook.getPublisher().getName(), "-", "-",
                            digitalBook.hasHyperlink() ? digitalBook.getHyperlink() : "No", "-", "-"});
                    }
                    if (book instanceof AudioBook audiobook) {
                        model.addRow(new Object[]{audiobook.getTitle(), authors, audiobook.getIsbn(),
                            audiobook.getGenre(), audiobook.getFormat(), audiobook.getValue(),
                            audiobook.getPublisher().getName(), "-", "-", "-",
                            FullName.unitVariables(audiobook.getNarrator().getFirstname(), audiobook.getNarrator().getLastname()), audiobook.getDuration()});
                    }
                }
            }

            return new Response("Book table updateed successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
