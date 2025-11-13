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
                        "8"
                );

                if (sel == null) return;
                int n = Integer.parseInt(sel);

                String base = DesafiosJava.class.getPackageName(); // "desafiosjava"
                String suf  = String.valueOf(n);                   // "8", "9", "10"
                String suf2 = (n < 10 ? "0" + n : suf);            // "08", "09", "10"

                // Probamos varias posibles rutas según cómo estén nombrados los paquetes
                String[] candidatos = new String[] {
                        // Ej: desafiosjava.Desafio8.Desafio8
                        base + ".Desafio" + suf + ".Desafio" + suf,
                        // Ej: desafiosjava.Desafio8
                        base + ".Desafio" + suf,

                        // Ej: desafiosjava.Desafio08.Desafio8
                        base + ".Desafio" + suf2 + ".Desafio" + suf,
                        // Ej: desafiosjava.Desafio08
                        base + ".Desafio" + suf2,

                        // Ej: desafiosjava.desafio8.Desafio8
                        base + ".desafio" + suf + ".Desafio" + suf,
                        // Ej: desafiosjava.desafio08.Desafio8
                        base + ".desafio" + suf2 + ".Desafio" + suf,
                        // Ej: desafiosjava.desafio8
                        base + ".desafio" + suf,
                        // Ej: desafiosjava.desafio08
                        base + ".desafio" + suf2
                };

                Class<?> clase = null;
                String elegido = null;

                for (String fqcn : candidatos) {
                    try {
                        clase = Class.forName(fqcn);
                        elegido = fqcn;
                        break;
                    } catch (ClassNotFoundException | NoClassDefFoundError ignore) {
                        // probamos el siguiente
                    }
                }

                if (clase == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "No encontré la clase del desafío " + n,
                            "Clase no encontrada",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // 1) Intentar ejecutar main(...)
                try {
                    Method main = clase.getMethod("main", String[].class);
                    main.invoke(null, (Object) new String[] {});
                    return;
                } catch (NoSuchMethodException ignore) {
                    // si no tiene main, seguimos al plan B
                }

                // 2) Intentar mostrarlo como ventana / componente Swing
                Constructor<?> ctor = clase.getDeclaredConstructor();
                Object instancia = ctor.newInstance();

                if (instancia instanceof java.awt.Window w) {
                    w.setLocationRelativeTo(null);
                    w.setVisible(true);
                } else if (instancia instanceof JComponent comp) {
                    JFrame f = new JFrame("Desafío " + n + " (" + elegido + ")");
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setContentPane(comp);
                    f.pack();
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "La clase " + elegido + " no tiene main() ni es una ventana.",
                            "No se pudo abrir",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error cargando desafío:\n" + e,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        });
    }
}




