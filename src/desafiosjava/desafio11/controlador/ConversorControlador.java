/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafiosjava.desafio11.controlador;

/**
 *
 * @author Mauri
 */

import desafiosjava.desafio11.modelo.ConversorModelo;
import desafiosjava.desafio11.vista.ConversorVista;
import desafiosjava.desafio11.vista.TareaVista;
import desafiosjava.desafio11.controlador.TareaControlador; 
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConversorControlador {

    private final ConversorModelo modelo;
    private final ConversorVista vista;

    public ConversorControlador(ConversorModelo modelo, ConversorVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        // --- Evento: escribir en el campo convierte automáticamente ---
        vista.txtValoraconvertir.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                convertir();
            }
        });

        // --- Evento: cambio en combo ---
        vista.cmbTipo.addActionListener(e -> convertir());

        // --- Evento: abrir la consigna número 2 ---
        vista.btnConsigna2.addActionListener(e -> abrirGestorTareas());
    }

    private void convertir() {
        try {
            double valor = Double.parseDouble(vista.txtValoraconvertir.getText());

            String tipo = (String) vista.cmbTipo.getSelectedItem();
            double resultado;

            if ("Celsius".equals(tipo)) {
                resultado = modelo.celsiusAFahrenheit(valor);
            } else {
                resultado = modelo.fahrenheitACelsius(valor);
            }

            vista.txtResultado.setText(String.valueOf(resultado));

        } catch (NumberFormatException ex) {
            vista.txtResultado.setText("");
        }
    }

    // ---------------------------
    //   ABRIR CONSIGNA 2
    // ---------------------------
    private void abrirGestorTareas() {
        TareaVista vistaTareas = new TareaVista();
        new TareaControlador(vistaTareas);   // conecta modelo+vista
        vistaTareas.setVisible(true);
    }
}






