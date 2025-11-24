package core.controlador.book;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Publisher;
import core.Book;
import core.PrintedBook;

import core.Person;
import core.Author;

import core.model.storage.PersonaStorage;
import core.model.storage.EditorialStorage;
import core.model.storage.LibroStorage;

import java.util.ArrayList;

public class BookFisicoController {

    public static Response registrarLibroFisico(
            String tituloIngresado,
            String datosAutores,
            String codigoIsbn,
            String generoSeleccionado,
            String formatoSeleccionado,
            String valorTexto,
            String editorialTexto,
            String paginasTexto,
            String copiasTexto) {

        try {
            PersonaStorage personasBD = PersonaStorage.getInstance();
            EditorialStorage editorialesBD = EditorialStorage.getInstance();
            LibroStorage librosBD = LibroStorage.getInstance();

            ArrayList<Book> listaLibros = librosBD.getLibros();

            // Validación título
            if (tituloIngresado.isBlank()) {
                return new Response("El título no puede estar vacío.", Status.BAD_REQUEST);
            }

            // Validación autores
            if (datosAutores.isBlank()) {
                return new Response("Debe seleccionar al menos un autor.", Status.BAD_REQUEST);
            }

            // Procesamiento de autores
            String[] lineasAutores = datosAutores.split("\n");
            ArrayList<Author> autoresFinales = new ArrayList<>();

            for (String linea : lineasAutores) {
                long idAutorExtraido;

                try {
                    idAutorExtraido = Long.parseLong(linea.split(" - ")[0]);
                } catch (NumberFormatException e) {
                    return new Response("El ID del autor debe ser numérico.", Status.BAD_REQUEST);
                }

                for (Person persona : personasBD.getPersonas()) {
                    if (persona instanceof Author autor && autor.getId() == idAutorExtraido) {
                        autoresFinales.add(autor);
                        break;
                    }
                }
            }

            if (autoresFinales.isEmpty()) {
                return new Response("No se encontraron autores válidos.", Status.BAD_REQUEST);
            }

            // Validación ISBN
            if (codigoIsbn.isBlank()) {
                return new Response("El ISBN no puede estar vacío.", Status.BAD_REQUEST);
            }

            if (!codigoIsbn.matches("^\\d{3}-\\d-\\d{2}-\\d{6}-\\d$")) {
                return new Response("Formato de ISBN incorrecto. Use: XXX-X-XX-XXXXXX-X", Status.BAD_REQUEST);
            }

            // Género
            if (generoSeleccionado.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Debe seleccionar un género.", Status.BAD_REQUEST);
            }

            // Formato
            if (formatoSeleccionado.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Debe seleccionar un formato.", Status.BAD_REQUEST);
            }

            // Valor
            if (valorTexto.isBlank()) {
                return new Response("Debe ingresar un valor.", Status.BAD_REQUEST);
            }

            double valorLibro;
            try {
                valorLibro = Double.parseDouble(valorTexto);
            } catch (NumberFormatException e) {
                return new Response("El valor debe ser numérico.", Status.BAD_REQUEST);
            }

            // Editorial
            if (editorialTexto.isBlank()) {
                return new Response("Debe seleccionar una editorial.", Status.BAD_REQUEST);
            }

            String nitExtraido = editorialTexto.split(" ")[1]
                    .replace("(", "")
                    .replace(")", "");

            Publisher editorialAsociada = null;

            for (Publisher pub : editorialesBD.getEditoriales()) {
                if (pub.getNit().equals(nitExtraido)) {
                    editorialAsociada = pub;
                    break;
                }
            }

            if (editorialAsociada == null) {
                return new Response("La editorial seleccionada no es válida.", Status.BAD_REQUEST);
            }

            // Copias
            if (copiasTexto.isBlank()) {
                return new Response("Debe ingresar el número de copias.", Status.BAD_REQUEST);
            }

            int cantidadCopias;
            try {
                cantidadCopias = Integer.parseInt(copiasTexto);
            } catch (NumberFormatException e) {
                return new Response("Las copias deben ser numéricas.", Status.BAD_REQUEST);
            }

            // Páginas
            if (paginasTexto.isBlank()) {
                return new Response("Debe ingresar el número de páginas.", Status.BAD_REQUEST);
            }

            int cantidadPaginas;
            try {
                cantidadPaginas = Integer.parseInt(paginasTexto);
            } catch (NumberFormatException e) {
                return new Response("Las páginas deben ser numéricas.", Status.BAD_REQUEST);
            }

            // Crear libro físico
            PrintedBook nuevoLibro = new PrintedBook(
                    tituloIngresado,
                    autoresFinales,
                    codigoIsbn,
                    generoSeleccionado,
                    formatoSeleccionado,
                    valorLibro,
                    editorialAsociada,
                    cantidadPaginas,
                    cantidadCopias
            );

            listaLibros.add(nuevoLibro);

            return new Response("Libro físico registrado con éxito.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Error inesperado al registrar el libro físico.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
