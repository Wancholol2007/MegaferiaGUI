package core.controlador.narrator;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Person;
import core.Narrator;

import core.model.storage.PersonaStorage;

import java.util.ArrayList;

public class NarratorController {

    public static Response registrarNarrador(
            String idIngresado,
            String nombreIngresado,
            String apellidoIngresado) {

        PersonaStorage basePersonas = PersonaStorage.getInstance();
        ArrayList<Person> listaPersonas = basePersonas.getPersonas();

        try {
            long idConvertido;

            // Validación de ID numérico
            try {
                idConvertido = Long.parseLong(idIngresado);

                if (idConvertido < 0) {
                    return new Response(
                        "El ID debe ser un número positivo o cero.",
                        Status.BAD_REQUEST
                    );
                }

                if (String.valueOf(idConvertido).length() > 15) {
                    return new Response(
                        "El ID no debe superar los 15 dígitos.",
                        Status.BAD_REQUEST
                    );
                }

                for (Person elemento : listaPersonas) {
                    if (elemento.getId() == idConvertido) {
                        return new Response(
                            "Ese ID ya está registrado. Intente con otro.",
                            Status.BAD_REQUEST
                        );
                    }
                }

            } catch (NumberFormatException ex) {
                return new Response(
                    "El ID debe contener únicamente números.",
                    Status.BAD_REQUEST
                );
            }

            // Validación nombres
            if (nombreIngresado == null || nombreIngresado.isBlank()) {
                return new Response(
                    "El nombre no puede estar vacío.",
                    Status.BAD_REQUEST
                );
            }

            if (apellidoIngresado == null || apellidoIngresado.isBlank()) {
                return new Response(
                    "El apellido no puede estar vacío.",
                    Status.BAD_REQUEST
                );
            }

            // Crear narrador
            Narrator nuevoNarrador = new Narrator(
                idConvertido,
                nombreIngresado,
                apellidoIngresado
            );

            listaPersonas.add(nuevoNarrador);

            return new Response(
                "Narrador registrado exitosamente.",
                Status.CREATED
            );

        } catch (Exception ex) {
            return new Response(
                "Ocurrió un error inesperado al registrar el narrador.",
                Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}
