package core.controlador.utilidades;

public class Response {

    private String mensaje;
    private Status estado;
    private Object contenido;

    public Response(String mensaje, Status estado) {
        this.mensaje = mensaje;
        this.estado = estado;
    }

    public Response(String mensaje, Status estado, Object contenido) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.contenido = contenido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Status getEstado() {
        return estado;
    }

    public Object getContenido() {
        return contenido;
    }
}

