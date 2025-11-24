package core.controlador.table.addConsultas;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.model.libro.Book;
import core.model.libro.DigitalBook;
import core.model.libro.PrintedBook;
import core.model.libro.audioBook.AudioBook;

import core.model.persona.Person;
import core.model.persona.autor.Author;
import core.model.persona.utilidades.FullName;

import core.model.storage.LibroStorage;
import core.model.storage.PersonaStorage;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LibroAutorTable {

    public static Response updateLibroAutorConAdd(DefaultTableModel modeloTabla, String datoAutorSeleccionado) {

        try {
            LibroStorage almacenamientoLibros = LibroStorage.getInstance();
            ArrayList<Book> listaLibros = almacenamientoLibros.getLibros();

            if (listaLibros == null || listaLibros.isEmpty()) {
                return new Response(
                    "No hay libros registrados para mostrar en relación con el autor.",
                    Status.NO_CONTENT
                );
            }

            // Validación de selección
            if (datoAutorSeleccionado == null ||
                datoAutorSeleccionado.equalsIgnoreCase("Seleccione uno...")) {

                return new Response(
                    "Debe seleccionar un autor antes de visualizar datos.",
                    Status.BAD_REQUEST
                );
            }

            // Extraer ID del autor
            long idAutor;

            try {
                idAutor = Long.parseLong(datoAutorSeleccionado.split(" - ")[0]);
            } catch (NumberFormatException ex) {
                return new Response(
                    "El ID del autor no tiene un formato válido.",
                    Status.BAD_REQUEST
                );
            }

            // Buscar autor
            PersonaStorage almacenamientoPersonas = PersonaStorage.getInstance();
            Author autorSeleccionado = null;

            for (Person persona : almacenamientoPersonas.getPersonas()) {
                if (persona instanceof Author autor && autor.getId() == idAutor) {
                    autorSeleccionado = autor;
                    break;
                }
            }

            if (autorSeleccionado == null) {
                return new Response(
                    "El autor seleccionado no está registrado.",
                    Status.NOT_FOUND
                );
            }

            // Limpiar tabla antes de llenarla
            modeloTabla.setRowCount(0);

            // Recorrer libros del autor
            for (Book libro : autorSeleccionado.getBooks()) {

                // Construir cadena de autores
                String autoresCadena = FullName.unitVariables(
                        libro.getAuthors().get(0).getNombres(),
                        libro.getAuthors().get(0).getApellidos()
                );

                for (int i = 1; i < libro.getAuthors().size(); i++) {
                    Author a = libro.getAuthors().get(i);
                    autoresCadena += ", " + FullName.unitVariables(a.getNombres(), a.getApellidos());
                }

                // Impreso
                switch (libro) {
                    case PrintedBook impreso -> modeloTabla.addRow(new Object[]{
                        impreso.getTitle(), autoresCadena, impreso.getIsbn(),
                        impreso.getGenre(), impreso.getFormat(), impreso.getValue(),
                        impreso.getPublisher().getNombre(), impreso.getCopies(),
                        impreso.getPages(), "-", "-", "-"
                    });
                    case DigitalBook digital -> modeloTabla.addRow(new Object[]{
                        digital.getTitle(), autoresCadena, digital.getIsbn(),
                        digital.getGenre(), digital.getFormat(), digital.getValue(),
                        digital.getPublisher().getNombre(), "-", "-",
                        digital.hasHyperlink() ? digital.getHyperlink() : "No",
                        "-", "-"
                    });
                    case AudioBook audio -> modeloTabla.addRow(new Object[]{
                        audio.getTitle(), autoresCadena, audi
                            
                    default -> {
                    }
                }

