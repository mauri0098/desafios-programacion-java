package desafiosjava;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class DesafiosJava {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Opciones de desafíos disponibles
                String[] opciones = {"8", "9", "10", "11"};
                String sel = (String) JOptionPane.showInputDialog(
                        null,
                        "Seleccioná el desafío a ejecutar:",
                        "Launcher de Desafíos",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        "9"
                );

                if (sel == null) return;
                int n = Integer.parseInt(sel);

                // Clase principal a buscar
                String nombreClase = "desafiosjava.Desafio" + n;

                Class<?> clase = Class.forName(nombreClase);

                // Caso 1: tiene un main propio
                try {
                    Method main = clase.getMethod("main", String[].class);
                    main.invoke(null, (Object) new String[] {});
                    return;
                } catch (NoSuchMethodException ignore) {
                    // Si no tiene main seguimos al plan B
                }

                // Caso 2: es una ventana o componente Swing
                Constructor<?> ctor = clase.getDeclaredConstructor();
                Object instancia = ctor.newInstance();

                if (instancia instanceof java.awt.Window w) {
                    w.setVisible(true);
                } else if (instancia instanceof JComponent comp) {
                    JFrame f = new JFrame("Desafío " + n);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setContentPane(comp);
                    f.pack();
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "La clase " + nombreClase + " no tiene main() ni es una ventana.",
                            "No se pudo abrir",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error cargando desafío:\n" + e,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}


