/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafiosjava.desafio11.controlador;

/**
 *
 * @author Mauri
 */

import desafiosjava.desafio11.modelo.TareaModelo;
import desafiosjava.desafio11.vista.TareaVista;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class TareaControlador {

    private final TareaVista vista;

    // Lista "real" de tareas (modelo)
    private final List<TareaModelo> tareas = new ArrayList<>();

    // Modelo que ve la JList (solo Strings)
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();

    public TareaControlador(TareaVista vista) {
        this.vista = vista;

        // Conectar modelo de la JList
        this.vista.ltsTareas.setModel(modeloLista);

        // Eventos de botones
        this.vista.btnAgregartarea1.addActionListener(e -> agregarTarea());
        this.vista.btnMarcarCompletada.addActionListener(e -> marcarCompletada());
    }

    private void agregarTarea() {
        String nombre = vista.txtNuevatarea.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    vista,
                    "La tarea no puede estar vacía.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        TareaModelo tarea = new TareaModelo(nombre);
        tareas.add(tarea);

        // Lo que se muestra en la lista
        modeloLista.addElement(formatear(tarea));

        vista.txtNuevatarea.setText("");
        vista.txtNuevatarea.requestFocus();
    }

    private void marcarCompletada() {
        int index = vista.ltsTareas.getSelectedIndex();

        if (index == -1) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Seleccioná una tarea para marcarla como completada.",
                    "Atención",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        TareaModelo tarea = tareas.get(index);
        tarea.setCompletada(true);

        // Actualizar el texto que se ve en la JList
        modeloLista.set(index, formatear(tarea));
    }

    private String formatear(TareaModelo tarea) {
        return tarea.getNombre() + (tarea.isCompletada() ? " (Completada)" : "");
    }
}
