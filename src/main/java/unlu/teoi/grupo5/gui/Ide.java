package unlu.teoi.grupo5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.io.StringReader;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java_cup.runtime.Symbol;
import unlu.teoi.grupo5.lexer.AnalizadorLexico;
import unlu.teoi.grupo5.lexer.Lexico;
import unlu.teoi.grupo5.parser.sym;
import unlu.teoi.grupo5.tablasimbolos.TablaSimbolos;
import unlu.teoi.grupo5.tablasimbolos.TablaSimbolosWriter;

public class Ide extends JFrame {

    private static final Color COLOR_FONDO = new Color(30, 30, 30);
    private static final Color COLOR_BOTON = new Color(0, 122, 204);
    private static final Color COLOR_BOTON_HOVER = new Color(28, 151, 234);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    private EditorPanel editor;
    private ConsolaPanel consola;
    private JButton btnCompilar;
    private AnalizadorLexico analizadorLexico;

    public Ide() {
        setTitle("Compilador");
        setSize(900, 650);
        setMinimumSize(new Dimension(600, 400));
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
        this.analizadorLexico = new AnalizadorLexico();

        this.editor = new EditorPanel();
        this.consola = new ConsolaPanel();

        this.btnCompilar = new JButton("Compilar");
        estilizarBoton(this.btnCompilar);
        this.btnCompilar.addActionListener(e -> compilar());
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);

        boton.addChangeListener(e -> {
            if (boton.getModel().isRollover()) {
                boton.setBackground(COLOR_BOTON_HOVER);
            } else {
                boton.setBackground(COLOR_BOTON);
            }
        });
    }

    private void initLayout() {
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelBotones.add(this.btnCompilar);
        add(panelBotones, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                this.editor,
                this.consola);
        splitPane.setResizeWeight(0.75);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);
    }

    private void compilar() {
        String codigo = this.editor.getText();
        Lexico lexer = new Lexico(new StringReader(codigo));
        try {
            analizadorLexico.analizar(lexer);
            TablaSimbolos ts = analizadorLexico.getTablaSimbolos();
            this.consola.append(TablaSimbolosWriter.tablaToString(ts));
            // Symbol symbol;
            // while ((symbol = lexer.next_token()).sym != sym.EOF) {
            //     this.consola.append(
            //             "Token: " + symbol.sym +
            //                     " | Valor: " + symbol.value + "\n");
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
