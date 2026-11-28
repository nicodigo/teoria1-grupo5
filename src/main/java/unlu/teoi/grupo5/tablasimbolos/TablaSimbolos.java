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

    /**
     * Obtiene el tipo asignado a un simbolo en la tabla, o {@code null} si
     * no existe o aun no tiene tipo asignado.
     */
    public Tipo obtenerTipo(String nombre) {
        EntradaTS entrada = buscar(nombre);
        return entrada != null ? entrada.getTipo() : null;
    }

    /**
     * Asigna el tipo a una variable ya registrada por el analizador lexico.
     * Si la entrada no existe, no la crea: el tipo solo completa un simbolo
     * previamente insertado. El orden de insercion se conserva porque
     * {@code put} sobre una clave existente no altera la posicion en el
     * {@link LinkedHashMap}.
     *
     * @return {@code true} si la entrada existia y el tipo se asigno.
     */
    public boolean asignarTipo(String nombre, Tipo tipo) {
        EntradaTS actual = tabla.get(nombre);
        if (actual == null || tipo == null) {
            return false;
        }
        tabla.put(nombre, actual.conTipo(tipo));
        return true;
    }

    /** Indica si el simbolo fue registrado (por el lexico o el parser). */
    public boolean contiene(String nombre) {
        return tabla.containsKey(nombre);
    }

    @Override
    public Iterator<EntradaTS> iterator() {
        return tabla.values().iterator();
    }

}
