/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafiosjava;

/**
 *
 * @author jose9
 */



import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Desafio10 extends JFrame {

    private final JTextArea txtAreaEj1 = new JTextArea(12, 30);
    private int contadorVentanas = 0;

    public Desafio10() {
        super("Desafío 10 - Swing 2");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { confirmarSalida(); }
        });

        setJMenuBar(crearMenuBarEj1());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("1) Archivo", crearPanelEj1());
        tabs.addTab("2) Menú contextual", crearPanelEj2());
        tabs.addTab("3) Coordenadas", crearPanelEj3());
        tabs.addTab("4) Desktop + IF", crearPanelEj4());
        tabs.addTab("5) Teclas", crearPanelEj5());
        setContentPane(tabs);
    }

    private JMenuBar crearMenuBarEj1() {
        JMenuBar bar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem mNuevo = new JMenuItem("Nuevo");
        mNuevo.addActionListener(e -> txtAreaEj1.setText(""));

        JMenuItem mGuardar = new JMenuItem("Guardar");
        mGuardar.addActionListener(e -> guardarContenidoDe(txtAreaEj1));

        JMenuItem mSalir = new JMenuItem("Salir");
        mSalir.addActionListener(e -> confirmarSalida());

        menuArchivo.add(mNuevo);
        menuArchivo.add(mGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(mSalir);
        bar.add(menuArchivo);
        return bar;
    }

    private JPanel crearPanelEj1() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        txtAreaEj1.setLineWrap(true);
        txtAreaEj1.setWrapStyleWord(true);
        p.add(new JScrollPane(txtAreaEj1), BorderLayout.CENTER);
        p.add(new JLabel("Menú: Archivo → Nuevo, Guardar, Salir (con confirmación)"), BorderLayout.SOUTH);
        return p;
    }

    private void confirmarSalida() {
        int r = JOptionPane.showConfirmDialog(
                this,
                "¿Deseás cerrar la aplicación?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (r == JOptionPane.YES_OPTION) System.exit(0);
    }

    private void guardarContenidoDe(JTextArea area) {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(area.getText());
                JOptionPane.showMessageDialog(this,
                        "Guardado en:\n" + f.getAbsolutePath(),
                        "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel crearPanelEj2() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        JTextArea area = new JTextArea(12, 30);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setText("Haz clic derecho para ver el menú contextual (Copiar, Cortar, Pegar).");

        JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem(new DefaultEditorKit.CopyAction()) {{ setText("Copiar"); }});
        popup.add(new JMenuItem(new DefaultEditorKit.CutAction()) {{ setText("Cortar"); }});
        popup.add(new JMenuItem(new DefaultEditorKit.PasteAction()) {{ setText("Pegar"); }});

        area.setComponentPopupMenu(popup);
        p.add(new JScrollPane(area), BorderLayout.CENTER);
        p.add(new JLabel("Menú emergente (JPopupMenu) con Copiar, Cortar y Pegar."), BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearPanelEj3() {
        JPanel cont = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Haz clic en el panel para ver (x, y).");
        lbl.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        cont.add(lbl, BorderLayout.SOUTH);

        JPanel lienzo = new JPanel() {
            Point ultimoClick = null;
            { setBackground(new Color(245, 245, 245)); setPreferredSize(new Dimension(600, 400)); }

            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (ultimoClick != null) g.fillOval(ultimoClick.x - 3, ultimoClick.y - 3, 6, 6);
            }
        };

        lienzo.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                lbl.setText("Click en: (x=" + e.getX() + ", y=" + e.getY() + ")");
                try {
                    var f = lienzo.getClass().getDeclaredField("ultimoClick");
                    f.setAccessible(true);
                    f.set(lienzo, e.getPoint());
                    lienzo.repaint();
                } catch (Exception ignored) {}
            }
        });

        cont.add(lienzo, BorderLayout.CENTER);
        return cont;
    }

    private JPanel crearPanelEj4() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        JDesktopPane desktop = new JDesktopPane();
        JButton btnNueva = new JButton("Nueva ventana");

        btnNueva.addActionListener(e -> {
            contadorVentanas++;
            JInternalFrame ifr = new JInternalFrame(
                    "Ventana #" + contadorVentanas, true, true, true, true);
            ifr.setSize(260, 160);
            ifr.setLocation(20 * contadorVentanas, 20 * contadorVentanas);
            ifr.add(new JLabel("Hola! Soy la #" + contadorVentanas, SwingConstants.CENTER),
                    BorderLayout.CENTER);
            ifr.setVisible(true);
            desktop.add(ifr);
            try { ifr.setSelected(true); } catch (Exception ignored) {}
        });

        p.add(new JScrollPane(desktop), BorderLayout.CENTER);
        p.add(btnNueva, BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearPanelEj5() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField tf = new JTextField(25);
        JLabel lbl = new JLabel("Tecla: (esperando...)");
        top.add(new JLabel("Escribe algo:"));
        top.add(tf);
        p.add(top, BorderLayout.NORTH);
        p.add(lbl, BorderLayout.CENTER);

        tf.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                lbl.setText("Tecla: '" + c + "' (código: " + (int) c + ")");
            }
        });
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Desafio10().setVisible(true));
    }
}
