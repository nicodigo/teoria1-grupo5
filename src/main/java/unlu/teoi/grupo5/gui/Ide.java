package unlu.teoi.grupo5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import unlu.teoi.grupo5.lexer.AnalizadorLexico;
import unlu.teoi.grupo5.lexer.Lexico;
import unlu.teoi.grupo5.parser.Parser;
import unlu.teoi.grupo5.tablasimbolos.TablaSimbolos;
import unlu.teoi.grupo5.tablasimbolos.TablaSimbolosWriter;

public class Ide extends JFrame {

    private static final Color COLOR_BOTON = new Color(0, 122, 204);
    private static final Color COLOR_BOTON_HOVER = new Color(28, 151, 234);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    /** Carpeta de entregables de la entrega vigente (se escribe ts.txt ahi). */
    private static final Path DIRECTORIO_ENTREGA = Paths.get("docs", "entrega2");

    private EditorPanel editor;
    private ConsolaPanel consola;
    private JButton btnCompilar;
    private JButton btnLimpiar;
    private JButton btnCargar;
    private JButton btnGuardar;
    private JLabel statusBar;
    private AnalizadorLexico analizadorLexico;
    private Parser parserSintactico;

    public Ide() {
        setTitle("Compilador - Grupo 5");
        setSize(950, 700);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        aplicarLookAndFeel();
        initComponents();
        initLayout();
    }

    private void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        this.editor = new EditorPanel();
        LineBorder borde = new LineBorder(Color.GRAY, 1);

