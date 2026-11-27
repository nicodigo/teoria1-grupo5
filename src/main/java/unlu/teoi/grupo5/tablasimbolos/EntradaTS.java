package unlu.teoi.grupo5.tablasimbolos;

public class EntradaTS {
    private final String nombre;
    private final String token;
    private final Tipo tipo;
    private final Object valor;
    private final int longitud;

    public EntradaTS(String nombre, String token, Tipo tipo, Object valor, int longitud) {
        this.nombre = nombre;
        this.token = token;
        this.tipo = tipo;
        this.valor = valor;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getToken() {
        return token;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public int getLongitud() {
        return longitud;
    }

    /**
     * Devuelve una copia de esta entrada con el tipo indicado. La entrada es
     * inmutable; el parser asigna el tipo reemplazando la entrada en la tabla.
     */
    public EntradaTS conTipo(Tipo nuevoTipo) {
        return new EntradaTS(nombre, token, nuevoTipo, valor, longitud);
    }

}
