package unlu.teoi.grupo5.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java_cup.runtime.Symbol;
import unlu.teoi.grupo5.lexer.AnalizadorLexico;
import unlu.teoi.grupo5.lexer.Lexico;
import unlu.teoi.grupo5.parser.sym;

public class Ide extends JFrame {
    private EditorPanel editor;
    private ConsolaPanel consola;
    private JButton btnCompilar;
    private AnalizadorLexico analizadorLexico;

    public Ide() {
        setTitle("Compilador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        initLayout();

    }

    private void initComponents() {
        this.analizadorLexico = new AnalizadorLexico();

        // Editor de código
        this.editor = new EditorPanel();

        this.consola = new ConsolaPanel();

        // Botón
        this.btnCompilar = new JButton("Compilar");
        // Acción del botón
        this.btnCompilar.addActionListener(e -> compilar());

    }

    private void initLayout() {
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(this.btnCompilar);

        add(panelBotones, BorderLayout.NORTH);

        // Editor + consola
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                this.editor,
                this.consola);

        // 75% editor / 25% consola aproximadamente
        splitPane.setResizeWeight(0.75);

        add(splitPane, BorderLayout.CENTER);
    }

    private void compilar() {
        String codigo = this.editor.getText();

        Lexico lexer = new Lexico(new StringReader(codigo));

        try {
            Symbol symbol;
            while ((symbol = lexer.next_token()).sym != sym.EOF) {
                this.consola.append(
                        "Token: " + symbol.sym +
                                " | Valor: " + symbol.value + "\n");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
