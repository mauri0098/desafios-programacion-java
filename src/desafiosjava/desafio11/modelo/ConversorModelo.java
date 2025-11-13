/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafiosjava.desafio11.modelo;

/**
 *
 * @author Mauri
 */

public class ConversorModelo {

    // Celsius → Fahrenheit
    public double celsiusAFahrenheit(double c) {
        return (c * 9.0 / 5.0) + 32.0;
    }

    // Fahrenheit → Celsius
    public double fahrenheitACelsius(double f) {
        return (f - 32.0) * 5.0 / 9.0;
    }
}
