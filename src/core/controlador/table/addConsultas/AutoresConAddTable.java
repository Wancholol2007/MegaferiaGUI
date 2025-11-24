package core.controlador.table.addConsultas;

import core.controlador.utilidades.Response;
import core.controlador.utilidades.Status;

import core.model.persona.Person;
import core.model.persona.author.Author;
import core.model.persona.author.AuthorBookService;
import core.model.persona.utilidades.FullName;

import core.model.storage.PersonaStorage;

import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;

public class AutoresConAddTable {

    public static Response actualizarTablaAutoresConMasEditoriales(DefaultTableModel modeloTabla) {

        try {
            PersonaStorage almacenPersonas = PersonaStorage.getInstance();
            ArrayList<core.Person> listaPersonas = almacenPersonas.getPersonas();

            // Validación de contenido
            if (listaPersonas == null || listaPersonas.isEmpty()) {
                return new Response(
                    "No hay autores registrados para mostrar.",
                    Status.NO_CONTENT
                );
            }

            // Filtrar únicamente autores
            ArrayList<Author> autoresRegistrados = new ArrayList<>();

            for (core.Person persona : listaPersonas) {
                if (persona instanceof Author author) {
                    autoresRegistrados.add(author);
                }
            }

            if (autoresRegistrados.isEmpty()) {
                return new Response(
                    "No hay autores válidos registrados.",
                    Status.NO_CONTENT
                );
            }

            // Determinar máximo número de editoriales distintas
            int maximoEditoriales = -1;
            ArrayList<Author> autoresConMayorCantidad = new ArrayList<>();

            for (Author autor : autoresRegistrados) {

                int cantidadEditoriales = AuthorBookService.countDistinctPublishers(autor);

                if (cantidadEditoriales > maximoEditoriales) {
                    maximoEditoriales = cantidadEditoriales;
                    autoresConMayorCantidad.clear();
                    autoresConMayorCantidad.add(autor);

                } else if (cantidadEditoriales == maximoEditoriales) {
                    autoresConMayorCantidad.add(autor);
                }
            }

            // Limpiar tabla
            modeloTabla.setRowCount(0);

            // Añadir autores con máximo número de editoriales
            for (Author autor : autoresConMayorCantidad) {

                String nombreCompleto = FullName.unitVariables(
                    autor.getNombres(),
                    autor.getApellidos()
                );

                Object[] fila = {
                    autor.getId(),
                    nombreCompleto,
                    maximoEditoriales
                };

                modeloTabla.addRow(fila);
            }

            return new Response(
                "Tabla de autores actualizada correctamente.",
                Status.OK
            );

        } catch (Exception ex) {
            return new Response(
                "Error inesperado al actualizar la tabla de autores.",
                Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}

