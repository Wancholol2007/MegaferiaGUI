package core.model.storage;

import core.Person;
import java.util.ArrayList;

public class PersonaStorage {

    private static PersonaStorage instancia;
    private ArrayList<Person> listaPersonas;

    private PersonaStorage() {
        listaPersonas = new ArrayList<>();
    }

    public static PersonaStorage getInstance() {
        if (instancia == null) {
            instancia = new PersonaStorage();
        }
        return instancia;
    }

    public ArrayList<Person> getPersonas() {
        return listaPersonas;
    }

    public void agregarPersona(Person nuevaPersona) {
        listaPersonas.add(nuevaPersona);
    }

    public Person buscarPorId(long idBuscado) {
        for (Person persona : listaPersonas) {
            if (persona.getId() == idBuscado) {
                return persona;
            }
        }
        return null;
    }

    public boolean existeId(long idBuscado) {
        for (Person persona : listaPersonas) {
            if (persona.getId() == idBuscado) {
                return true;
            }
        }
        return false;
    }
}

