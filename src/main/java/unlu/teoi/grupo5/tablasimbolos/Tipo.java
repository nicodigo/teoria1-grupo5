package unlu.teoi.grupo5.tablasimbolos;

/**
 * Tipo de dato de una variable, segun los tipos declarados en DECLARE.SECTION
 * (INT, FLOAT, STRING).
 *
 * <p>
 * El analizador lexico no conoce el tipo de una variable: solo el analizador
 * sintactico sabe que posicion ocupa cada identificador en una declaracion
 * {@code [a, b] ::= [INT, FLOAT]}. Por eso el tipo se asigna desde las acciones
 * semanticas de {@code Sintactico.cup} a traves de
 * {@link TablaSimbolos#asignarTipo(String, Tipo)}.
 */
public enum Tipo {

    INT("INT"),
    FLOAT("FLOAT"),
    STRING("STRING");

    private final String etiqueta;

    Tipo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /** Texto que se muestra en la columna TIPO de la tabla de simbolos. */
    public String etiqueta() {
        return etiqueta;
    }

    /**
     * Convierte el texto de un token de tipo ("INT", "FLOAT", "STRING") en el
     * enum correspondiente. Devuelve {@code null} si el texto no es un tipo.
     */
    public static Tipo desdeTexto(String texto) {
        if (texto == null) {
            return null;
        }
        String limpio = texto.trim().toUpperCase();
        for (Tipo tipo : values()) {
            if (tipo.etiqueta.equals(limpio)) {
                return tipo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
