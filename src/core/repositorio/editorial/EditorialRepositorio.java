package core.repositorio.editorial;

import core.Publisher;
import java.util.ArrayList;

public class EditorialRepositorio {

    private static EditorialRepositorio instancia;
    private ArrayList<Publisher> listaEditoriales;

    private EditorialRepositorio() {
        listaEditoriales = new ArrayList<>();
    }

    public static EditorialRepositorio getInstancia() {
    if (instancia == null) {
        instancia = new EditorialRepositorio();
    }
    return instancia;
}


    public ArrayList<Publisher> getEditoriales() {
        return listaEditoriales;
    }

    public void registrarEditorial(Publisher editorial) {
        listaEditoriales.add(editorial);
    }

    public Publisher buscarPorNIT(String nit) {
        for (Publisher item : listaEditoriales) {
            if (item.getNit().equals(nit)) {
                return item;
            }
        }
        return null;
    }

    public boolean existeNIT(String nit) {
        for (Publisher item : listaEditoriales) {
            if (item.getNit().equals(nit)) {
                return true;
            }
        }
        return false;
    }
}

