package core.repositorio.persona;

import core.Person;
import java.util.ArrayList;

public class PersonaRepositorio {

    private static PersonaRepositorio instancia;
    private ArrayList<Person> personas;

    private PersonaRepositorio() {
        personas = new ArrayList<>();
    }

    public static PersonaRepositorio getInstancia() {
        if (instancia == null) {
            instancia = new PersonaRepositorio();
        }
        return instancia;
    }

    public ArrayList<Person> getPersonas() {
        return personas;
    }

    public void addPersona(Person persona) {
        personas.add(persona);
    }
}

