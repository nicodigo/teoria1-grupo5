package unlu.teoi.grupo5.main;

import javax.swing.JFrame;

/**
 * Punto de entrada del IDE del compilador.
 *
 * <p>Placeholder del kickstart: abre una ventana vacía para validar que el
 * JAR ejecutable (maven-shade-plugin) arranca. No contiene lógica del TP.
 *
 * <p>TODO: reemplazar por la instanciación real de la GUI
 * (paquete unlu.teoi.grupo5.gui).
 */
public final class Main {

    private Main() {
        // No instanciable.
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Compilador - Grupo 5");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
