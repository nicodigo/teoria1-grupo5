package unlu.teoi.grupo5.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsolaPanel extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public ConsolaPanel() {
        this.textArea = new JTextArea();
        textArea.setEditable(false);

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
