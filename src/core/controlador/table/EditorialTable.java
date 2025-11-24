package core.controlador.table;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.Publisher;
import core.model.persona.utilidades.FullName;

import core.model.storage.EditorialStorage;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;

public class EditorialTable {

    public static Response updateEditorialTable(DefaultTableModel modeloTabla) {

        try {
            EditorialStorage almacenamientoEditorial = EditorialStorage.getInstance();
            ArrayList<Publisher> listaEditoriales = almacenamientoEditorial.getEditoriales();

            // Validar si existe contenido
            if (listaEditoriales == null || listaEditoriales.isEmpty()) {
                return new Response(
                    "No hay editoriales registradas para mostrar.",
                    Status.NO_CONTENT,
                    listaEditoriales
                );
            }

            // Ordenar por NIT
            listaEditoriales.sort(Comparator.comparing(Publisher::getNit));

            // Limpiar tabla
            modeloTabla.setRowCount(0);

            // Poblar tabla con datos ordenados
            for (Publisher editorial : listaEditoriales) {

                String nombreCompletoGerente = FullName.unitVariables(
                        editorial.getGerenteAsignado().getNombres(),
                        editorial.getGerenteAsignado().getApellidos()
                );

                Object[] fila = {
                    editorial.getNit(),
                    editorial.getNombre(),
                    editorial.getDireccion(),
                    nombreCompletoGerente,
                    editorial.getStandQuantity()
                };

                modeloTabla.addRow(fila);
            }

            return new Response(
                "Tabla de editoriales actualizada correctamente.",
                Status.OK,
                listaEditoriales
            );

        } catch (Exception ex) {
            return new Response(
                "Ocurrió un error inesperado al actualizar la tabla de editoriales.",
                Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}

