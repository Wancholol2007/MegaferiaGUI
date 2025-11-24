package core.controlador.table.addConsultas;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Publisher;
import core.model.persona.utilidades.FullName;
import core.model.storage.EditorialStorage;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;

public class EditorialTable {

    public static Response updateEditorialTable(DefaultTableModel tablaModelo) {

        try {
            EditorialStorage almacenamientoEditoriales = EditorialStorage.getInstance();
            ArrayList<Publisher> listaEditoriales = almacenamientoEditoriales.getEditoriales();

            // Validar datos
            if (listaEditoriales == null || listaEditoriales.isEmpty()) {
                return new Response(
                    "No hay editoriales registradas para mostrar.",
                    Status.NO_CONTENT,
                    listaEditoriales
                );
            }

            // Ordenar editoriales por NIT
            listaEditoriales.sort(Comparator.comparing(Publisher::getNit));

            // Limpiar tabla antes de llenarla
            tablaModelo.setRowCount(0);

            // Insertar filas
            for (Publisher editorial : listaEditoriales) {

                String gerenteCompleto;
                gerenteCompleto = FullName.unitVariables(
                        editorial.getGerenteAsignado().getFirstname(),
                        editorial.getGerenteAsignado().getLastname()
                );

                Object[] fila = {
                        editorial.getNit(),
                        editorial.getNombre(),
                        editorial.getDireccion(),
                        gerenteCompleto,
                        editorial.getStandQuantity()
                };

                tablaModelo.addRow(fila);
            }

            return new Response(
                "Tabla de editoriales actualizada correctamente.",
                Status.OK,
                listaEditoriales
            );

        } catch (Exception ex) {
            return new Response(
                "Ocurrió un error inesperado al actualizar la tabla.",
                Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}
