package core.controlador.stand;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Stand;
import core.model.storage.StandStorage;

import java.util.ArrayList;

public class StandControlaler {

    public static Response addStand(String idTexto, String precioTexto) {

        StandStorage repositorioStands = StandStorage.getInstance();
        ArrayList<Stand> listaStands = repositorioStands.getStands();

        try {
            long idConvertido;
            double precioConvertido;

            // Validación del ID
            try {
                idConvertido = Long.parseLong(idTexto);

                if (idConvertido < 0) {
                    return new Response(
                        "El ID debe ser un número positivo.",
                        Status.BAD_REQUEST
                    );
                }

                if (String.valueOf(idConvertido).length() > 15) {
                    return new Response(
                        "El ID no puede tener más de 15 dígitos.",
                        Status.BAD_REQUEST
                    );
                }

                for (Stand elemento : listaStands) {
                    if (elemento.getId() == idConvertido) {
                        return new Response(
                            "Ese ID ya está registrado. Ingrese otro.",
                            Status.BAD_REQUEST
                        );
                    }
                }

            } catch (NumberFormatException ex) {
                return new Response(
                    "El ID debe ser un número válido.",
                    Status.BAD_REQUEST
                );
            }

            // Validación del precio
            try {
                precioConvertido = Double.parseDouble(precioTexto);

                if (precioConvertido <= 0) {
                    return new Response(
                        "El precio debe ser mayor que cero.",
                        Status.BAD_REQUEST
                    );
                }

            } catch (NumberFormatException ex) {
                return new Response(
                    "El precio debe ser numérico.",
                    Status.BAD_REQUEST
                );
            }

            // Crear nuevo stand
            Stand nuevoStand = new Stand(idConvertido, precioConvertido);
            listaStands.add(nuevoStand);

            return new Response(
                "Stand registrado exitosamente.",
                Status.CREATED
            );

        } catch (Exception ex) {
            return new Response(
                "Ocurrió un error inesperado al registrar el stand.",
                Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}

