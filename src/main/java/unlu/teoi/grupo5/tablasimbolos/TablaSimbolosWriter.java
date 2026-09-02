package unlu.teoi.grupo5.tablasimbolos;

import java.nio.file.Path;

public class TablaSimbolosWriter {

    private static final String formato = "%-8s | %-15s | %-10s | %-10s | %-10s | %-5s";
    private static final String HEADER = String.format(
            formato,
            "LINEA",
            "NOMBRE",
            "TOKEN",
            "TIPO",
            "VALOR",
            "LONG");

    public static void escribir(TablaSimbolos tablaSimbolos, Path archivo) {

    }

    public static String tablaToString(TablaSimbolos tablaSimbolos) {
        StringBuilder sb = new StringBuilder();

        sb.append(HEADER);
        sb.append(System.lineSeparator());

        for (EntradaTS entrada : tablaSimbolos) {
            sb.append(entradaToString(entrada));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    private static String entradaToString(EntradaTS entrada) {
        return String.format(
                formato,
                entrada.getFila() + ":" + entrada.getColumna(),
                entrada.getNombre(),
                entrada.getToken(),
                entrada.getTipo() != null ? entrada.getTipo() : "-",
                entrada.getValor() != null ? entrada.getValor() : "-",
                entrada.getLongitud() > -1 ? entrada.getLongitud() : "-");
    }

}
