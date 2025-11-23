package core.controlador.editorial;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

public class AddEditorialForPucharse {
    
    public static Response anexarEditorial(String editorialIngresada, String listaEditorialesActuales) {

        if (listaEditorialesActuales != null && listaEditorialesActuales.contains(editorialIngresada)) {
            return new Response(
                "Esta editorial ya fue incluida en la selección.",
                Status.BAD_REQUEST
            );
        }

        return new Response(
            "Editorial añadida correctamente a la compra.",
            Status.OK
        );
    }
}


