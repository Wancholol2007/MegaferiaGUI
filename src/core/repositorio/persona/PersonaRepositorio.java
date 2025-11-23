package core.repositorio.persona;

import core.model.persona.Person;
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

    public ArrayList<Person> obtenerPersonas() {
        return personas;
    }

    public void agregarPersona(Person persona) {
        personas.add(persona);
    }
}

