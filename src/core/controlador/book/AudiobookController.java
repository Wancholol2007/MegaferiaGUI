package core.controlador.book;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Publisher;
import core.Book;
import core.Audiobook;
import core.Person;
import core.Author;
import core.Narrator;

import core.repositorio.editorial.EditorialRepositorio;
import core.repositorio.libro.LibroRepositorio;
import core.repositorio.persona.PersonaRepositorio;

import java.util.ArrayList;

public class AudiobookController {

    public static Response registrarAudiolibro(
            String tituloObra,
            String autoresTexto,
            String codigoIsbn,
            String generoSeleccionado,
            String formatoSeleccionado,
            String valorTexto,
            String editorialTexto,
            String duracionTexto,
            String narradorTexto) {

        try {
            PersonaRepositorio repoPersonas = PersonaRepositorio.getInstancia();
            EditorialRepositorio repoEditoriales = EditorialRepositorio.getInstancia();
            LibroRepositorio repoLibros = LibroRepositorio.getInstancia();
            ArrayList<Book> coleccionLibros = repoLibros.getLibros();

            // Validación título
            if (tituloObra == null || tituloObra.isBlank()) {
                return new Response("El título no puede estar vacío.", Status.BAD_REQUEST);
            }

            // Validación autores
            if (autoresTexto == null || autoresTexto.isBlank()) {
                return new Response("Debe seleccionar al menos un autor.", Status.BAD_REQUEST);
            }

            String[] autoresEnLineas = autoresTexto.split("\n");
            ArrayList<Author> listaAutores = new ArrayList<>();

            for (String lineaAutor : autoresEnLineas) {
                long idAutor;
                try {
                    idAutor = Long.parseLong(lineaAutor.split(" - ")[0]);
                } catch (NumberFormatException e) {
                    return new Response("El ID del autor debe ser numérico.", Status.BAD_REQUEST);
                }

                for (Person persona : repoPersonas.getPersonas()) {
                    if (persona instanceof Author autor && autor.getId() == idAutor) {
                        listaAutores.add(autor);
                        break;
                    }
                }
            }

            if (listaAutores.isEmpty()) {
                return new Response("No se encontraron autores válidos.", Status.BAD_REQUEST);
            }

            // Validación ISBN
            if (codigoIsbn == null || codigoIsbn.isBlank()) {
                return new Response("El ISBN no puede estar vacío.", Status.BAD_REQUEST);
            }

            if (!codigoIsbn.matches("^\\d{3}-\\d{2}-\\d{6}-\\d$")) {
                return new Response("El ISBN debe cumplir el formato XXX-X-XX-XXXXXX-X.", Status.BAD_REQUEST);
            }

            // Validación género
            if (generoSeleccionado == null || generoSeleccionado.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Debe seleccionar un género.", Status.BAD_REQUEST);
            }

            // Validación formato
            if (formatoSeleccionado == null || formatoSeleccionado.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Debe seleccionar un formato.", Status.BAD_REQUEST);
            }

            // Validación valor
            if (valorTexto == null || valorTexto.isBlank()) {
                return new Response("El valor del audiolibro no puede estar vacío.", Status.BAD_REQUEST);
            }

            double valor;
            try {
                valor = Double.parseDouble(valorTexto);
            } catch (NumberFormatException e) {
                return new Response("El valor debe ser numérico.", Status.BAD_REQUEST);
            }

            // Validación editorial
            if (editorialTexto == null || editorialTexto.isBlank()) {
                return new Response("Debe seleccionar una editorial.", Status.BAD_REQUEST);
            }

            String nitEditorial = editorialTexto.split(" ")[1]
                    .replace("(", "")
                    .replace(")", "");

            Publisher editorialSeleccionada = null;

            for (Publisher pub : repoEditoriales.getEditoriales()) {
                if (pub.getNit().equals(nitEditorial)) {
                    editorialSeleccionada = pub;
                    break;
                }
            }

            if (editorialSeleccionada == null) {
                return new Response("La editorial seleccionada no es válida.", Status.BAD_REQUEST);
            }

            // Validación duración
            if (duracionTexto == null || duracionTexto.isBlank()) {
                return new Response("La duración no puede estar vacía.", Status.BAD_REQUEST);
            }

            int duracionMinutos;
            try {
                duracionMinutos = Integer.parseInt(duracionTexto);
            } catch (NumberFormatException e) {
                return new Response("La duración debe ser numérica.", Status.BAD_REQUEST);
            }

            // Validación narrador
            if (narradorTexto == null || narradorTexto.isBlank()) {
                return new Response("Debe seleccionar un narrador.", Status.BAD_REQUEST);
            }

            long idNarrador;
            try {
                idNarrador = Long.parseLong(narradorTexto.split(" - ")[0]);
            } catch (NumberFormatException e) {
                return new Response("El ID del narrador debe ser numérico.", Status.BAD_REQUEST);
            }

            Narrator narradorAsociado = null;

            for (Person persona : repoPersonas.getPersonas()) {
                if (persona instanceof Narrator narrador && narrador.getId() == idNarrador) {
                    narradorAsociado = narrador;
                    break;
                }
            }

            if (narradorAsociado == null) {
                return new Response("No se encontró un narrador válido.", Status.BAD_REQUEST);
            }

            // Crear audiolibro
            Audiobook nuevoAudiolibro = new Audiobook(
                    tituloObra,
                    listaAutores,
                    codigoIsbn,
                    generoSeleccionado,
                    formatoSeleccionado,
                    valor,
                    editorialSeleccionada,
                    duracionMinutos,
                    narradorAsociado
            );

            coleccionLibros.add(nuevoAudiolibro);

            return new Response("Audiolibro registrado correctamente.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Error inesperado al registrar el audiolibro.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}

