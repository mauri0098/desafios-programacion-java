/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafiosjava;

/**
 *
 * @author Mauri
 */
import desafiosjava.desafio11.modelo.ConversorModelo;
import desafiosjava.desafio11.vista.ConversorVista;
import desafiosjava.desafio11.controlador.ConversorControlador;

public class Desafio11 {

    public static void main(String[] args) {
        ConversorModelo modelo = new ConversorModelo();
        ConversorVista vista = new ConversorVista();
        new ConversorControlador(modelo, vista); // conecta MVC

        vista.setVisible(true); // abre el formulario
    }
}

