
package core.controlador.table;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;
import core.Stand;
import core.model.storage.StandStorage;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class StandTable {
    
    public static Response updateStandTable(DefaultTableModel model) {
        try {
            StandStorage standStorage = StandStorage.getInstance();
            ArrayList<Stand> stands = standStorage.getStands();
            if (stands == null || stands.isEmpty()) {
                return new Response("No stands available to display", Status.NO_CONTENT);
            }
            model.setRowCount(0);

            for (Stand stand : stands) {
                String publishers = "";
                if (stand.getPublisherQuantity() > 0) {
                    publishers += stand.getPublishers().get(0).getName();
                    for (int i = 1; i < stand.getPublisherQuantity(); i++) {
                        publishers += ", " + stand.getPublishers().get(i).getName();
                    }
                }
                Object[] rowData = {
                        stand.getId(),
                        stand.getPrice(),
                        stand.getPublisherQuantity() > 0 ? "Si" : "No",
                        publishers
                };
                model.addRow(rowData);
            }
            return new Response("Stand table updated successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
