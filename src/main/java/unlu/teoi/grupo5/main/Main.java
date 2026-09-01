package unlu.teoi.grupo5.main;

import unlu.teoi.grupo5.gui.Ide;

/**
 * Punto de entrada del IDE del compilador.
 *
 * <p>
 * Placeholder del kickstart: abre una ventana vacía para validar que el
 * JAR ejecutable (maven-shade-plugin) arranca. No contiene lógica del TP.
 *
 * <p>
 * TODO: reemplazar por la instanciación real de la GUI
 * (paquete unlu.teoi.grupo5.gui).
 */
public final class Main {

    private Main() {
        // No instanciable.
    }

    public static void main(String[] args) {
        Ide ide = new Ide();
        ide.setVisible(true);
    }
}
