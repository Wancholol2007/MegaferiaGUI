package core.controlador.autor;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Author;
import core.Person;
import core.repositorio.persona.PersonaRepositorio;

import java.util.ArrayList;

public class ControllerAutor {

    public static Response agregarAutor(String idCadena, String nombrePrimario, String apellidoPrimario) {

        PersonaRepositorio repositorioPersonas = PersonaRepositorio.getInstancia();
        ArrayList<Person> listaPersonas = repositorioPersonas.obtenerPersonas();

        try {
            long idConvertido;

            // Validación de ID
            try {
                idConvertido = Long.parseLong(idCadena);

                if (idConvertido < 0) {
                    return new Response("El ID debe ser mayor o igual a 0.", Status.BAD_REQUEST);
                }

                if (String.valueOf(idConvertido).length() > 15) {
                    return new Response("El ID no puede exceder 15 dígitos.", Status.BAD_REQUEST);
                }

                if (listaPersonas != null) {
                    for (Person elemento : listaPersonas) {
                        if (elemento.getId() == idConvertido) {
                            return new Response("El ID ya está en uso, ingrese otro.", Status.BAD_REQUEST);
                        }
                    }
                }

            } catch (NumberFormatException ex) {
                return new Response("El ID debe ser numérico.", Status.BAD_REQUEST);
            }

            // Nombres obligatorios
            if (nombrePrimario == null || nombrePrimario.trim().isEmpty()) {
                return new Response("El nombre es obligatorio.", Status.BAD_REQUEST);
            }

            if (apellidoPrimario == null || apellidoPrimario.trim().isEmpty()) {
                return new Response("El apellido es obligatorio.", Status.BAD_REQUEST);
            }

            // Crear autor
            Author nuevoAutor = new Author(idConvertido, nombrePrimario, apellidoPrimario);
            listaPersonas.add(nuevoAutor);

            return new Response("Autor registrado con éxito.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Error inesperado en el registro.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}

