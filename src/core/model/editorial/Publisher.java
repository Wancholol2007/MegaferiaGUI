package core.model.editorial;

import core.model.persona.Manager;

public class Publisher {

    private String nit;
    private String nombre;
    private String direccion;
    private Manager gerenteAsignado;

    public Publisher(String nit, String nombre, String direccion, Manager gerenteAsignado) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.gerenteAsignado = gerenteAsignado;
    }

    public String getNit() {
        return nit;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Manager getGerenteAsignado() {
        return gerenteAsignado;
    }

    public void setDireccion(String nuevaDireccion) {
        this.direccion = nuevaDireccion;
    }

    public void setGerenteAsignado(Manager nuevoGerente) {
        this.gerenteAsignado = nuevoGerente;
    }
}

