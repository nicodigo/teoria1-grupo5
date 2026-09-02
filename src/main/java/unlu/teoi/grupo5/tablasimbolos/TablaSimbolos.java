package unlu.teoi.grupo5.tablasimbolos;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TablaSimbolos implements Iterable<EntradaTS> {
    private final Map<String, EntradaTS> tabla = new LinkedHashMap<>();

    public void insertar(EntradaTS entrada) {
        tabla.putIfAbsent(entrada.getNombre(), entrada);
    }

    public EntradaTS buscar(String nombre) {
        return tabla.get(nombre);
    }

    @Override
    public Iterator<EntradaTS> iterator() {
        return tabla.values().iterator();
    }

}
