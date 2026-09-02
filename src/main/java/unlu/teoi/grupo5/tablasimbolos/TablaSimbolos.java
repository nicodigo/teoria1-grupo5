package unlu.teoi.grupo5.tablasimbolos;

import java.util.LinkedHashMap;
import java.util.Map;

public class TablaSimbolos {
    private final Map<String, EntradaTS> tabla = new LinkedHashMap<>();

    public void insertar(EntradaTS entrada) {
        tabla.putIfAbsent(entrada.getNombre(), entrada);
    }

}
