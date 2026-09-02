package unlu.teoi.grupo5.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java_cup.runtime.Symbol;
import unlu.teoi.grupo5.lexer.AnalizadorLexico;
import unlu.teoi.grupo5.lexer.Lexico;
import unlu.teoi.grupo5.parser.sym;

public class Ide extends JFrame {
    private JTextArea editor;
    private JButton btnCompilar;
    private AnalizadorLexico analizadorLexico;

    public Ide() {
        setTitle("Compilador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.analizadorLexico = new AnalizadorLexico();

        // Editor de código
        editor = new JTextArea();
        editor.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Scroll para el editor
        JScrollPane scrollEditor = new JScrollPane(editor);

        // Botón
        btnCompilar = new JButton("Compilar");

        // Acción del botón
        btnCompilar.addActionListener(e -> compilar());

        // Layout
        setLayout(new BorderLayout());

        add(scrollEditor, BorderLayout.CENTER);
        add(btnCompilar, BorderLayout.SOUTH);
    }

    private void compilar() {
        String codigo = this.editor.getText();

        Lexico lexer = new Lexico(new StringReader(codigo));

        try {
            Symbol symbol;
            while ((symbol = lexer.next_token()).sym != sym.EOF){
                System.out.println(
                "Token: " + symbol.sym +
                " | Valor: " + symbol.value);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
