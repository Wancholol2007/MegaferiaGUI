package core.controlador.table.addConsultas;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.model.book.Book;
import core.model.book.DigitalBook;
import core.model.book.PrintedBook;
import core.model.book.audioBook.AudioBook;

import core.model.persona.utilidades.FullName;

import core.model.storage.LibroStorage;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LibroFormatTable {

    public static Response updateLibroFormatoConAdd(DefaultTableModel modeloTabla, String formatoSeleccionado) {

        try {
            LibroStorage almacenamientoLibros = LibroStorage.getInstance();
            ArrayList<core.Book> listaLibros = almacenamientoLibros.getLibros();

            // Limpiar tabla
            modeloTabla.setRowCount(0);

            // Recorrer libros y filtrar por formato
            for (core.Book libro : listaLibros) {

                if (!libro.getFormat().equalsIgnoreCase(formatoSeleccionado)) {
                    continue;
                }

                // Construcción de cadena de autores
                String autoresCadena = FullName.unitVariables(
                        libro.getAuthors().get(0).getNombres(),
                        libro.getAuthors().get(0).getApellidos()
                );

                for (int i = 1; i < libro.getAuthors().size(); i++) {
                    autoresCadena += ", " + FullName.unitVariables(
                            libro.getAuthors().get(i).getNombres(),
                            libro.getAuthors().get(i).getApellidos()
                    );
                }

                // Impreso
                if (libro instanceof PrintedBook impreso) {

                    modeloTabla.addRow(new Object[]{
                            impreso.getTitle(), autoresCadena, impreso.getIsbn(),
                            impreso.getGenre(), impreso.getFormat(), impreso.getValue(),
                            impreso.getPublisher().getNombre(), impreso.getCopies(),
                            impreso.getPages(), "-", "-", "-"
                    });
                }

                // Digital
                else if (libro instanceof DigitalBook digital) {

                    modeloTabla.addRow(new Object[]{
                            digital.getTitle(), autoresCadena, digital.getIsbn(),
                            digital.getGenre(), digital.getFormat(), digital.getValue(),
                            digital.getPublisher().getNombre(), "-", "-",
                            digital.hasHyperlink() ? digital.getHyperlink() : "No",
                            "-", "-"
                    });
                }

                // Audiobook
                else if (libro instanceof AudioBook audio) {

                    modeloTabla.addRow(new Object[]{
                            audio.getTitle(), autoresCadena, audio.getIsbn(),
                            audio.getGenre(), audio.getFormat(), audio.getValue(),
                            audio.getPublisher().getNombre(), "-", "-", "-",
                            FullName.unitVariables(
                                    audio.getNarrator().getNombres(),
                                    audio.getNarrator().getApellidos()
                            ),
                            audio.getDuration()
                    });
                }
            }

            return new Response(
                "Tabla de libros filtrada por formato actualizada correctamente.",
                Status.OK
            );

        } catch (Exception ex) {
            return new Response(
                "Hubo un error inesperado al actualizar la tabla de libros por formato.",
                Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}

