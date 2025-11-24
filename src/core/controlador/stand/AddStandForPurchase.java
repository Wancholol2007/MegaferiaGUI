package core.controlador.stand;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

public class AddStandForPurchase {

    public static Response addStandForCompra(String standIngresado, String listadoActual) {

        if (listadoActual != null && listadoActual.contains(standIngresado)) {
            return new Response(
                "Este stand ya fue incluido previamente.",
                Status.BAD_REQUEST
            );
        }

        return new Response(
            "Stand agregado correctamente a la selección.",
            Status.OK
        );
    }
}

