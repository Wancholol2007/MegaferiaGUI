package core.controlador.editorial;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Publisher;
import core.Person;
import core.Manager;

import core.repositorio.editorial.EditorialRepositorio;
import core.repositorio.persona.PersonaRepositorio;

import java.util.ArrayList;

public class Editorial {

    public static Response registrarEditorial(String nitIngresado, String nombreEditorial, String direccion,
                                              String infoGerenteSeleccionado) {

        PersonaRepositorio repoPersonas = PersonaRepositorio.getInstancia();
        EditorialRepositorio repoEditoriales = EditorialRepositorio.getInstancia();
        ArrayList<Publisher> listaEditoriales = repoEditoriales.getEditoriales();

        try {
            // Validación básica de campos
            if (nitIngresado == null || nitIngresado.trim().isEmpty()) {
                return new Response("Debe ingresar un NIT válido.", Status.BAD_REQUEST);
            }

            if (nombreEditorial == null || nombreEditorial.trim().isEmpty()) {
                return new Response("El nombre de la editorial no puede estar vacío.", Status.BAD_REQUEST);
            }

            if (direccion == null || direccion.trim().isEmpty()) {
                return new Response("La dirección no puede estar vacía.", Status.BAD_REQUEST);
            }

            if (infoGerenteSeleccionado == null 
                || infoGerenteSeleccionado.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Debe seleccionar un gerente válido.", Status.BAD_REQUEST);
            }

            // Validación del formato de NIT
            if (!nitIngresado.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d$")) {
                return new Response(
                    "El NIT debe tener el formato XXX.XXX.XXX-X",
                    Status.BAD_REQUEST
                );
            }

            // Obtener ID del gerente seleccionado
            String[] datosGerente = infoGerenteSeleccionado.split(" - ");
            long idGerente = Long.parseLong(datosGerente[0]);

            Manager gerenteAsociado = null;

            for (Person personaActual : repoPersonas.getPersonas()) {
                if (personaActual instanceof Manager) {
                    if (personaActual.getId() == idGerente) {
                        gerenteAsociado = (Manager) personaActual;
                        break;
                    }
                }
            }

            // Crear editorial
            Publisher nuevaEditorial = new Publisher(
                nitIngresado,
                nombreEditorial,
                direccion,
                gerenteAsociado
            );

            listaEditoriales.add(nuevaEditorial);

            return new Response(
                "Editorial registrada exitosamente.",
                Status.CREATED
            );

        } catch (Exception ex) {
            return new Response(
                "Ocurrió un error inesperado durante el registro.",
                Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}

