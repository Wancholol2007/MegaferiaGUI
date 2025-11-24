package core.model.storage;

import core.Publisher;
import java.util.ArrayList;

public class EditorialStorage {

    private static EditorialStorage instancia;
    private ArrayList<Publisher> listaEditoriales;

    private EditorialStorage() {
        listaEditoriales = new ArrayList<>();
    }

    public static EditorialStorage getInstance() {
        if (instancia == null) {
            instancia = new EditorialStorage();
        }
        return instancia;
    }

    public ArrayList<Publisher> getEditoriales() {
        return listaEditoriales;
    }

    public void agregarEditorial(Publisher nuevaEditorial) {
        listaEditoriales.add(nuevaEditorial);
    }

    public Publisher buscarPorNit(String nit) {
        for (Publisher item : listaEditoriales) {
            if (item.getNit().equals(nit)) {
                return item;
            }
        }
        return null;
    }

    public boolean existeNit(String nit) {
        for (Publisher item : listaEditoriales) {
            if (item.getNit().equals(nit)) {
                return true;
            }
        }
        return false;
    }
}

