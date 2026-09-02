package unlu.teoi.grupo5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorPanel extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public EditorPanel() {
        // Editor de código
        this.textArea = new JTextArea();
        textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        textArea.setTabSize(4);

        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(false);

        textArea.setMargin(new Insets(10, 12, 10, 12));

        textArea.setBackground(new Color(30, 30, 30));
        textArea.setForeground(new Color(220, 220, 220));
        textArea.setCaretColor(new Color(220, 220, 220));

        textArea.setSelectionColor(new Color(60, 90, 120));
        textArea.setSelectedTextColor(Color.WHITE);

        textArea.setBorder(BorderFactory.createEmptyBorder());

        // Scroll para el editor
        this.scrollPane = new JScrollPane(this.textArea);

        setLayout(new BorderLayout());
        add(this.scrollPane, BorderLayout.CENTER);
    }

    public String getText() {
        return this.textArea.getText();
    }
}
