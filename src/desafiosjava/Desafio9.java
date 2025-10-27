/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package desafiosjava;

import javax.swing.*;          // JOptionPane + componentes Swing
import java.awt.*;             // BorderLayout / GridLayout
import java.awt.event.*;       // Listeners

public class Desafio9 {

    // ====== EJECUTA TODA LA APP ======
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Desafío 9 - Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(520, 620);
            frame.setLocationRelativeTo(null);

            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("1) Calculadora", crearCalculadora());
            tabs.addTab("2) Fuente", crearPanelFuente());
            tabs.addTab("3) Lenguajes", crearPanelLenguajes());
            tabs.addTab("4) Edad", crearPanelEdad());

            frame.setContentPane(tabs);
            frame.setVisible(true);
        });
    }

    // ====== EJERCICIO 1: CALCULADORA ======
    private static JPanel crearCalculadora() {
        JPanel root = new JPanel(new BorderLayout(8, 8));

        // Panel superior: display
        JTextField display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.BOLD, 22));
        root.add(display, BorderLayout.NORTH);

        // Estado de la calculadora
        final String[] operador = {null};   // "+", "-", "*", "/"
        final double[] acumulado = {0};     // operando 1
        final boolean[] limpiando = {false}; // limpiar display en el próximo dígito

        // Panel central: números 0-9
        JPanel pnlNumeros = new JPanel(new GridLayout(4, 3, 6, 6));
        String[] nums = {"7","8","9","4","5","6","1","2","3","0"};
        for (String n : nums) {
            JButton btn = new JButton(n);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
            btn.addActionListener(e -> {
                if (limpiando[0]) { display.setText(""); limpiando[0] = false; }
                display.setText(display.getText() + n);
            });
            pnlNumeros.add(btn);
        }
        // Relleno para que queden 4x3 prolijos
        pnlNumeros.add(new JLabel(""));
        pnlNumeros.add(new JLabel(""));
        root.add(pnlNumeros, BorderLayout.CENTER);

        // Panel inferior: operaciones (+ - * /) y "="
        JPanel pnlOps = new JPanel(new GridLayout(1, 5, 6, 6));
        String[] ops = {"+","-","*","/","="};
        for (String op : ops) {
            JButton b = new JButton(op);
            b.setFont(new Font("SansSerif", Font.BOLD, 18));
            b.addActionListener(e -> {
                try {
                    if (!"=".equals(op)) {
                        // Guardar operando 1 y operador
                        double valorActual = display.getText().isEmpty() ? 0 : Double.parseDouble(display.getText());
                        acumulado[0] = valorActual;
                        operador[0] = op;
                        limpiando[0] = true; // cuando toquen un número, se limpia
                    } else {
                        // Resolver
                        if (operador[0] == null) return;
                        double op2 = display.getText().isEmpty() ? 0 : Double.parseDouble(display.getText());
                        double res;
                        switch (operador[0]) {
                            case "+": res = acumulado[0] + op2; break;
                            case "-": res = acumulado[0] - op2; break;
                            case "*": res = acumulado[0] * op2; break;
                            case "/":
                                if (op2 == 0) {
                                    JOptionPane.showMessageDialog(root, "No se puede dividir por cero",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                res = acumulado[0] / op2; break;
                            default: return;
                        }
                        display.setText((res % 1 == 0) ? String.valueOf((long)res) : String.valueOf(res));
                        operador[0] = null;
                        limpiando[0] = true;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(root, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            pnlOps.add(b);
        }
        root.add(pnlOps, BorderLayout.SOUTH);

        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return root;
    }

    // ====== EJERCICIO 2: TAMAÑO DE FUENTE ======
    private static JPanel crearPanelFuente() {
        JPanel p = new JPanel(new BorderLayout(10,10));

        JLabel lbl = new JLabel("Texto de ejemplo", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));

        Integer[] tamanios = {12, 14, 16, 18, 20};
        JComboBox<Integer> combo = new JComboBox<>(tamanios);
        combo.setSelectedItem(16);
        combo.addActionListener(e -> {
            int size = (Integer) combo.getSelectedItem();
            lbl.setFont(lbl.getFont().deriveFont((float) size));
        });

        p.add(combo, BorderLayout.NORTH);
        p.add(lbl, BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return p;
    }

    // ====== EJERCICIO 3: SELECTOR DE LENGUAJES ======
    private static JPanel crearPanelLenguajes() {
        JPanel p = new JPanel(new BorderLayout(10,10));

        String[] langs = {"Java", "Python", "C++", "JavaScript", "C#", "Go", "Ruby"};
        JList<String> list = new JList<>(langs);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Doble clic: mostrar info con JOptionPane
        list.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int idx = list.locationToIndex(e.getPoint());
                    if (idx >= 0) {
                        String lang = list.getModel().getElementAt(idx);
                        String info = infoLenguaje(lang);
                        JOptionPane.showMessageDialog(p, info, lang, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        p.add(new JScrollPane(list), BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return p;
    }

    private static String infoLenguaje(String lang) {
        switch (lang) {
            case "Java": return "Java: Orientado a objetos, JVM, muy usado en backend y Android.";
            case "Python": return "Python: Sintaxis simple, ciencia de datos, IA, scripting.";
            case "C++": return "C++: Alto rendimiento, control fino de memoria, juegos/sistemas.";
            case "JavaScript": return "JavaScript: Lenguaje de la Web, ejecuta en el navegador y Node.js.";
            case "C#": return "C#: Similar a Java, ecosistema .NET, apps Windows y juegos (Unity).";
            case "Go": return "Go: Concurrencia simple, compilación rápida, backends cloud.";
            case "Ruby": return "Ruby: Productivo y expresivo, famoso por Ruby on Rails.";
            default: return "Lenguaje no documentado.";
        }
    }

    // ====== EJERCICIO 4: VALIDACIÓN DE EDAD ======
    private static JPanel crearPanelEdad() {
        JPanel p = new JPanel(new BorderLayout(10,10));
        JButton validar = new JButton("Validar edad");
        validar.setFont(new Font("SansSerif", Font.BOLD, 16));

        validar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(p, "Ingresá tu edad:", "Validación de edad",
                    JOptionPane.QUESTION_MESSAGE);
            if (input == null) return; // Cancelado
            try {
                int edad = Integer.parseInt(input.trim());
                if (edad < 18) {
                    JOptionPane.showMessageDialog(p, "Sos menor de edad. Acceso denegado.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(p, "¡Bienvenido/a! Acceso concedido.",
                            "OK", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(p, "Ingresá un número entero válido.",
                        "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }
        });

        p.add(new JLabel("Presioná el botón para validar tu edad:", SwingConstants.CENTER), BorderLayout.NORTH);
        p.add(validar, BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return p;
    }
}

