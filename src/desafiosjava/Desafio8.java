/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package desafiosjava;

import javax.swing.JOptionPane;

public class Desafio8 {
    public static void main(String[] args) {
        // Formulario simple con JOptionPane

        String nombre = JOptionPane.showInputDialog("Nombre completo:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre es obligatorio.");
            return;
        }

        String email = JOptionPane.showInputDialog("Email:");
        if (email == null || !email.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "Email inválido.");
            return;
        }

        String edadTxt = JOptionPane.showInputDialog("Edad:");
        int edad;
        try {
            edad = Integer.parseInt(edadTxt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La edad debe ser un número.");
            return;
        }

        if (edad < 18) {
            JOptionPane.showMessageDialog(null, "Debes ser mayor de 18.");
            return;
        }

        // Resumen final
        JOptionPane.showMessageDialog(
                null,
                "✅ Inscripción registrada\n\n"
              + "Nombre: " + nombre + "\n"
              + "Email: "  + email  + "\n"
              + "Edad: "   + edad
        );
    }
}