        this.editor.setBorder(BorderFactory.createTitledBorder(
                borde,
                " Codigo Fuente ", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), Color.DARK_GRAY));

        this.consola = new ConsolaPanel();
        this.consola.setBorder(BorderFactory.createTitledBorder(
                borde,
                " Salida del Compilador ", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), Color.DARK_GRAY));

        this.btnCargar = new JButton("Cargar");
        this.btnGuardar = new JButton("Guardar");
        this.btnLimpiar = new JButton("Limpiar Salida");
        this.btnCompilar = new JButton("Compilar");

        estilizarBoton(this.btnCargar);
        estilizarBotonArchivo(this.btnGuardar);
        estilizarBotonSecundario(this.btnLimpiar);
        estilizarBoton(this.btnCompilar);

        this.btnCargar.addActionListener(e -> cargarArchivo());
        this.btnGuardar.addActionListener(e -> guardarArchivo());
        this.btnLimpiar.addActionListener(e -> {
            this.consola.clear();
            this.consola.setColor(new Color(200, 200, 200));
            this.statusBar.setText(" Estado: Listo");
        });
        this.btnCompilar.addActionListener(e -> compilar());

        this.statusBar = new JLabel(" Estado: Listo");
        this.statusBar.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addChangeListener(e -> {
            if (boton.getModel().isRollover())
                boton.setBackground(COLOR_BOTON_HOVER);
            else
                boton.setBackground(COLOR_BOTON);
        });
    }

    private void estilizarBotonSecundario(JButton boton) {
        Color colorGrisBase = new Color(100, 100, 100);
        Color colorGrisHover = new Color(130, 130, 130);

        boton.setFont(FUENTE_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorGrisBase);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addChangeListener(e -> {
            if (boton.getModel().isRollover())
                boton.setBackground(colorGrisHover);
            else
                boton.setBackground(colorGrisBase);
        });
    }

    private void estilizarBotonArchivo(JButton boton) {
        Color colorBase = new Color(46, 139, 87);
        Color colorHover = new Color(60, 179, 113);

        boton.setFont(FUENTE_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorBase);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addChangeListener(e -> {
            if (boton.getModel().isRollover())
                boton.setBackground(colorHover);
            else
                boton.setBackground(colorBase);
        });
    }

    private void initLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.add(this.btnCargar);
        topPanel.add(this.btnGuardar);
        add(topPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                this.editor,
                this.consola);

        splitPane.setResizeWeight(0.70);
        splitPane.setDividerSize(8);
        splitPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelIzquierdo.add(this.statusBar);
        bottomPanel.add(panelIzquierdo, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        panelDerecho.add(this.btnLimpiar);
        panelDerecho.add(this.btnCompilar);
        bottomPanel.add(panelDerecho, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String contenido = new String(Files.readAllBytes(file.toPath()));
                this.editor.setText(contenido);
                this.statusBar.setText(" Estado: Archivo cargado (" + file.getName() + ")");
            } catch (Exception ex) {
                this.consola.setColor(new Color(255, 0, 0));
                this.consola.append("\n[ERROR] No se pudo leer el archivo.\n");
            }
        }
    }

    private void guardarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                Files.write(file.toPath(), this.editor.getText().getBytes());
                this.statusBar.setText(" Estado: Archivo guardado (" + file.getName() + ")");
            } catch (Exception ex) {
                this.consola.setColor(new Color(255, 0, 0));
                this.consola.append("\n[ERROR] No se pudo guardar el archivo.\n");
            }
        }
    }

    private void compilar() {
        this.consola.clear();
        this.consola.setColor(new Color(200, 200, 200));
        this.statusBar.setText(" Estado: Compilando...");
        String codigo = this.editor.getText();

        // Una tabla por compilacion: la comparten el lexico (registra simbolos)
        // y el parser (asigna el TIPO al reducir DECLARE.SECTION).
        TablaSimbolos tablaSimbolos = new TablaSimbolos();
        this.analizadorLexico = new AnalizadorLexico(new Lexico(new StringReader(codigo)), tablaSimbolos);
        this.parserSintactico = new Parser(this.analizadorLexico, tablaSimbolos);

        // Las acciones de Sintactico.cup imprimen las reglas con System.out; se
        // redirige a la consola del IDE mientras dura el analisis sintactico.
        PrintStream salidaOriginal = System.out;
        PrintStream salidaConsola = new PrintStream(new ConsolaOutputStream(), true, StandardCharsets.UTF_8);
        System.setOut(salidaConsola);

        this.consola.append("--- ANALISIS SINTACTICO ---\n");
        boolean exito = true;
        try {
            // El parser consume el lexico a traves del adaptador: no hay que
            // recorrer los tokens antes, o el parser arrancaria en EOF.
            this.parserSintactico.parse();
        } catch (Error e) {
            // Errores lexicos que Lexico.flex lanza al validar constantes.
            exito = false;
            this.consola.setColor(new Color(255, 0, 0));
            String msg = e.getMessage();
            if (msg != null && msg.startsWith("Error Lexico: ")) {
                msg = msg.substring("Error Lexico: ".length());
            }
            this.consola.append("\n[ERROR LEXICO] " + msg + "\n");
            this.statusBar.setText(" Estado: Error Lexico detectado - Compilacion interrumpida");
        } catch (Exception e) {
            // Errores sintacticos: CUP no puede recuperarse del primer error.
            exito = false;
            this.consola.setColor(new Color(255, 0, 0));
            this.consola.append("\n[ERROR SINTACTICO] " + e.getMessage() + "\n");
            this.statusBar.setText(" Estado: Error Sintactico detectado - Compilacion interrumpida");
        } finally {
            salidaConsola.flush();
            System.setOut(salidaOriginal);
        }

        if (!exito) {
            // Igual criterio que con los errores lexicos: si la compilacion se
            // interrumpe, no se sobreescribe el ts.txt de la entrega.
            return;
        }

        this.statusBar.setText(" Estado: Compilacion exitosa");

        TablaSimbolos ts = analizadorLexico.getTablaSimbolos();
        String stringTabla = TablaSimbolosWriter.tablaToString(ts);

        this.consola.append("\n--- TABLA DE SIMBOLOS ---\n");
        this.consola.append(stringTabla);

        // Autoguardado en docs/entrega2/ts.txt (la carpeta se crea si no existe)
        try {
            Files.createDirectories(DIRECTORIO_ENTREGA);
            Files.write(DIRECTORIO_ENTREGA.resolve("ts.txt"), stringTabla.getBytes(StandardCharsets.UTF_8));
            this.consola.append("\n[INFO] Tabla guardada automaticamente en "
                    + DIRECTORIO_ENTREGA.resolve("ts.txt") + "\n");
        } catch (Exception ex) {
            this.consola.setColor(new Color(255, 0, 0));
            this.consola.append("\n[ERROR] No se pudo guardar ts.txt en la carpeta " + DIRECTORIO_ENTREGA + ".\n");
        }
    }

    /**
     * Vuelca en el panel de salida lo que el parser escribe en {@code System.out}.
     * Acumula bytes hasta el salto de linea para no cortar una regla al medio.
     */
    private final class ConsolaOutputStream extends OutputStream {

        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        @Override
        public void write(int b) {
            if (b == '\n') {
                volcarLinea();
            } else if (b != '\r') {
                buffer.write(b);
            }
        }

        @Override
        public void flush() {
            if (buffer.size() > 0) {
                volcarLinea();
            }
        }

        private void volcarLinea() {
            String linea = buffer.toString(StandardCharsets.UTF_8);
            buffer.reset();
            consola.append(linea + "\n");
        }
    }
}
