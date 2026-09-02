package unlu.teoi.grupo5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsolaPanel extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public ConsolaPanel() {
        this.textArea = new JTextArea();
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        textArea.setEditable(false);

        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(false);

        textArea.setMargin(new Insets(8, 10, 8, 10));

        textArea.setBackground(new Color(25, 25, 25));
        textArea.setForeground(new Color(200, 200, 200));
        textArea.setCaretColor(new Color(200, 200, 200));

        textArea.setBorder(BorderFactory.createEmptyBorder());

        this.scrollPane = new JScrollPane(this.textArea);

        setLayout(new BorderLayout());
        add(this.scrollPane, BorderLayout.CENTER);
    }

    public void append(String texto) {
        this.textArea.append(texto);
    }

    public void clear() {
        this.textArea.setText("");
    }
}
