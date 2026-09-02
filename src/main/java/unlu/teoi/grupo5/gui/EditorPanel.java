package unlu.teoi.grupo5.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorPanel extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public EditorPanel() {
        // Editor de código
        this.textArea = new JTextArea();
        this.textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        this.textArea.setTabSize(4);

        // Scroll para el editor
        this.scrollPane = new JScrollPane(this.textArea);

        setLayout(new BorderLayout());
        add(this.scrollPane, BorderLayout.CENTER);
    }


    public String getText() {
        return this.textArea.getText();
    }
}
