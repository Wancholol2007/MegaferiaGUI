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

    public static Response updateEditorialTable(DefaultTableModel model) {
        try {
            EditorialStorage editorialStorage = EditorialStorage.getInstance();
            ArrayList<Publisher> editoriales = editorialStorage.getEditoriales();

            if (editoriales == null || editoriales.isEmpty()) {
                return new Response("No editorials available to display", Status.NO_CONTENT, editoriales.clone());
            }

            editoriales.sort(Comparator.comparing(Publisher::getNit));
            model.setRowCount(0);
            for (Publisher editorial : editoriales) {
                Object[] rowData = {
                        editorial.getNit(),
                        editorial.getName(),
                        editorial.getAddress(),
                        FullName.unitVariables(editorial.getManager().getFirstname(),
                                editorial.getManager().getLastname()),
                        editorial.getStandQuantity()
                };
                model.addRow(rowData);
            }
            return new Response("Editorial table updated successfully", Status.OK, editoriales.clone());
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }

    }

}

