package core.controlador.book;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Publisher;
import core.Book;
import core.DigitalBook;

import core.Person;
import core.Author;

import core.model.storage.EditorialStorage;
import core.model.storage.LibroStorage;
import core.model.storage.PersonaStorage;

import java.util.ArrayList;

public class BookVirtualController {

    public static Response registrarLibroVirtual(
            String tituloIngresado,
            String datosAutores,
            String codigoIsbn,
            String generoEscogido,
            String formatoEscogido,
            String valorTexto,
            String editorialTexto,
            String urlAcceso) {

        try {
            PersonaStorage basePersonas = PersonaStorage.getInstance();
            EditorialStorage baseEditoriales = EditorialStorage.getInstance();
            LibroStorage baseLibros = LibroStorage.getInstance();

            ArrayList<Book> coleccionLibros = baseLibros.getLibros();

            // Validación del título
            if (tituloIngresado.isBlank()) {
                return new Response("El título no puede estar vacío.", Status.BAD_REQUEST);
            }

            // Validación de autores
            if (datosAutores.isBlank()) {
                return new Response("Debe seleccionar al menos un autor.", Status.BAD_REQUEST);
            }

            // Procesar los autores seleccionados
            String[] lineasAutores = datosAutores.split("\n");
            ArrayList<Author> autoresFinales = new ArrayList<>();

            for (String linea : lineasAutores) {
                long idAutor;

                try {
                    idAutor = Long.parseLong(linea.split(" - ")[0]);
                } catch (NumberFormatException ex) {
                    return new Response("El ID del autor debe ser numérico.", Status.BAD_REQUEST);
                }

                for (Person persona : basePersonas.getPersonas()) {
                    if (persona instanceof Author autor && autor.getId() == idAutor) {
                        autoresFinales.add(autor);
                        break;
                    }
                }
            }

            if (autoresFinales.isEmpty()) {
                return new Response("No se encontraron autores válidos.", Status.BAD_REQUEST);
            }

            // Validación de ISBN
            if (codigoIsbn.isBlank()) {
                return new Response("El ISBN no puede estar vacío.", Status.BAD_REQUEST);
            }

            if (!codigoIsbn.matches("^\\d{3}-\\d{2}-\\d{6}-\\d$")) {
                return new Response("ISBN inválido. Debe tener formato XXX-X-XX-XXXXXX-X.", Status.BAD_REQUEST);
            }

            // Validación de género
            if (generoEscogido.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Debe seleccionar un género.", Status.BAD_REQUEST);
            }

            // Validación de formato
            if (formatoEscogido.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Debe seleccionar un formato.", Status.BAD_REQUEST);
            }

            // Validación de valor
            if (valorTexto.isBlank()) {
                return new Response("Debe ingresar un valor.", Status.BAD_REQUEST);
            }

            double valorLibro;

            try {
                valorLibro = Double.parseDouble(valorTexto);
            } catch (NumberFormatException ex) {
                return new Response("El valor debe ser numérico.", Status.BAD_REQUEST);
            }

            // Validación de editorial
            if (editorialTexto.isBlank()) {
                return new Response("Debe seleccionar una editorial.", Status.BAD_REQUEST);
            }

            String nitEditorial = editorialTexto.split(" ")[1]
                    .replace("(", "")
                    .replace(")", "");

            Publisher editorialSeleccionada = null;

            for (Publisher pub : baseEditoriales.getEditoriales()) {
                if (pub.getNit().equals(nitEditorial)) {
                    editorialSeleccionada = pub;
                    break;
                }
            }

            if (editorialSeleccionada == null) {
                return new Response("La editorial seleccionada no es válida.", Status.BAD_REQUEST);
            }

            // Crear libro digital, con o sin URL según corresponda
            DigitalBook nuevoLibro = urlAcceso.isBlank()
                    ? new DigitalBook(
                            tituloIngresado,
                            autoresFinales,
                            codigoIsbn,
                            generoEscogido,
                            formatoEscogido,
                            valorLibro,
                            editorialSeleccionada)
                    : new DigitalBook(
                            tituloIngresado,
                            autoresFinales,
                            codigoIsbn,
                            generoEscogido,
                            formatoEscogido,
                            valorLibro,
                            editorialSeleccionada,
                            urlAcceso);

            coleccionLibros.add(nuevoLibro);

            return new Response("Libro digital registrado exitosamente.", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Error inesperado al registrar el libro digital.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}

