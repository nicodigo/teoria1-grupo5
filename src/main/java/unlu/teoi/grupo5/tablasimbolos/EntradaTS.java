package unlu.teoi.grupo5.tablasimbolos;

public class EntradaTS {
    private final String nombre;
    private final String token;
    private final String tipo;
    private final Object valor;
    private final int longitud;
    private final int fila;
    private final int columna;

    public EntradaTS(String nombre, String token, String tipo, Object valor, int longitud, int fila, int columna) {
        this.nombre = nombre;
        this.token = token;
        this.tipo = tipo;
        this.valor = valor;
        this.longitud = longitud;
        this.fila = fila;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public int getLongitud() {
        return longitud;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

}
