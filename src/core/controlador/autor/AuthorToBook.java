package core.controlador.autor;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

public class AuthorToBook {

    public static Response vincularAutor(String autorSeleccionado, String listaAutoresActual) {

        if (listaAutoresActual != null && listaAutoresActual.contains(autorSeleccionado)) {
            return new Response(
                    "Este autor ya fue agregado previamente.",
                    Status.BAD_REQUEST
            );
        }

        return new Response(
                "Autor incorporado correctamente al libro.",
                Status.OK
        );
    }
}

