/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package desafiosjava;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class DesafiosJava {

    // Cambiá este valor para abrir 8, 9 o 10
    private static final int DESAFIO_ACTUAL = 10;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String nombreClase = DesafiosJava.class.getPackageName() + ".Desafio" + DESAFIO_ACTUAL;
                Class<?> clase = Class.forName(nombreClase);

                // 1) Intentar ejecutar el main(String[]) de la clase del desafío
                try {
                    Method main = clase.getMethod("main", String[].class);
                    main.invoke(null, (Object) new String[]{}); // llama al main estático
                    return; // listo
                } catch (NoSuchMethodException ignore) {
                    // Si no tiene main, pasamos al plan B
                }

                // 2) Plan B: instanciar la clase (si extiende JFrame/JDialog/Window)
                Constructor<?> ctor = clase.getDeclaredConstructor();
                Object instancia = ctor.newInstance();

                if (instancia instanceof java.awt.Window) {
                    ((java.awt.Window) instancia).setVisible(true);
                } else if (instancia instanceof JComponent) {
                    JFrame f = new JFrame("Desafío " + DESAFIO_ACTUAL);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setContentPane((JComponent) instancia);
                    f.pack();
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "La clase " + nombreClase + " no tiene main() ni es una ventana/componente.",
                        "No se pudo abrir", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "No se pudo abrir el Desafío " + DESAFIO_ACTUAL + ":\n" + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}

