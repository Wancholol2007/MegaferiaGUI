package core.controlador.stand;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Publisher;
import core.Stand;

import core.model.storage.EditorialStorage;
import core.model.storage.StandStorage;

import java.util.ArrayList;

public class BuyStand {

    public static Response comprarStand(String textoStands, String textoEditoriales) {

        StandStorage baseStands = StandStorage.getInstance();
        EditorialStorage baseEditoriales = EditorialStorage.getInstance();

        ArrayList<Stand> listaStands = baseStands.getStands();
        ArrayList<Publisher> listaEditoriales = baseEditoriales.getEditoriales();

        ArrayList<Stand> standsSeleccionados = new ArrayList<>();
        ArrayList<Publisher> editorialesSeleccionadas = new ArrayList<>();

        // Validaciones básicas
        if (textoStands == null || textoStands.isBlank()) {
            return new Response("Debe seleccionar al menos un stand.", Status.BAD_REQUEST);
        }

        if (textoEditoriales == null || textoEditoriales.isBlank()) {
            return new Response("Debe seleccionar al menos una editorial.", Status.BAD_REQUEST);
        }

        // Procesar ID de stands seleccionados
        String[] bloquesStands = textoStands.split("\n");

        for (String lineaStand : bloquesStands) {
            try {
                long idStand = Long.parseLong(lineaStand.trim());

                for (Stand stand : listaStands) {
                    if (stand.getId() == idStand) {
                        standsSeleccionados.add(stand);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                return new Response("Los IDs de stands deben ser numéricos.", Status.BAD_REQUEST);
            }
        }

        // Procesar nit de editoriales seleccionadas
        String[] bloquesEditoriales = textoEditoriales.split("\n");

        for (String lineaEditorial : bloquesEditoriales) {
            try {
                String nitExtraido = lineaEditorial.split(" ")[1]
                        .replace("(", "")
                        .replace(")", "");

                for (Publisher editorial : listaEditoriales) {
                    if (editorial.getNit().equals(nitExtraido)) {
                        editorialesSeleccionadas.add(editorial);
                        break;
                    }
                }
            } catch (Exception ex) {
                return new Response("Formato de editorial inválido.", Status.BAD_REQUEST);
            }
        }

        // Vincular stands con editoriales
        for (Stand stand : standsSeleccionados) {
            for (Publisher editorial : editorialesSeleccionadas) {
                stand.addPublisher(editorial);
                editorial.addStand(stand);
            }
        }

        return new Response("Compra de stand(s) realizada correctamente.", Status.OK);
    }
}

