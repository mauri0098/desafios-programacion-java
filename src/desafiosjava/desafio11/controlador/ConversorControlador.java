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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConversorControlador {

    private final ConversorModelo modelo;
    private final ConversorVista vista;

    public ConversorControlador(ConversorModelo modelo, ConversorVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Cuando escribe en el texto, recalculamos
        vista.txtValoraconvertir.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                convertir();
            }
        });

        // Cuando cambia el tipo de conversión, recalculamos
        vista.cmbTipo.addActionListener(e -> convertir());
    }

    private void convertir() {
        try {
            // Leo el valor que escribió el usuario
            double valor = Double.parseDouble(vista.txtValoraconvertir.getText());

            String tipo = (String) vista.cmbTipo.getSelectedItem();
            double resultado;

            if ("Celsius".equals(tipo)) {
                // Interpreto que escribió °C y los paso a °F
                resultado = modelo.celsiusAFahrenheit(valor);
            } else { // "Fahrenheit"
                // Escribió °F y los paso a °C
                resultado = modelo.fahrenheitACelsius(valor);
            }

            vista.txtResultado.setText(String.valueOf(resultado));

        } catch (NumberFormatException ex) {
            // Si no es un número, limpio o marco error
            vista.txtResultado.setText("");
        }
    }
}





